package test;

import java.util.List;

public class Bad {

    private static double EucDistance(Point a, Point b){
        return Math.sqrt(Math.pow(a.x-b.x, 2) + Math.pow(a.y-b.y, 2));
    }

    private static double sumDist(Point a, List<Point> ps){
        double sum = 0;
        for(Point pi : ps)
            sum += Math.pow(EucDistance(a, pi), 2);
        return sum;
    }

    /** we want to find the Point that is the center Point of all Points in ps = the sum of the distances from this Point to
     the other Points (סכום המרחקים הריבועיים) -> the Point that has the minimum sum is the center Point we want to return */
    public static Point minSqrSum(List<Point> ps) {
        double min = Double.MAX_VALUE;
        Point minPoint = null;
        for(Point pi : ps){
            if(min > sumDist(pi, ps)){
                min = sumDist(pi, ps);
                minPoint = pi;
            }
        }
        return minPoint;
    }

}
