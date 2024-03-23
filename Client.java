import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Client {
    static JFrame gui;
    static Graph g;

    public static void main(String[] args) { // zoom issue / function issue eventually / derivative integrate buttons
        instantiate();

    }
    public static void instantiate() {
        gui = new JFrame() {
            {
                setTitle("Graphing Calculator");
                setSize(600,700);//random choice
                setLayout(null);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setVisible(true);
                setResizable(false);//for now?
            }
        };
        g = new Graph(1) {
            {
                setVisible(true);
                setBackground(Color.black);
            }
        };
        JTextField derivInput = new JTextField(){
            {
                setBounds(175, 525, 50, 50);
            }
        };

        JButton derivButton = new JButton() {
            {
                setForeground(Color.black);
                setText("Dy \\ Dx");
                setBounds(150, 500, 100, 50);
                setBackground(Color.lightGray);
            }
        };
        JButton integralButton = new JButton() {
            {
                setForeground(Color.black);
                setText("∫ f(x) dx");
                setBounds(350, 500, 100, 50);
                setBackground(Color.lightGray);
            }
        };
        //gui.add(derivInput);
        gui.add(integralButton);
        gui.add(derivButton);
        gui.add(g);
        gui.setVisible(true);
    }
}