package test;

/** Solution 3: using Object adapter - by creating the class RunnableTask */
public class MyTask {

    int x;
    volatile boolean stop;

    public MyTask(int x) {
        this.x = x;
    }

    public void doAction() {
        System.out.println(Thread.currentThread().getName());
        while(!stop){
            x++;
        }
    }

    public void stopMe() {
        stop = true;
    }

    int getX() {
        return x;
    }

}

/** Solution 2: using "implements Runnable" instead of "extends Thread", in order to not waste the only inheritance.
 * THE PROBLEM: we changed MyTask!!! not open/close */
//public class MyTask implements Runnable{
//
//    int x;
//    volatile boolean stop;
//
//    public MyTask(int x) {
//        this.x = x;
//    }
//
//    public void doAction() {
//        System.out.println(Thread.currentThread().getName());
//        while(!stop){
//            x++;
//        }
//    }
//
//    public void stopMe() {
//        stop = true;
//    }
//
//    int getX() {
//        return x;
//    }
//
//    @Override
//    public void run() {
//        doAction();
//    }
//}

/** Solution 1: the problem with this solution, is that we use our only inheritance! (extends Thread) */
//public class MyTask extends Thread{
//
//    int x;
//    volatile boolean stop;
//
//    public MyTask(int x) {
//        this.x = x;
//    }
//
//    /* the problem: the one that enters to doAction, cannot exit it unless someone *else* changes
//    the value of "stop" to true using the stopMe method */
//    public void doAction() {
//        System.out.println(Thread.currentThread().getName());
//        while(!stop){
//            x++;
//        }
//    }
//
//    public void stopMe() {
//        stop = true;
//    }
//
//    int getX() {
//        return x;
//    }
//
//    // Thread implements Runnable, and we Override its run() method
//    @Override
//    public void run(){
//        doAction();
//    }
//}
