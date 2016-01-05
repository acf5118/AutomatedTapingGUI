/**
 * Created by Jon on 1/5/2016.
 */

import java.lang.Math;

public class PracticeCode {

    public static void main(String[] args)
    {
        System.out.println(test());
    }
    static double test()
    {
        double a = 0;

       for(double b = 0;b <= Math.PI*2;b += .1)
       {
            a = Math.PI / b;
           //System.out.println(a);
       }
       return Math.sin(a);
    }
}
