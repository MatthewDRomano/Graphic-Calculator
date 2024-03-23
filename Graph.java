import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;


public class Graph extends JPanel {
    //make the functions either variable / enum or user inputted
    private final int WIDTH = 400, HEIGHT = 400;
    public static double zoomFactor = 10;// FIX
    private Point distanceDragged;
    private Point offset;
    private Integer[] yCoords; 

    public Graph(int desiredFunc) {
        setBounds(100,50,WIDTH, HEIGHT);
        offset = new Point(WIDTH/2,HEIGHT/2);
        yCoords = new Integer[WIDTH];
        distanceDragged = new Point(0,-0);
        calculateYVals(); 
        //System.out.print(Integral(0, 1));     
        addMouseListener(ma);
        addMouseMotionListener(ma);
        addMouseWheelListener(ma);
    }

    public void calculateYVals() { // FOR DISPLAY
         int zoomInt = (int)zoomFactor;
        for (int x = (int)(-offset.x+distanceDragged.x); x < (offset.x+distanceDragged.x); x++)
            yCoords[x+offset.x-distanceDragged.x] = (int)((Functions.Parabola(x, 0, -0)*zoomFactor) - distanceDragged.y + offset.y); //  x = 0  is center so offset ensures graph has negative values          
            
    }
    // public double Integral(int a, int b) {
    //     //if statement to use simpsons rule if log based / trig function
    //     double deltaX = 0.001;
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

        //draw Equation
        g2.setColor(Color.red);
        
        for (int x = 0; x < WIDTH-1; x++) {
            if (yCoords[x] != null && yCoords[x+1] != null) {
                //System.out.println(yCoords[x] - offset.y + distanceDragged.y + " ");
                g2.drawLine(x, HEIGHT-yCoords[x], x+1, HEIGHT-yCoords[x+1]);//on screen coords
            }
                
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
                zoomFactor = (e.getWheelRotation() < 0) ? zoomFactor/2 : zoomFactor*2;;
                calculateYVals();
                repaint();
        }
    };
}