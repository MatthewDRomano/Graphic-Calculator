import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calculator {
    static JFrame gui;
    static Graph graph;
    static JTextField derivInput, derivAns;
    static JTextField integralBoundOne, integralBoundTwo, integralAns;

    public static void main(String[] args) {//deriv at non d'able points / document code w/ comments
        EventQueue.invokeLater(new Runnable() { //fixes components not loading properly on start
            public void run() {
                createFrameAndScreen();
                instantiateCalculusButtons();
                instantiateTextFields();
                instantiateArrowButtons();
                instantiateFunctionButtons();

            }
        });
    }

    public static void refresh() {
        integralAns.setText("");
        integralBoundOne.setText("");
        integralBoundTwo.setText("");
        derivAns.setText("");
        derivInput.setText("");
        graph.resetScreen();
    }

    public static void instantiateArrowButtons() {
        JButton[] arrowButtons = new JButton[4];
        int[][] bounds = {{178,515,20,30}, {143,550,30,20}, {203,550,30,20}, {178,575,20,30}};
        ActionListener[] actions = {
            e -> {
            graph.showTrace = !graph.showTrace;
            graph.repaint();
            },
            e -> {
            if (!graph.showTrace) return;
            graph.tracePosition.x-=(Math.PI/24);
            graph.calculateYVals();
            },
            e -> {
            if (!graph.showTrace) return;
            graph.tracePosition.x+=(Math.PI/24);
            graph.calculateYVals();
            },
            e -> {
            graph.showTrace = !graph.showTrace;
            graph.repaint();
            }
        };

        for (int i = 0; i < arrowButtons.length; i++) {
            int indx = i;
            arrowButtons[i] = new JButton() {
            {
                setBounds(bounds[indx][0], bounds[indx][1], bounds[indx][2], bounds[indx][3]);
                setBackground(Color.darkGray);
                addActionListener(actions[indx]);
            }
            };
            gui.add(arrowButtons[i]);
        }
    }

    public static void createFrameAndScreen() {
        gui = new JFrame() {
            {
                setTitle("Graphing Calculator");
                setPreferredSize(new Dimension(400,675));//random choice
                pack();
                setLayout(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setResizable(false);//for now?
                getContentPane().setBackground(new Color(26,26,26));
            }
        };
        graph = new Graph();
        gui.add(graph);
        gui.setVisible(true);
    }
    public static void instantiateFunctionButtons() {
        String[] buttonNames = Functions.functionsList;
        int amtPerRow = 4, yVal = 325;

        for (int i = 0; i < buttonNames.length; i++) {
            if (i % amtPerRow == 0 && i != 0) yVal += 30;
            GraphButtons gb = new GraphButtons(buttonNames[i]);
            gb.setBounds(35+80*(i%4),yVal,75,25); 
            gui.add(gb);
        }
    }
    public static void instantiateTextFields() {
        JTextField[] txtFields = new JTextField[5];
        int[][] bounds = new int[][] {{155, 400, 75, 25}, {265, 400, 85, 25}, {155, 450, 30, 25}, {200, 450, 30, 25}, {265, 450, 85, 25}};
        boolean[] editStatus = new boolean[] {true, false, true, true, false};
        
        for (int i = 0; i < txtFields.length; i++) {
            int index = i;
            txtFields[i] = new JTextField() {
                {
                    setBounds(bounds[index][0], bounds[index][1], bounds[index][2], bounds[index][3]);
                    setEditable(editStatus[index]);
                    setForeground(Color.blue);
                }
            };
            gui.add(txtFields[i]);
        }
        derivInput = txtFields[0];
        derivAns = txtFields[1];
        integralBoundOne = txtFields[2];
        integralBoundTwo = txtFields[3];
        integralAns = txtFields[4];
    }  
    public static void instantiateCalculusButtons() {
        JButton derivButton = new JButton() {
            {
                setForeground(Color.black);
                setText("Dy \\ Dx");
                setBounds(35, 400, 85, 25);
                setBackground(Color.lightGray);      
                setFocusPainted(false);
                //setBorderPainted(false);         
            }
        };
        derivButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double x; 
                try { x = Double.parseDouble(derivInput.getText()); } 
                catch (NumberFormatException ex) { x = Double.NaN; }
                float ans = (float)Functions.Derivative(x);
                ans = (Math.abs(Math.round(ans) - ans) <= 0.0001) ? (int)Math.round(ans) : ans;
                derivAns.setText(ans + "");
                graph.derivativePoint = x;
                graph.repaint();
            }
        });
        JButton integralButton = new JButton() {
            {
                setForeground(Color.black);
                setText("\u222b f(x) dx");
                setBounds(35, 450, 85, 25);
                setBackground(Color.lightGray);
                setFocusPainted(false);
                //setBorderPainted(false);
            }
        };
        integralButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double a, b; 
                try { a = Double.parseDouble(integralBoundOne.getText()); } 
                catch (NumberFormatException ex) { a = Double.NaN; }
                try { b = Double.parseDouble(integralBoundTwo.getText()); } 
                catch (NumberFormatException ex) { b = Double.NaN; }
                float ans = (float)Functions.Integral(a, b);
                ans = (Math.abs(Math.round(ans) - ans) <= 0.0001) ? (int)Math.round(ans) : ans;
                integralAns.setText(ans + "");
                graph.riemannRange[0] = (Double.isNaN(a) || Double.isNaN(b)) ? Double.NaN : (a < b) ? a : b; 
                graph.riemannRange[1] = (Double.isNaN(a) || Double.isNaN(b)) ? Double.NaN : (a >= b) ? a : b; 
                graph.repaint();
                //graph REIMANN DRAWING
            }
        }); 

        gui.add(integralButton);
        gui.add(derivButton);
    }    
}