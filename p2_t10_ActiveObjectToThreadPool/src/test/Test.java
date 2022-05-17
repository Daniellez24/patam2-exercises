package test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        // Example with FixedThreadPool
        /**
        ExecutorService es = Executors.newFixedThreadPool(3);

        CompletableFuture.supplyAsync(()->{
            try{ Thread.sleep(5*1000); } catch(InterruptedException e) {}
            return "42";
        }, es) // returns CompletableFuture<String>
        .thenApply(s -> Integer.parseInt(s)) // CF<Integer>
        .thenApply(x -> x*2) // CF<Integer>
        .thenAccept(ans -> System.out.println("The result is " + ans));

        es.shutdown();
        System.out.println("main is done");
         */

        // Example with MyThreadPool we created
        MyThreadPool tp = new MyThreadPool(100, 3);
        tp.submit(()->{
            try { Thread.sleep(5*1000); } catch (InterruptedException e) {}
            return "42";
        }) // Future<String>
                .thenApply(s -> Integer.parseInt(s)) // F<Integer>
                .thenApply(x -> x*2) // F<Integer>
                .thenAccept(ans -> System.out.println("The result is " + ans));

        tp.shutdown();
        System.out.println("main is done");
    }

}
