/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI;

import javax.swing.JPanel;

/**
 *
 * @author marcher89
 */
public abstract class StepPanel extends JPanel {
    protected SS2Wizard wizard;
    
    public StepPanel(SS2Wizard wizard){
        this.wizard = wizard;
    }
    
    public abstract void onDisplay();
    
    public abstract void onHide();
}
