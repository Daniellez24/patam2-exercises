package test;

import java.util.List;
/** in the beginning, Good class equals Bad class. we want to change Good to be better then Bad */
public class Good {

    /** make min static to stop redundant calculations */
    static double min;

    private static double EucDistance(Point a, Point b){
        /** here in Bad we made sqrt, and in sumDist we pow by 2 - we can remove them.
         * in addition - we can replace the pow call with multiplying. it doesn't make much difference because since the 8th version,
         * the JVM does this transfer automatically. but by doing this we also cancel the calling for the function Math.pow, which is better
         * than calling it (even though the Math functions are natives, already compiled) */

        return (a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y);
    }

    private static double sumDist(Point a, List<Point> ps){
        double sum = 0;
        for(Point pi : ps) {
            sum += EucDistance(a, pi);
            /** if sum is already bigger then the min we already have, we can immediately return the MAX_VALUE and not sum -
             * we cut some calculations - BETTER TIMING!!! MAKES A DIFFERENCE!! */
            if(sum > min)
                return Double.MAX_VALUE;
        }
        /** instead of calling EucDist, we just do the calculation here - we made it inLine (the JVM does it already, no big difference),
         * so we can keep the EucDist call */
//      for(Point b : ps)
//            sum += (a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y);
        return sum;

        /** few other tries: */

        /** using stream instead of for loop. it takes longer then before!!! very bad!! */
//        return ps.stream().map(pi->EucDistance(a,pi)).reduce(0.0, (x,y)->x+y);
        /** using parallelStream instead of Stream - still, very bad timing!! also we can't use threads in the question rules */
//        return ps.parallelStream().map(pi->EucDistance(a,pi)).reduce(0.0, (x,y)->x+y);


        /** loop unrolling (although the JVM does it automatically) so we can even stay with the code above - cleaner and more readable */
//        double sum0 = 0;
//        double sum1 = 0;
//        double sum2 = 0;
//        double sum3 = 0;
//        // we know we have 10,000 Points, an even number. no problem to make jumps by 2, and also by 4 - no remainder
//        for (int i = 0; i < ps.size(); i+=4) {
//            Point b = ps.get(i);
//            Point b1 = ps.get(i+1);
//            Point b2 = ps.get(i+2);
//            Point b3 = ps.get(i+3);
//            sum0 += (a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y);
//            sum1 += (a.x-b1.x)*(a.x-b1.x) + (a.y-b1.y)*(a.y-b1.y);
//            sum2 += (a.x-b2.x)*(a.x-b2.x) + (a.y-b2.y)*(a.y-b2.y);
//            sum3 += (a.x-b3.x)*(a.x-b3.x) + (a.y-b3.y)*(a.y-b3.y);
//        }
//
//        return (sum0+sum1)+(sum2+sum3); // make the calculations independent
    }


    public static Point minSqrSum(List<Point> ps) {
        /** as we know, the center Point should be around the center that is possible: we calculate the average x and y to create the
         * "imaginary" center Point. then, we will search for the real one in the area of the imaginary one */
        double avx = 0, avy = 0;
        for(Point pi : ps){
            avx += pi.x;
            avy += pi.y;
        }
        avx /= ps.size();
        avy /= ps.size();

        // the radius around the avx, avy
        int r = 100;

        /** make min a static variable at the top of the class, and when calculating distance between 2 Points when calling
         * sunDist in this function, if during the calculation we see we exceed the min already, we stop the current calculation immediately */
//        double min = Double.MAX_VALUE;
        min = Double.MAX_VALUE;

        Point minPoint = null;
        for(Point pi : ps){
            /** checking id the Point pi is in the radius around the imaginary center Point. if so - make the search.
             * this way we didn't change the algorithm, but we make it on fewer Points than before */
            if(pi.x>avx-r && pi.x<avx+r && pi.y>avy-r && pi.y<avy+r){

                /** saving sumDist in a local variable to avoid multiple function calls. so we call the function just once.
                 * NOTICE: by calling sumDist for every Point - we make doubled calculations.
                 * (once when iterating Point a, and calculate it with Point b, AND vice versa).
                 * we might think of saving the data in a matrix - but the memory allocation takes time! so it doesn't help, even worse timing */
                double d = sumDist(pi, ps);
                if(min > d){
                    min = d;
                    minPoint = pi;
                }

            }
        }
        return minPoint;
    }

}
