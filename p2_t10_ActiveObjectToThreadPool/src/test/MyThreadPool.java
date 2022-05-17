package test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/** Thread pool using ActiveObjects. 2 important things in ThreadPool: open few Threads,
 * and reuse them until we want to close them.
 * each ActiveObject has one thread that executes tasks, so one ActiveObject serves as an executing thread in the ThreadPool */
public class MyThreadPool {

    BlockingQueue<ActiveObject> activeObjects;
    BlockingQueue<Runnable> mainQ; // main queue that holds new tasks to be sorted to the ActiveObjects
    Thread t; // while mainQ isn't empty, t will pull a task from it and will choose which ActiveObject will get that task
    volatile boolean stop;
    Runnable stopAll = () -> stop = true;

    public MyThreadPool(int capacity, int maxThreads) {
        /** capacity of each ActiveObject, and maximum number of threads (=ActiveObjects) that we'll open */
        activeObjects = new ArrayBlockingQueue<>(maxThreads);
        mainQ = new ArrayBlockingQueue<>(capacity);
        t = new Thread(()->{
            while(!stop){
                try {
                    Runnable task = mainQ.take();
                    /** if the task is stopAll, we just run it without inserting it to an ActiveObject to run it.
                     * because if an ActiveObject will run it - it won't change the ThreadPool's "stop" to true.
                     * after running stopAll, we'll get out of the while loop */
                    if(task == stopAll){
                        stopAll.run(); // stop = true
                    }
                    else{
                        // if we didn't reach the maxThread, we'll open a new one, give it the task and add the ac to the activeObjects queue
                        if(activeObjects.size() < maxThreads){
                            ActiveObject ac = new ActiveObject(capacity);
                            ac.execute(task);
                            /** we can use "add" here instead of "put" because we'll never have a situation where there isn't enough
                             * space to add it to activeObjects, because of the "if" above */
                            activeObjects.add(ac);
                        } // if we reached maxThread, we'll choose the one with the least tasks in its queue, to execute this task
                        else{
                            ActiveObject choice = chooseSmallest();
                            choice.execute(task);
                        }
                    }

                } catch (InterruptedException e) {}
            }
        });

        t.start();
    }

    // returns the ActiveObject with the shortest queue of tasks
    private ActiveObject chooseSmallest() {
        ActiveObject choice = null;
        int min = Integer.MAX_VALUE;
        for(ActiveObject a : activeObjects){
            if(a.size() < min){
                min = a.size();
                choice = a;
            }
        }
        return choice;
    }


    //
    public void execute(Runnable r){
        if(!stop){
            try {
                /** put is a blocking call: inserts element into the queue, and waiting if necessary for space to become available.
                 * we insert a Runnable to mainQ, and in the constructor the thread t takes it out and give it to an ActiveObject */
                mainQ.put(r);
            } catch (InterruptedException e) {}
        }
    }

    public void shutdownNow(){
        stop = true;
        activeObjects.forEach(a -> a.shutdownNow()); // shutdownNow each ActiveObject
        t.interrupt();
    }

    public void shutdown(){
        activeObjects.forEach(a -> a.shutdown()); // shutdown each ActiveObject
        execute(stopAll);
    }

    public <V> Future<V> submit(Callable<V> c){
        Future<V> f = new Future<>();
        execute(()-> f.set(c.call()));
        return f;
    }
}
