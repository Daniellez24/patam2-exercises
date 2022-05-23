package test;

import java.util.concurrent.RecursiveTask;

public class ParMaxSearcher extends RecursiveTask<Integer> {

    BinTree tree;

    public ParMaxSearcher(BinTree tree) {
        this.tree = tree;
    }

    /** when calling submit in the MainTrain (line 17), compute() is executed:
     * if the l and r are null, this tree is a leaf - return the leaf's value. else - we separate the tree to l and r,
     * the l is sent to the thread pool and computed there (fork), and the r is computed in the current thread (compute).
     * then we take the maximum between the r and l, and then the max between the result and the value of this node (tree) */
    @Override
    protected Integer compute() {
        if(tree.getLeft() == null && tree.getRight() == null) // this is a leaf
            return tree.get();
        /** not a leaf. this is a full binary tree - a node that is not a leaf, has 2 sons.
         * we'll want to calculate the right sub-tree and the left sub-tree at the same time (concurrently) */
        ParMaxSearcher l = new ParMaxSearcher(tree.getLeft());
        ParMaxSearcher r = new ParMaxSearcher(tree.getRight());
        /** fork takes the method compute of the l tree, and runs it concurrently in the fork-join pool */
        l.fork();
        /** the current thread will run compute() of the r tree */
//        r.compute();
//        /** join returns the Integer of l's compute method if it's ready, and if not - it waits until it's ready */
//        l.join();

        /** takes the max value between the current tree (the data member here), and the max value returned from r.compute
         * and the max value returned from l.join */
        return Math.max(tree.get(), Math.max(r.compute(), l.join()));
    }
}
