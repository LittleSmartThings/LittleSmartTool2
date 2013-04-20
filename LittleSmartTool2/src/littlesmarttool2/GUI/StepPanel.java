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
    public abstract void setWizard(SS2Wizard wizard);
    
    public abstract void onDisplay();
    
    public abstract void onHide();
}
