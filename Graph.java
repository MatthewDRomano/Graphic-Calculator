import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.*;

public class Graph extends JPanel { // make trig view
    private final int WIDTH = 350, HEIGHT = 300;
    private static double zoomFactor = 10; // changes window size (in calculations)
    private Point offset, distanceDragged;  //  x = 0  is center so offset ensures graph has negative values // drag is distance mouse drags      
    private Integer[] yCoords; 
    //Method f = Functions.class.getMethod("Parabola");

    public Graph() {
        setBounds(17, 17, WIDTH, HEIGHT);
        offset = new Point(WIDTH/2,HEIGHT/2);
        yCoords = new Integer[WIDTH];
        distanceDragged = new Point(0,0);
        calculateYVals(); 

        addMouseListener(ma);
        addMouseMotionListener(ma);
        addMouseWheelListener(ma);
        repaint();     
    }

    public void calculateYVals() {
        
        for (int i = 0; i < WIDTH; i++) {
            double y, x = (int)(-offset.x-distanceDragged.x) + i;   
            x = x / zoomFactor; //if window goes from -10 to 10 down to -1 to 1, zoom is x10 AKA / 10   
            y = Functions.Rocket(x, 0, 0);
            yCoords[i] = (Double.isNaN(y)) ? null : (int)((y + (distanceDragged.y/zoomFactor) + (offset.y/zoomFactor)) * zoomFactor); 
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.clearRect(0, 0, WIDTH, HEIGHT);
        
        //draw axis
        g2.setColor(Color.black);
        g2.drawLine(WIDTH/2+distanceDragged.x, 0, WIDTH/2+distanceDragged.x, HEIGHT);
        g2.drawLine(0, HEIGHT/2-distanceDragged.y, WIDTH, HEIGHT/2-distanceDragged.y);
        
        //draw tiny lines - must be centered from origin
        int interval = (int)Math.round(1*zoomFactor);
        for (int i = 0, j = 0; i < WIDTH/2; i++, j--) // i and j are x offsets from center i moves pos j moves neg to Math.Abs(WIDTH/2) (edge of screen)
            if (i % interval == 0) {
                g2.drawLine(offset.x+(i+distanceDragged.x%interval), offset.y-distanceDragged.y-3, offset.x+(i+distanceDragged.x%interval), offset.y-distanceDragged.y+3);
                g2.drawLine(offset.x+distanceDragged.x-3, offset.y+(i-distanceDragged.y%interval), offset.x+distanceDragged.x+3, offset.y+(i-distanceDragged.y%interval));
                g2.drawLine(offset.x+(j+distanceDragged.x%interval), offset.y-distanceDragged.y-3, offset.x+(j+distanceDragged.x%interval), offset.y-distanceDragged.y+3);
                g2.drawLine(offset.x+distanceDragged.x-3, offset.y+(j-distanceDragged.y%interval), offset.x+distanceDragged.x+3, offset.y+(j-distanceDragged.y%interval));
            }
            
        //draw Equation
        g2.setColor(Color.red);
        for (int x = 0; x < WIDTH-1; x++) {
            if (yCoords[x] == null || yCoords[x+1] == null) continue;
                g2.drawLine(x, HEIGHT-yCoords[x], x+1, HEIGHT-yCoords[x+1]);//on screen coords        
        }                          
    }

    MouseAdapter ma = new MouseAdapter() {
        Point startCoords;

        @Override
        public void mousePressed(MouseEvent e) {
            if (!SwingUtilities.isLeftMouseButton(e)) return;
            startCoords = new Point(e.getPoint().x - offset.x - distanceDragged.x, HEIGHT - e.getPoint().y - offset.y - distanceDragged.y);
        }
        @Override
        public void mouseDragged(MouseEvent e) {
            if (!SwingUtilities.isLeftMouseButton(e)) return;
            Point curCoords = new Point(e.getPoint().x - offset.x - distanceDragged.x, HEIGHT - e.getPoint().y - offset.y - distanceDragged.y);

            distanceDragged.x += curCoords.x - startCoords.x;
            distanceDragged.y += curCoords.y - startCoords.y;

            calculateYVals();
            repaint();
        }
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            //Point cursorPos = e.getPoint();
            zoomFactor = (e.getWheelRotation() > 0) ? zoomFactor/1.1 : zoomFactor*1.1;
            zoomFactor = (zoomFactor < 0.5) ? 0.5 : zoomFactor; //0.5 cuz interval variable in paint component
            calculateYVals();
            repaint();
        }
    };
}