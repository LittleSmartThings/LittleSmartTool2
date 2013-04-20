/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2;

import javax.swing.JPanel;
import littlesmarttool2.GUI.*;

/**
 *
 * @author Rasmus
 */
public class LittleSmartTool2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new SS2Wizard(new JPanel[]{new Step1Panel(), new Step2Panel(), new Step3Panel(), new Step4Panel()});
    }
}
