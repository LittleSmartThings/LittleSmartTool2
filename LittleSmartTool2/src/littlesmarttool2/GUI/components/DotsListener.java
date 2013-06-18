/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;

/**
 *
 * @author Rasmus
 */
public class DotsListener implements ActionListener
    {
        JLabel label;
        String text;
        int dots = 1;
        public DotsListener(JLabel label, String text)
        {
            this.label = label;
            this.text = text;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            String d = (dots == 1) ? "." : ((dots == 2) ? ".." : "...");
            label.setText(text+d);
            dots++;
            if (dots > 3) dots = 1;
        }
    }
