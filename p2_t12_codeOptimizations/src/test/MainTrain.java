package test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainTrain {

    public static void main(String[] args) {
        // random input
        Random r = new Random();
        List<Point> ps = new ArrayList<>();

        /** generating 10,000 Points: running code 10,000 and up, makes it be compiled the next times we run it, and we run the compiled code.
         * so the functions in Bad and Good classes are running the compiled version, thanks to JIT */
        for (int i = 0; i < 10000; i++) {
            // x,y values of each Point is a random number between -1000,1000
            ps.add(new Point(-1000 + r.nextInt(2001) , -1000+r.nextInt(2001)));
        }

        // time for bad code
        long bad = System.nanoTime();
        Point br = Bad.minSqrSum(ps);
        bad = System.nanoTime() - bad;

        // time for OPT code
        long good = System.nanoTime();
        Point gr = Good.minSqrSum(ps);
        good = System.nanoTime() - good;

        if(gr != br){
            System.out.println("your function did not get the same result (-30)");
            System.out.println("done");
            return;
        }

        DecimalFormat f = new DecimalFormat("#,###.##");
        System.out.println("bad time:\t" + f.format(bad));
        System.out.println("your time:\t" + f.format(good));
        double optRate = (double) bad/good;
        System.out.println("opt rate: " + f.format(optRate));
        if(optRate < 4){
            System.out.println("you can do better optimizations (-" + (Math.round(30*(4-optRate)/4)) + ")");
        }
        System.out.println("done");

    }

}
