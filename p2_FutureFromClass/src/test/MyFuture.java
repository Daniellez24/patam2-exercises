package test;

import java.util.function.Consumer;
import java.util.function.Function;

/** mimic of the Completable Future */
public class MyFuture<V> {
	V v;

	Runnable r;

	/** the set-run chain of actions will begin in the ThreadPool's submit. submit is the first one
	 * that calls set(), and this set() runs r, this r calls set(), that runs r and on...
	 * every CF runs the set() of the CF that it returns - which is the next CF */
	public void set(V v) {
		this.v=v;
		if(r!=null)
			/** whenever the ThreadPool set the v, we'll run the Runnable with the desired action */
			r.run();
	}
	

	public <R> MyFuture<R> thenApply(Function<V, R> func) {
		MyFuture<R> fr=new MyFuture<>();
		// when we get the value, we need to set it into the future.
		/** we would want to do:
		 * R r = func.apply(v);
		 * fr.set(r);
		 * but we can't do it here, because then we have to wait until v is ready. we want to do it in set().
		 * so we'll define a Runnable with this code, and run() the Runnable in set() ! */
		r=()->fr.set(func.apply(v));
		return fr;
	}
	
	public void thenAccept(Consumer<V> c) {
		/** same logic as thenApply */
		r=()->c.accept(v);
	}

}
