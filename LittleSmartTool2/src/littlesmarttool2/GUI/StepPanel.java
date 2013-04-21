/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author marcher89
 */
public abstract class StepPanel extends JPanel {
    protected SS2Wizard wizard;
    
    protected Font headlineFont = new Font("Lucida Grande", 0, 18);
    protected Border headlineBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    
    public StepPanel(SS2Wizard wizard){
        this.wizard = wizard;
    }
    
    public abstract void onDisplay();
    
    public abstract void onHide();
}
