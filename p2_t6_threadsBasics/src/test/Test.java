package test;

public class Test {

    public static void main(String[] args) {
        System.out.println(Thread.activeCount()); // prints how many open threads are there right now
        System.out.println(Thread.currentThread().getName()); // returns a reference to the current running thread (main)

        MyTask t = new MyTask(0);
        /* these lines cause an infinite loop that is stuck in doAction, and the "hello world" will never be printed.
        if the doAction will run in the background, another thread will be able to call stopMe
        t.doAction(); // stuck here
        t.stopMe();
        System.out.println("hello world!");
         */

        /** Solution 4: the same as solution 3, but with an anonymous class/lambda expression.
         * if we use it only once, there's no need to create a new class "RunnableTask" since Runnable is a functional interface */

        /** lambda expression */
        Thread t0 = new Thread(()->t.doAction());
        /** anonymous class */
//        Thread t0 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                t.doAction();
//            }
//        });
        t0.start();

        /** Solution 3: using object adapter with the class RunnableTask */
//        Thread t0 = new Thread(new RunnableTask(t));
//        // start() calls to RunnableTask.run() in the background, which calls doAction()
//        t0.start();

        /** Solution 2: MyTask implements Runnable */
//        // create new thread with the Runnable t (MyTask)
//        Thread t0 = new Thread(t);
//        // t0 runs in the background, and runs the Overridden run() method
//        t0.start();

        /** Solution 1: MyTask extends Thread */
//        // start() creates a new thread that runs the method run() in the background
//        t.start();


        try {
            // asking from the thread main to go to sleep for 2 seconds
            Thread.sleep(2000);
        } catch (InterruptedException e) { }
        // the thread main stops the loop
        t.stopMe();

        /** Attention: if we run the program few times, we'll get different values for x. why?
         * beacuse the main thread goes to sleep for at least 2 secs, and then enters to the *ready queue*, and
         * waits there until it's its turn to run (and change its state to "Running") */
        System.out.println("x = " + t.getX());
        System.out.println("done");
    }
}
