package test;

import java.util.concurrent.*;

public class ThreadPoolExample {

    /** Example 1: Runnable (no return value from run()) */
    public static class ExampleTask implements Runnable{

        int id;

        public ExampleTask(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            // simulation of a task that takes a lot of time
            System.out.println("task started " + id);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}

            System.out.println("task finished " + id);
        }
    }

    /** Example 2: Callable (there's a return value from call()) */

    public static class ExampleTask2 implements Callable<Integer> {


        @Override
        public Integer call() throws Exception {
            // simulates a long calculation that in the end returns the value 42
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}
            return 42;
        }
    }

    public static void main(String[] args) {
        // create ThreadPool with 3 active threads
        /** the ExecutorService thread runs in the background, and it runs a loop that waits while the queue is empty,
         * and when there's an object in the queue, it sends it to one of the 3 active threads.
         * the ExecutorService thread keeps waiting until we close the ExecutorService */
        ExecutorService es = Executors.newFixedThreadPool(3);
        // execute() puts the Runnable in the ThreadPool queue, and then returns.
        // execute() expects Runnable, and returns void!
        es.execute(new ExampleTask(1));
        es.execute(new ExampleTask(2));
        es.execute(new ExampleTask(3));
        es.execute(new ExampleTask(4));
        es.execute(new ExampleTask(5));
        // submit also puts the object in the queue, and returns Future<Integer>
        Future<Integer> f = es.submit(new ExampleTask2());
        System.out.println("..."); // doing something until we have to get the Integer from Future
        try {
            // if we call get() before the value is ready, it will wait until it's ready (blocking call)
            Integer i = f.get();
            /** the main thread is the one who prints the returned value i,
             * so the thread main waits until it gets the value and then prints "main is dead" */
            System.out.println(Thread.currentThread().getName()+ " returned value is " + i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        /** shutdown() doesn't let any more tasks to enter the queue, but waits until all the tasks
         * that are already in the queue are finished - and then shuts down the ES */
        es.shutdown();
        System.out.println("main is dead");
    }
}
