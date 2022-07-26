package test;

import java.util.concurrent.ArrayBlockingQueue;

/** ThreadPool using our GenericActiveObject */
public class MyThreadPool {
	
	int maxSize; // max number of threads we want to open
	ArrayBlockingQueue<GenericActiveObject> aos; // the "threads"
	GenericActiveObject main; // the main thread that puts tasks to the threadPool threads
	
	public MyThreadPool(int maxSize) {
		this.maxSize=maxSize;
		aos=new ArrayBlockingQueue<>(maxSize);
		main=new GenericActiveObject();
	}

	/** puts a Runnable into one of the ActiveObjects */
	private void assign(Runnable task) {
		if(aos.size()<maxSize) {
			GenericActiveObject ao=new GenericActiveObject();
			ao.execute(task);
			aos.add(ao);
		}else { // if we reached maxSize, reuse the ActiveObjects: put the Runnable in the min AO available
			GenericActiveObject min=null;
			int minSize=Integer.MAX_VALUE;
			for(GenericActiveObject ao : aos) {
				if(ao.size()<minSize) {
					minSize=ao.size();
					min=ao;
				}
			}
			min.execute(task);
		}
	}

	/** Runnable */
	public void execute(Runnable r) {
		/** code for the regular Future MyFutureA:
		 * new Thread(r).start() */
		// the "main thread" of the thread pool assigns tasks to the aos
		main.execute(()->assign(r));
	}

	/** Callable */
	public <V> MyFuture<V> submit(MyCallable<V> c){
		MyFuture<V> f=new MyFuture<>();
		/** execute to a Runnable that takes the f and set it to the return value from call() */
		execute(()->f.set(c.call()));		
		return f;
	}
	
	public void shutdownNow() {
		for(GenericActiveObject ao : aos) {
			ao.shutdownNow();
		}
		main.shutdownNow();
	}
	
	public void shutdown() {
		for(GenericActiveObject ao : aos) {
			ao.shutdown();
		}
		main.shutdown();
	}
	
}
