public class Functions {
    public static double Parabola(double x, int xShift, int yShift) {
        return Math.pow(x-xShift, 2) + yShift/Graph.zoomFactor;
    }
    public static double Line(double x, int xShift, int yShift) {
        return (x-xShift) + yShift/Graph.zoomFactor;
    }
    public static double SquareRoot(double x, int xShift, int yShift) {
        return Math.sqrt(x-xShift) + yShift/Graph.zoomFactor;
        //return (ans == Double.NaN) ? null : ans;// move this check into Graph class where Function is called
    }
    public static double ThirdDegree(double x, int xShift, int yShift) {
        return Math.pow(x-xShift, 3) + yShift/Graph.zoomFactor;
    }
    public static double NaturalLog(double x, int xShift, int yShift) {
        return ((x-xShift) == 0) ? Double.NaN : Math.log(x-xShift) + yShift/Graph.zoomFactor;
    }
    // public static double Special(double x, int xShift, int yShift) {
    //     return Math.pow(x, 2) / x+10;
    // }
    public static double Sine(double x, int xShift, int yShift) {
        return Math.sin((x-xShift)*Math.PI/180) + yShift/Graph.zoomFactor;
    }
    public static double cbrt(double x, int xShift, int yShift) {
        return Math.cbrt(x-xShift) + yShift/Graph.zoomFactor;
    }
}