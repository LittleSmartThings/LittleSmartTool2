/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
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
        try {
            Logger logger = Logger.getLogger(SS2Wizard.class.getName());
            FileHandler fh = new FileHandler("SSLog.txt", true);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.log(Level.INFO, "OS: {0}. ", System.getProperty("os.name"));
        } catch (Exception ex) {
            //DO nothing
        }
        
        
       SS2Wizard wizard = new SS2Wizard();
       wizard.setVisible(true);
    }
}
