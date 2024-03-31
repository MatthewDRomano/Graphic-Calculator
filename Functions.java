import java.lang.reflect.*;
public class Functions {
    //public static Method func;
    public static String function = "Line";//maybe make empty at start
    public static  double f(double x, double xShift, double yShift) {
        switch (function) {
            case "Line": return (x-xShift) + yShift;
            case "Parab": return  Math.pow(x-xShift, 2) + yShift;
            case "Sqrt":  return Math.sqrt(x-xShift) + yShift;
            case "Cube": return Math.pow(x-xShift, 3) + yShift;
            case "Rocket": return 1/Math.pow(x-xShift, 2) + yShift;
            case "Cbrt": return Math.cbrt(x-xShift) + yShift;
            case "Bird": return Math.pow(Math.cbrt(x-xShift), 2) + yShift;
            case "Sine": return Math.sin((x-xShift)) + yShift;//fix to work with rad view?
            case "NaturalLog": return ((x-xShift) == 0) ? Double.NaN : Math.log(x-xShift) + yShift;
            default: return Double.NaN;
        }
    }
    public static void updateFunction(String newFunction) {
        function = newFunction;
    }
    // public static void updateFunction(String funcName) { // sets function
    //     try {
    //         func = Functions.class.getMethod(funcName, double.class, double.class, double.class); // Class<?>[] {arg1.class, arg2.class...} works too
    //     } catch (IllegalArgumentException e) { e.printStackTrace(); } 
    //       catch (SecurityException e) { e.printStackTrace(); } 
    //       catch (NoSuchMethodException e) { e.printStackTrace(); }
    // }
    // public static double f(double x, double xShift, double yShift) { // does function
    //     Object ans = Double.NaN;
    //     try { ans = (Double)func.invoke(null, x, xShift, yShift); }//double, double, double // null since static (no instance of Functions)
    //         catch (IllegalAccessException e) { e.printStackTrace(); } 
    //         catch (InvocationTargetException e) { e.printStackTrace(); } 
    //     return (Double)ans;
    // }
    // public static double Parab(double x, double xShift, double yShift) {
    //     return Math.pow((x-xShift), 2) + yShift;
    // }
    // public static double Line(double x, double xShift, double yShift) {
    //     return (x-xShift) + yShift;
    // }
    // public static double Sqrt(double x, double xShift, double yShift) {
    //     return Math.sqrt(x-xShift) + yShift;
    // }
    // public static double Cube(double x, double xShift, double yShift) {
    //     return Math.pow(x-xShift, 3) + yShift;
    // }
    // public static double NaturalLog(double x, double xShift, double yShift) {
    //     return ((x-xShift) == 0) ? Double.NaN : Math.log(x-xShift) + yShift;
    // }
    //  public static double Rocket(double x, double xShift, double yShift) {
    //     return 1/Math.pow(x-xShift, 2) + yShift;
    // }
    // public static double Sine(double x, double xShift, double yShift) {
    //     return (Math.sin((x-xShift)*Math.PI/180) + yShift);
    // }
    // public static double Cbrt(double x, double xShift, double yShift) {
    //     return Math.cbrt(x-xShift) + yShift;
    // }
    // public static double Bird(double x, double xShift, double yShift) {
    //     return Math.pow(Cbrt(x, 0,  0), 2);
    // }
    public static double Derivative(double x) {//poopy butt when x approaches non differentiable point
        double deltaX = 0.0001;
        return (f(x+deltaX, 0, 0) - f(x, 0, 0))/deltaX;
    }
    public static double Integral(double a, double b) {
        //if statement to use simpsons rule if not log based / trig function?
        if (Double.isNaN(a) || Double.isNaN(b)) return Double.NaN;
        if (b < a) return -Integral(b, a);
        double deltaX = 0.00001;
        double ans = 0;
        for (; a < b; a += deltaX)
            ans += f(a, 0, 0) + f(a + deltaX, 0, 0);
        return ans * 0.5 * deltaX;
    }
}