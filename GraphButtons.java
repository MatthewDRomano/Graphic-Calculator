import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GraphButtons extends JButton {
    
    public GraphButtons(String text ) {
        setText(text);
        setBackground(Color.black);        
        setForeground(new Color(230, 230, 230));
        setFocusPainted(false);

        addActionListener(new ActionListener() { 
            public void actionPerformed(ActionEvent e) {  
                if (Functions.function.equals(text)) return;
                Functions.updateFunction(text);
                Calculator.refresh();
            }    
        });
    }
}
