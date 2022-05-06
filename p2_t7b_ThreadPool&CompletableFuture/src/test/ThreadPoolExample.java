package test;

import java.util.concurrent.*;
import java.util.function.Supplier;

public class ThreadPoolExample {

//    /** Example 1: Runnable (no return value from run()) */
//    public static class ExampleTask implements Runnable{
//
//        int id;
//
//        public ExampleTask(int id) {
//            this.id = id;
//        }
//
//        @Override
//        public void run() {
//            // simulation of a task that takes a lot of time
//            System.out.println("task started " + id);
//
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {}
//
//            System.out.println("task finished " + id);
//        }
//    }

    /** Example 2: Callable (there's a return value from call()) AND Supplier*/

    public static class ExampleTask2 implements Callable<Integer>, Supplier<Integer> {


        @Override
        public Integer call() throws Exception {
            // simulates a long calculation that in the end returns the value 42
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}
            return 42;
        }

        // get() by Supplier is like call(), but doesn't throw exception
        @Override
        public Integer get() {
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

        /** CompletableFuture puts the task "new ExampleTask2" into the es with supplyAsync which returns CompletableFuture. when the CF gets the Integer,
         * it runs the function of thenApply with the received Integer and returns a CompletableFuture<Integer>.
         * the second thenApply's function takes the Integer and transforms it into a String, and returns a CompletableFuture<String>.
         * finally, thenAccept which receives a Consumer, runs the Consumer's function on the received String */
        CompletableFuture.supplyAsync(new ExampleTask2(), es).thenApply(x -> x*2).thenApply(x -> "the value is "+ x).thenAccept(s -> System.out.println(s));

        es.shutdown();
        System.out.println("main is dead");
        /** Attention: the main thread doesn't have to wait to the returned value and can die before the value is returned.
         * the thread from ES who runs the ExampleTask2 has all the instructions that he has to do when he gets the value from
         * CompletableFuture */
    }
}
