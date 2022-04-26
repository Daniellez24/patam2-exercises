package test;

/** Solution 3: Object adapter - receives a MyTask object, and implements Runnable */
public class RunnableTask implements Runnable{

    MyTask t;

    public RunnableTask(MyTask t) {
        this.t = t;
    }

    @Override
    public void run() {
        t.doAction();
    }
}
