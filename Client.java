import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Client {
    static JFrame gui;
    static Graph g;

    public static void main(String[] args) { // fix red line issue on sqrt / zoom issue / function issue eventually
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
        gui.add(g);
        gui.setVisible(true);
    }
}