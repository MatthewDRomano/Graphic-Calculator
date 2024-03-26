import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;

public class Client {
    static JTextField derivInput, derivAns;
    static JTextField integralBoundOne, integralBoundTwo, integralAns;

    public static void main(String[] args) { //function issue eventually / derivative integral graphs / refactor code
        EventQueue.invokeLater(new Runnable() { //fixes components not loading properly on start
            public void run() {
                JFrame gui = createFrameAndScreen();
                instantiateCalculusButtons(gui);
                instantiateAnswerFields(gui);
                instantiateFunctionButtons(gui);
                
            }
        });
    }
    public static JFrame createFrameAndScreen() {
        JFrame gui = new JFrame() {
            {
                setTitle("Graphing Calculator");
                setPreferredSize(new Dimension(400,700));//random choice
                pack();
                setLayout(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setResizable(false);//for now?
                getContentPane().setBackground(new Color(26,26,26));
            }
        };
        Graph g = new Graph() {
            {
                setVisible(true);
                setBackground(Color.black);
            }
        };

        gui.add(g);
        gui.setVisible(true);
        return gui;
    }
    public static void instantiateFunctionButtons(JFrame gui) { // add to loop maybe?
        JButton parabolaButton = new JButton() {
            {
                setText("Parab");
                setBackground(Color.black);
                setBounds(50,600,75,25);
                setForeground(new Color(230, 230, 230));
                setFocusPainted(false);
                setBorderPainted(false);
            }
        };
        JButton sqrtButton = new JButton() {
            {
                //setText("\u221ax");
                setText("Sqrt");
                setBackground(Color.black);
                setBounds(156,600,75,25);
                setForeground(new Color(230, 230, 230));
                setFocusPainted(false);
                setBorderPainted(false);
            }
        };
        JButton lineButton = new JButton() {
            {
                setText("Line");
                setBackground(Color.black);
                setBounds(262,600,75,25);
                setForeground(new Color(230, 230, 230));
                setFocusPainted(false);
                setBorderPainted(false);
            }
        };
        JButton rocketButton = new JButton() {
            {
                setText("Rocket");
                setBackground(Color.black);
                setBounds(50,550,75,25);
                setForeground(new Color(230, 230, 230));
                setFocusPainted(false);
                setBorderPainted(false);
            }
        };
        JButton cbrtButton = new JButton() {
            {
                //setText("\u221Bx");
                setText("Cbrt");
                setBackground(Color.black);
                setBounds(156,550,75,25);
                setForeground(new Color(230, 230, 230));
                setFocusPainted(false);
                setBorderPainted(false);
            }
        };
        JButton sineButton = new JButton() {
            {
                setText("Sine");
                setBackground(Color.black);
                setBounds(262,550,75,25);
                setForeground(new Color(230, 230, 230));
                setFocusPainted(false);
                setBorderPainted(false);
            }
        };
        
        gui.add(parabolaButton);
        gui.add(sqrtButton);
        gui.add(lineButton);
        gui.add(rocketButton);
        gui.add(cbrtButton);
        gui.add(sineButton);
    }
    public static void instantiateAnswerFields(JFrame gui) {
        derivInput = new JTextField() {
            {
                setBounds(142, 350, 75, 25);
                setForeground(Color.blue);
            }
        };
        derivAns = new JTextField() {
            {
                setBounds(242, 350, 75, 25);
                setEditable(false);
                setForeground(Color.blue);
            }
        };
        integralBoundOne = new JTextField() {
            {
                setBounds(142, 400, 30, 25);
                setForeground(Color.blue);
            }
        };
        integralBoundTwo = new JTextField() {
            {
                setBounds(187, 400, 30, 25);
                setForeground(Color.blue);
            }
        };
        integralAns = new JTextField() {
            {
                setBounds(242, 400, 75, 25);
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
    public static void instantiateCalculusButtons(JFrame gui) {
        JButton derivButton = new JButton() {
            {
                setForeground(Color.black);
                setText("Dy \\ Dx");
                setBounds(17, 350, 100, 25);
                setBackground(Color.lightGray);      
                setFocusPainted(false);
                setBorderPainted(false);         
            }
        };
        derivButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                double x; 
                try { x = Double.parseDouble(derivInput.getText()); } 
                catch (NumberFormatException ex) { x = Double.NaN; }
                float ans = (float)Functions.Derivative(x);
                derivAns.setText(ans + "");
            }
        });
        JButton integralButton = new JButton() {
            {
                setForeground(Color.black);
                setText("\u222b f(x) dx");
                setBounds(17, 400, 100, 25);
                setBackground(Color.lightGray);
                setFocusPainted(false);
                setBorderPainted(false);
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
                integralAns.setText(ans + "");
            }
        });

        gui.add(integralButton);
        gui.add(derivButton);
    }    
}