import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Client {
    static JFrame gui;
    static JTextField derivInput, derivAns;
    static JTextField integralBoundOne, integralBoundTwo, integralAns;

    public static void main(String[] args) { //function issue eventually / derivative integral graphs / refactor code
        EventQueue.invokeLater(new Runnable() { //fixes components not loading properly on start
            public void run() {
                Graph graph = createFrameAndScreen();
                instantiateCalculusButtons();
                instantiateAnswerFields();
                instantiateFunctionButtons(graph);
            }
        });
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
        JButton[] functionButtons = new JButton[6];
        String[] names = new String[] {"Parab", "Sqrt", "Line", "Rocket", "Cbrt", "Sine"};
        int yVal = 550;
        for (int i = 0; i < functionButtons.length; i++) {
            if (i == 3) yVal += 50;
            int index = i;
            functionButtons[index] = new JButton() {
                {
                    setText(names[index]);
                    setBackground(Color.black);        
                    setForeground(new Color(230, 230, 230));
                    setFocusPainted(false);
                    setBorderPainted(false);                                  
                    
                    addActionListener(new ActionListener() { 
                    public void actionPerformed(ActionEvent e) { 
                        graph.updateFunction(getText());
                        Functions.updateFunction(getText());
                    }    
                });
                }
            };   
            functionButtons[i].setBounds(50+106*(i%3),yVal,75,25); 
            gui.add(functionButtons[i]);
        }
    }
    public static void instantiateAnswerFields() {
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
    public static void instantiateCalculusButtons() {
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