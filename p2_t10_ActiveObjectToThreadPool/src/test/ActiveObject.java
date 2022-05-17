package test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/** ActiveObject separates between the Thread that inserted the task, and the Thread who executes it */
public class ActiveObject {

    BlockingQueue<Runnable> q;
    Thread t;
    /** "stop" is volatile because many threads will use it, so it's important to have one source of truth.
     * volatile is saved only in the RAM, and not in the cache */
    volatile boolean stop;

    public ActiveObject(int capacity) {
        stop = false;
        q = new ArrayBlockingQueue<>(capacity);
        /** Thread constructor gets Runnable. the while loop is in the run() lambda expression.
         * this t thread is the one who executes the Runnables, and the thread main is the one to insert them
         * into the queue, with the execute method below */
        t = new Thread(()->{
            while(!stop){
                try {
                    // take is a blocking call, if the queue is empty, it waits. it pulls a Runnable from q and runs is
                    q.take().run();
                } catch (InterruptedException e) {}
            }
        });

        t.start();
    }

    public void execute(Runnable r){
        if(!stop){
            try {
                // put is a blocking call: inserts element into the queue, and waiting if necessary for space to become available
                q.put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void shutdownNow(){
        stop = true;
        /** if the queue is empty, we're stuck in the "take" line. and if we're in the middle of a task, we're in the run() -
         * t will continue the while loop only when it finishes the task. for these reasons we need
         * to interrupt t, and throw the interrupted exception, so t is in the catch and then continues to the while(!stop)
         * which is now false */
        t.interrupt();
    }


    public void shutdown(){
        /** we want all the tasks that are already in the queue to run and finish, and then to shut down.
         * we use execute to insert a Runnable to the queue, that its run() method changes stop to true */
        execute(() -> stop = true);
    }

    public int size(){
        return q.size();
    }
}
