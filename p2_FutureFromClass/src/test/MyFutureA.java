package test;

/** mimic of the regular Future */
public class MyFutureA<V> {
    V v;

    public synchronized void set(V v){
        this.v = v;
        /** wakes all the threads that are in wait() in the get method */
        notifyAll();
    }

    public V get(){
        /** double check locking */
        if(v == null) {
            synchronized (this) {
                if (v == null)
                    try {
                        /** blocking call until we have the value of v */
                        wait();
                    } catch (InterruptedException e) {
                    }
            }
        }
        return v;
    }
}
