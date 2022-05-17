package test;

import java.util.function.Consumer;
import java.util.function.Function;

public class Future<V> {

    V v = null;
    Runnable task;

    /** calling notify has to be synchronized */
    public synchronized void set(V v){
        this.v = v;
        /** run the instructions from thenApply and thenAccept, now that the v is set */
        task.run();
        /** wakes all the thread that are stuck on wait (in get()) */
        notifyAll();
    }

    public V get(){
        /** double-checked locking
         * we want to wait until v isn't null in order to return it here. the set method will get us out of wait */
        if(v == null){
            synchronized(this) {
                try {
                    if(v == null)
                        wait();
                } catch (InterruptedException e) {}
            }
        }
        return v;
    }

    public <R> Future<R> thenApply(Function<V,R> func){
        Future<R> f = new Future<>();
        /**  f.set(func.apply(v)); ----> we can't do this because we want thenApply to return immediately, even if we don't have the v yet.
         * so we'll insert this line into a Runnable. this way, we don't run this line here, but we run it in "set" method (after we get the v),
         * by calling task.run() */
        task = ()-> f.set(func.apply(v));
        /** return the Future immediately, even if it's "empty" (no v value yet). and we wrote the instructions that it has to do
         * once it gets the v */
        return f;
    }
    
    public void thenAccept(Consumer<V> c){
        /** like in thenApply - we insert the instructions into task, that will run after we get the value of v in set() */
        task = ()-> c.accept(v);
    }
}
