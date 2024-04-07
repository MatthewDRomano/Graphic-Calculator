import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList; // for eventual use of multiple functions

import javax.swing.*;
public class Graph extends JPanel {
    private final int WIDTH = 350, HEIGHT = 300, DEFAULTZOOM = 10;
    private static double zoomFactor; //if window goes from -10 to 10 down to -1 to 1, zoom is x10 AKA / 10   
    private Point offset, distanceDragged;  //  x = 0  is center so offset ensures graph has negative values // drag is distance mouse drags      
    private Integer[] yCoords;
    public double derivativePoint; // w/ new updates put these in classes/change visibility or overall functions
    public Double[] riemannRange;
    public Point2D.Double tracePosition;
    public boolean showTrace;

    public Graph() {
        setBounds(17, 17, WIDTH, HEIGHT);
        yCoords = new Integer[WIDTH];

        addMouseListener(ma);
        addMouseMotionListener(ma);
        addMouseWheelListener(ma);
        resetScreen();
    }
    public void resetScreen() {
        offset = new Point(WIDTH/2,HEIGHT/2);
        distanceDragged = new Point(0,0);
        zoomFactor = DEFAULTZOOM;
        riemannRange = new Double[] {Double.NaN, Double.NaN};
        derivativePoint = Double.NaN;
        tracePosition = new Point2D.Double(0, 0);
        showTrace = false;

        calculateYVals();
    }

    public double convertGraphToScreenCoord(Double graphCoord, boolean isXVal) {
        if (graphCoord == null) return Double.NaN; 
        else return (isXVal)
            ? (graphCoord*zoomFactor) + offset.x + distanceDragged.x
            : (graphCoord*zoomFactor) + offset.y + distanceDragged.y;
        //else return (scrnCoord*zoomFactor) + ((isXVal) ? offset.x + distanceDragged.x: offset.y + distanceDragged.y);

    }
    public double convertScreenToGraphCoord(int scrnCoord, boolean isXVal) {
        return (isXVal)
           ? (scrnCoord - offset.x - distanceDragged.x)/zoomFactor
           : (scrnCoord - offset.y - distanceDragged.y)/zoomFactor;
        //return scrnCoord - ((isXVal) ? offset.x + distanceDragged.x: offset.y + distanceDragged.y) / zoomFactor;
    }

    public void calculateYVals() {
        for (int i = 0; i < WIDTH; i++) {
            double y, x = convertScreenToGraphCoord(i, true);
            y = Functions.f(x);  
            yCoords[i] = (Double.isNaN(y)) ? null : (int)convertGraphToScreenCoord(y, false); // graph to screen
        }
        int x = (int)convertGraphToScreenCoord(tracePosition.x, true);//graph to screen
        if (x >= 0 && x < WIDTH && yCoords[x] != null)
            tracePosition.y = HEIGHT-yCoords[x];
        else tracePosition.y = Integer.MAX_VALUE;
        repaint();
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

        g2.setColor(Color.red);
        for (int i = 0; i < WIDTH-1; i++) {
            if (yCoords[i] == null || yCoords[i+1] == null) continue;
            g2.drawLine(i, HEIGHT-yCoords[i], i+1, HEIGHT-yCoords[i+1]);//on screen coords   
            // BELOW Riemann Sum Visual
            double x = convertScreenToGraphCoord(i, true);//screen to graph
            if (x > riemannRange[0] && x < riemannRange[1])
                g2.drawLine(i, HEIGHT-yCoords[i], i, offset.y - distanceDragged.y);
        }
        
        //draw derivative tanget line
        double tangentPointX = convertGraphToScreenCoord(derivativePoint, true); // graph to screen
        double tangentPointY = HEIGHT-convertGraphToScreenCoord(Functions.f(derivativePoint), false);
        double slope = Functions.Derivative(derivativePoint);
        double tanLength = (500+distanceDragged.x)*zoomFactor; //500 is just base tan line length 
        g2.setColor(Color.blue);
        g2.drawLine((int)(tangentPointX-tanLength), (int)(tangentPointY+(tanLength*slope)),(int)(tangentPointX+tanLength), (int)(tangentPointY-(tanLength*slope)));
        g2.fillOval((int)tangentPointX-3,(int)(tangentPointY+slope)-3,6,6);//dot on tangent
        
        //Draws Tracer
        g2.setColor(Color.black);
        double x = convertGraphToScreenCoord(tracePosition.x, true); //graph to screen
        if (x > 0 && x < WIDTH && showTrace) {
            g2.drawString("Trace: (" + round(tracePosition.x) + "," + round(Functions.f(tracePosition.x)) + ")",10,10);  
            g2.fillOval((int)x-3, (int)tracePosition.y-3, 6, 6);
        }     

        //Displays Average Value of Function within graph window
        String avgDisplayText = "Avg On Screen Val: " + round(Average(yCoords));
        g2.drawString(avgDisplayText, WIDTH-avgDisplayText.length()*6, HEIGHT-10);
    }

    public double Average(Integer[] vals) { //average value of displayed segment for APCSP Requirement
        double total = 0, count = 0;
        for (int i = 0; i < vals.length; i++)
            if (vals[i] != null && vals[i] > 0 && vals[i] < HEIGHT) {// if val is graphed and withon window bounds (AKA on screen value)
                double yVal = convertScreenToGraphCoord(vals[i], false);
                total += yVal;
                count++;
            }
        return total / count;
    }

    public double round(double num) { return (int)(Math.round(num*1000))/1000.0;}

    public MouseAdapter ma = new MouseAdapter() {
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
        }
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            //Point cursorPos = e.getPoint();
            zoomFactor = (e.getWheelRotation() > 0) ? zoomFactor/1.1 : zoomFactor*1.1;
            zoomFactor = (zoomFactor < 1) ? 1 : zoomFactor; //0.5 cuz interval variable in paint component
            //distanceDragged.x += ((cursorPos.x - offset.x)/zoomFactor)*(e.getWheelRotation()/Math.abs(e.getWheelRotation()));
            //distanceDragged.y -= ((cursorPos.y - offset.y)/zoomFactor)*(e.getWheelRotation()/Math.abs(e.getWheelRotation()));
            calculateYVals();
        }
    };
}