import java.lang.reflect.*;
public class Functions {
    public static String function = "Line";//maybe make empty at start
    public static String[] functionsList = {"Line", "Parab", "Sqrt", "Cube", "Cbrt", "Bird", "Rocket", "Sine"};

    public static double f(double x) { // make these return double.NaN if applicable instead of error catch in graph
        switch (function) {
            case "Line": return x;
            case "Parab": return Math.pow(x, 2);
            case "Sqrt":  return Math.sqrt(x);
            case "Cube": return Math.pow(x, 3);
            case "Rocket": return 1/Math.pow(x, 2);
            case "Cbrt": return Math.cbrt(x);
            case "Bird": return Math.pow(Math.cbrt(x), 2) ;
            case "Sine": return Math.sin(x);//fix to work with rad view?
            case "NaturalLog": return (x == 0) ? Double.NaN : Math.log(x);
            default: return Double.NaN;
        }
    }

    public static double Derivative(double x) {//poopy butt when x approaches non differentiable point
        double deltaX = 0.0001;
        //double derivLeft = (f(x) - f(x-deltaX))/deltaX;
        //double derivRight = (f(x+deltaX) - f(x))/deltaX;
        //return (Math.abs(derivLeft-derivRight) > 1) ? Double.NaN : derivRight;
        return (f(x+deltaX) - f(x))/deltaX;
    }
    public static double Integral(double a, double b) {
        if (Double.isNaN(a) || Double.isNaN(b)) return Double.NaN;
        if (b < a) return -Integral(b, a);
        double deltaX = 0.00001;
        double ans = 0;
        for (; a < b; a += deltaX)
            ans += f(a) + f(a + deltaX);
        return ans * 0.5 * deltaX;
    }
}