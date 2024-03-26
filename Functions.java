public class Functions {
    public static double Parabola(double x, int xShift, int yShift) {
        return Math.pow((x-xShift), 2) + yShift;
    }
    public static double Line(double x, int xShift, int yShift) {
        return (x-xShift) + yShift;
    }
    public static double SquareRoot(double x, int xShift, int yShift) {
        return Math.sqrt(x-xShift) + yShift;
    }
    public static double ThirdDegree(double x, int xShift, int yShift) {
        return Math.pow(x-xShift, 3) + yShift;
    }
    public static double NaturalLog(double x, int xShift, int yShift) {
        return ((x-xShift) == 0) ? Double.NaN : Math.log(x-xShift) + yShift;
    }
     public static double Rocket(double x, int xShift, int yShift) {
        return 1/Math.pow(x, 2);
    }
    public static double Sine(double x, int xShift, int yShift) {
        return (Math.sin((x-xShift)*Math.PI/180) + yShift);
    }
    public static double cbrt(double x, int xShift, int yShift) {
        return Math.cbrt(x-xShift) + yShift;
    }
    public static double Derivative(double x) {
        double deltaX = 0.001;
        return (Parabola(x+deltaX, 0, 0) - Parabola(x, 0, 0))/deltaX;
    }
    public static double Integral(double a, double b) {
        //if statement to use simpsons rule if not log based / trig function
        if (Double.isNaN(a) || Double.isNaN(b)) return Double.NaN;
        if (b < a) return -Integral(b, a);
        double deltaX = 0.0001;
        double ans = 0;
        for (; a < b; a += deltaX)
            ans += Functions.Parabola(a, 0, 0) + Functions.Parabola(a + deltaX, 0, 0);
        return ans * 0.5 * deltaX;
    }
}