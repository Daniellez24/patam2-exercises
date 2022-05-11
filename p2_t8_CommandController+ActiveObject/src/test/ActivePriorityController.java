package test;

import java.util.concurrent.PriorityBlockingQueue;

public class ActivePriorityController {

    /** creating a private helper class that saves both Command and its priority - Priority Command */
    private class PCommand{
        public Command c;
        public int priority;

        public PCommand(Command c, int priority) {
            this.c = c;
            this.priority = priority;
        }
    }

    /** since we are using threads, we should use a Thread-safe priority queue: BlockingPriorityQueue from
     * java.util.concurrent. the queue will hold PCommand objects, so it has both the command and its priority */
    PriorityBlockingQueue<PCommand> q;
    Thread t;
    // volatile - only one true source
    volatile boolean stop;

    public ActivePriorityController() {
        // setting initial capacity AND comparator with the compare method using lambda expression
        q = new PriorityBlockingQueue<PCommand>(100, (a,b) -> a.priority - b.priority);

        t = new Thread(() -> {
            while(!stop){
                /** take() is a blocking call - if the queue is empty, it'll wait.
                 * if it's not empty - it will pull from the queue.
                 * take() is better than pull() beacuse pull() returns null if the queue is empty, and then we'll have
                 * to manage the thread (tell it to sleep, interrupt it..) */
            try {
                q.take().c.execute();
            } catch (InterruptedException e) {}
        }
        });
        t.start();
    }

    public void addCommand (Command c, int priority){
        // adding a PCommand to the queue, with the given Command and priority
        q.add(new PCommand(c,priority));
    }

    public void close(){
        /** adding a PCommand that changes stop to true, with the priority we want -
         * if we want it to stop immediately, we'll set the priority to 0.
         * if we want the queue to finish with all the Command that are already in the queue, we'll set it to MAX_VALUE
         * so the stop Command will execute last */
        q.add(new PCommand(()-> stop=true, Integer.MAX_VALUE));
    }
}
