package test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
	
	public static class DeepThought implements MyCallable<String>{
		@Override
		public String call() {
			try {Thread.sleep(5*1000);} catch (InterruptedException e) {}
			return "42";
		}
	}

	public static void main(String[] args) {
		MyThreadPool tp=new MyThreadPool(4);
		/** regular Future (MyFutureA)*/
//		MyFuture<String> f=tp.submit(new DeepThought());
//		System.out.println("main is doing things in parallel...");
//		String s=f.get();
//		Integer i=Integer.parseInt(s);
//		System.out.println("result is "+ i*2);
//		System.out.println("main is done");
		
		/** Completable Future (MyFuture) */
/*		MyFuture<String> f=tp.submit(new DeepThought());
		MyFuture<Integer> fi=f.thenApply(s->Integer.parseInt(s));
		MyFuture<Integer> fi2=fi.thenApply(x->x*2);
		fi2.thenAccept(i->System.out.println("result is "+i));
		System.out.println("main is done");
*/		 
		
		tp.submit(new DeepThought())
		  .thenApply(s->Integer.parseInt(s))
		  .thenApply(x->x*2)
		  .thenAccept(i->System.out.println("result is "+i));
		
		tp.shutdown();
		
		System.out.println("main is done");


	}

}
