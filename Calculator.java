import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calculator {
    static JFrame gui;
    static Graph graph;
    static JTextField derivInput, derivAns;
    static JTextField integralBoundOne, integralBoundTwo, integralAns;

    public static void main(String[] args) {//fix tan line / refactor code (document with comments / arrow key class?)
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
        JButton upArrow = new JButton() {
            {
                setBounds(178,515,20,30);
                setBackground(Color.darkGray);
                addActionListener(new ActionListener() { 
                    public void actionPerformed(ActionEvent e) {
                        graph.showTrace = !graph.showTrace;
                        graph.repaint();
                    }
                });
            }
        };
        JButton leftArrow = new JButton() {
            {
                setBounds(143,550,30,20);
                setBackground(Color.darkGray);
                addActionListener(new ActionListener() { 
                    public void actionPerformed(ActionEvent e) {
                        if (!graph.showTrace) return;
                        graph.tracePosition.x-=(Math.PI/24);
                        graph.calculateYVals();
                    }
                });
            }
        };
        JButton rightArrow = new JButton() {
            {
                setBounds(203,550,30,20);
                setBackground(Color.darkGray);
                addActionListener(new ActionListener() { 
                    public void actionPerformed(ActionEvent e) {  
                        if (!graph.showTrace) return;
                        graph.tracePosition.x+=(Math.PI/24);
                        graph.calculateYVals();
                    }
                });
            }
        };
        JButton downArrow = new JButton() {
            {
                setBounds(178,575,20,30);
                setBackground(Color.darkGray);
                addActionListener(new ActionListener() { 
                    public void actionPerformed(ActionEvent e) {
                        graph.showTrace = !graph.showTrace;
                        graph.repaint();
                    }
                });
            }
        };
        gui.add(upArrow);
        gui.add(leftArrow);
        gui.add(rightArrow);
        gui.add(downArrow);
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
        graph = new Graph() {
            {
                setVisible(true);
                setBackground(Color.black);
            }
        };

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
        derivInput = new JTextField() {
            {
                setBounds(155, 400, 75, 25);
                setForeground(Color.blue);
            }
        };
        derivAns = new JTextField() {
            {
                setBounds(265, 400, 85, 25);
                setEditable(false);
                setForeground(Color.blue);
            }
        };
        integralBoundOne = new JTextField() {
            {
                setBounds(155, 450, 30, 25);
                setForeground(Color.blue);
            }
        };
        integralBoundTwo = new JTextField() {
            {
                setBounds(200, 450, 30, 25);
                setForeground(Color.blue);
            }
        };
        integralAns = new JTextField() {
            {
                setBounds(265, 450, 85, 25);
                setEditable(false);
                setForeground(Color.blue);
            }
        };

        gui.add(derivInput);
        gui.add(derivAns);
        gui.add(integralBoundOne);
        gui.add(integralBoundTwo);
        gui.add(integralAns);
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
                if (Double.isNaN(ans)) return;
                graph.riemannRange[0] = (a < b) ? a : b; 
                graph.riemannRange[1] = (a >= b) ? a : b; 
                graph.repaint();
                //graph REIMANN DRAWING
            }
        }); 

        gui.add(integralButton);
        gui.add(derivButton);
    }    
}