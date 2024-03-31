import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calculator {
    static JFrame gui;
    static JTextField derivInput, derivAns;
    static JTextField integralBoundOne, integralBoundTwo, integralAns;

    public static void main(String[] args) { // zoom //  derivative tangent line / arrow button functionality / refactor code (this class)
        EventQueue.invokeLater(new Runnable() { //fixes components not loading properly on start
            public void run() {
                Graph graph = createFrameAndScreen();
                instantiateCalculusButtons(graph);
                instantiateTextFields();
                instantiateArrowButtons();
                instantiateFunctionButtons(graph);
            }
        });
    }
    public static void instantiateArrowButtons() {
        JButton upArrow = new JButton() {
            {
                setBounds(178,515,20,30);
                setBackground(Color.darkGray);
            }
        };
        JButton leftArrow = new JButton() {
            {
                setBounds(143,550,30,20);
                setBackground(Color.darkGray);
            }
        };
        JButton rightArrow = new JButton() {
            {
                setBounds(203,550,30,20);
                setBackground(Color.darkGray);
            }
        };
        JButton downArrow = new JButton() {
            {
                setBounds(178,575,20,30);
                setBackground(Color.darkGray);
            }
        };
        gui.add(upArrow);
        gui.add(leftArrow);
        gui.add(rightArrow);
        gui.add(downArrow);
    }
    public static Graph createFrameAndScreen() {
        gui = new JFrame() {
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
        return g;
    }
    public static void instantiateFunctionButtons(Graph graph) { // add to loop maybe?
        JButton[] functionButtons = new JButton[8];
        String[] names = new String[] {"Line", "Parab", "Sqrt", "Cube", "Cbrt", "Bird", "Rocket", "Sine"};//eventually get this array from Functions class (it'll have one)
        int yVal = 325;
        //int amtPerRow = 4;//if dynamic / dont rly need
        for (int i = 0; i < functionButtons.length; i++) {
            if (i == 4) yVal += 30;
            int index = i;
            functionButtons[index] = new JButton() {
                {
                    setText(names[index]);
                    setBackground(Color.black);        
                    setForeground(new Color(230, 230, 230));
                    setFocusPainted(false);                              
                    
                    addActionListener(new ActionListener() { 
                    public void actionPerformed(ActionEvent e) {  
                        //if (Functions.func.getName().equals(getText())) return;   
                        if (Functions.function.equals(getText())) return;
                        Functions.updateFunction(getText());
                        graph.resetScreen();
                        clearText();
                    }    
                    });
                }
            };   
            functionButtons[i].setBounds(35+80*(i%4),yVal,75,25); 
            gui.add(functionButtons[i]);
        }
    }
    public static void clearText() {
        integralAns.setText("");
        integralBoundOne.setText("");
        integralBoundTwo.setText("");
        derivAns.setText("");
        derivInput.setText("");
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
    public static void instantiateCalculusButtons(Graph graph) {
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
                derivAns.setText(ans + "");
                graph.derivativePoint = (int)Math.round(x);
                //draw tangent line
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
                integralAns.setText(ans + "");
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