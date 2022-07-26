package test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GenericActiveObject {

	Thread t;
	BlockingQueue<Runnable> q;
	volatile  boolean stop;
	
	public GenericActiveObject() {
		q=new ArrayBlockingQueue<>(100);
		stop=false;
		t=new Thread(()->{
			while(!stop) {
				try {q.take().run();} catch (InterruptedException e) {}
			}
		});
		t.start();
	}

	/** for Runnable */
	public void execute(Runnable r) {

		try {q.put(r);} catch (InterruptedException e) {}
	}

	public void shutdownNow() {
		stop=true;
		t.interrupt();
	}
	
	public void shutdown() {
		/** inserting a task, so if the queue is empty and stuck on take(), now it's not empty and will run the task */
		execute(()->stop=true);
	}

	/** for Callable */
	public <V> MyFuture<V> submit(MyCallable<V> c){
		MyFuture<V> f=new MyFuture<>();
		/** we want to insert the Callable into the queue, but the queue is queue of Runnable.
		 * so we insert the Callable into a Runnable and call execute that puts it in the queue */
		execute(()->f.set(c.call()));
		return f;
	}
	
	public int size() {
		return q.size();
	}
	
}
