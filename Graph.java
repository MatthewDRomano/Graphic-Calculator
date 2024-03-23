import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Graph extends JPanel {
    //fix Zoom / make the functions either variable / enum or user inputted
    private final int WIDTH = 400, HEIGHT = 400;
    private static double zoomFactor = 1;// chanage window?  zoom changes 400*400 to wtv
    private Point distanceDragged; 
    private Point offset; //  x = 0  is center so offset ensures graph has negative values       
    private Integer[] yCoords; 

    public Graph(int desiredFunc) {
        setBounds(100,50,WIDTH, HEIGHT);
        offset = new Point(WIDTH/2,HEIGHT/2);
        yCoords = new Integer[WIDTH];
        distanceDragged = new Point(0,-0);
        calculateYVals(); 

        // System.out.print(Integral(0, 1));     
        //System.out.println(Derivative(5.0));
        addMouseListener(ma);
        addMouseMotionListener(ma);
        addMouseWheelListener(ma);
        repaint();
    }

    public void calculateYVals() {
        // for (int x = (int)(-offset.x+distanceDragged.x); x < (offset.x+distanceDragged.x); x++) {
        //    yCoords[(x+offset.x-distanceDragged.x)] = (int)((Functions.Parabola(x, 0, 0)) - distanceDragged.y + offset.y);    
        // }
        //OR
        for (int i = 0; i < WIDTH; i++) {
            double y, x = (int)(-offset.x+distanceDragged.x) + i;   
            x = x / zoomFactor; //if window goes from -10 to 10 down to -1 to 1, zoom is x10 AKA / 10   
            y = Functions.Parabola(x, 0, 0); // shifts must be affected by zoomFactor too
            yCoords[i] = (Double.isNaN(y)) ? null : (int)((y - (distanceDragged.y/zoomFactor) + (offset.y/zoomFactor)) * zoomFactor); 
            //System.out.println(x + " " + yCoords[i]);
        }
    }
    
    public double Derivative(double x) {
        double deltaX = 0.00001;
        return (Functions.Parabola(x+deltaX, 0, 0) - Functions.Parabola(x, 0, 0))/deltaX;
    }
    // public double Integral(int a, int b) {
    //     //if statement to use simpsons rule if not log based / trig function
    //     double deltaX = 0.1;
    //     double ans = 0;
    //     for (; a < b; a += deltaX)
    //         ans += Functions.Line(a, 0, 0) + Functions.Line(a + deltaX, 0, 0);
    //     return ans * 0.5 * deltaX;
    // }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        //draw axis
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g2.setColor(Color.black);
        //g2.setStroke(new BasicStroke(1/(float)zoomFactor));
        g2.drawLine(WIDTH/2-distanceDragged.x, 0, WIDTH/2-distanceDragged.x, HEIGHT);
        g2.drawLine(0, HEIGHT/2+distanceDragged.y, WIDTH, HEIGHT/2+distanceDragged.y);
        
        //draw tiny lines - must be centered from origin
        int interval = (int)(10*zoomFactor);
        for (int i = 0; i < WIDTH/2; i++)
            if (i % interval == 0) {
                g2.drawLine(offset.x+(i-distanceDragged.x%interval), offset.y+distanceDragged.y-3, offset.x+(i-distanceDragged.x%interval), offset.y+distanceDragged.y+3);
                g2.drawLine(offset.x-distanceDragged.x-3, offset.y+(i+distanceDragged.y%interval), offset.x-distanceDragged.x+3, offset.y+(i+distanceDragged.y%interval));
                i *= -1;
                g2.drawLine(offset.x+(i-distanceDragged.x%interval), offset.y+distanceDragged.y-3, offset.x+(i-distanceDragged.x%interval), offset.y+distanceDragged.y+3);
                g2.drawLine(offset.x-distanceDragged.x-3, offset.y+(i+distanceDragged.y%interval), offset.x-distanceDragged.x+3, offset.y+(i+distanceDragged.y%interval));
                i *= -1;
            }

        //draw Equation
        g2.setColor(Color.red);
        for (int x = 0; x < WIDTH-1; x++) {
            if (yCoords[x] == null || yCoords[x+1] == null) continue;
                //System.out.println(yCoords[x] - offset.y + distanceDragged.y + " ");
                g2.drawLine(x, HEIGHT-yCoords[x], x+1, HEIGHT-yCoords[x+1]);//on screen coords
            
        }                          
    }

    MouseAdapter ma = new MouseAdapter() {
        Point startCoords;

        @Override
        public void mousePressed(MouseEvent e) {
            if (!SwingUtilities.isLeftMouseButton(e)) return;
            startCoords = new Point(e.getPoint().x - offset.x + distanceDragged.x, HEIGHT - e.getPoint().y - offset.y + distanceDragged.y);
        }
        @Override
        public void mouseReleased(MouseEvent e) { }
        @Override
        public void mouseDragged(MouseEvent e) {
            Point curCoords = new Point(e.getPoint().x - offset.x + distanceDragged.x, HEIGHT - e.getPoint().y - offset.y + distanceDragged.y);

            distanceDragged.x -= curCoords.x - startCoords.x;
            distanceDragged.y -= curCoords.y - startCoords.y;

            calculateYVals();
            repaint();
        }
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            zoomFactor = (e.getWheelRotation() < 0) ? zoomFactor/1.1 : zoomFactor*1.1;
            zoomFactor = (zoomFactor < 0.1) ? 0.1 : zoomFactor;
            calculateYVals();
            repaint();
        }
    };
}