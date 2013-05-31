/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2;

import java.io.*;
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
    
    public static final int minFirmwareMain = 0; //{Main.Sub}
    public static final int minFirmwareSub = 2;
    public static final int maxFirmwareMain = 0; //{Main.Sub}
    public static final int maxFirmwareSub = 2;
    public static String Version = "0.0"; //Software version (loaded from file)
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Setup log
        try {
            Logger logger = Logger.getLogger(SS2Wizard.class.getName());
            FileHandler fh = new FileHandler("logs/SSLog.txt", true);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.log(Level.INFO, "OS: {0}. ", System.getProperty("os.name"));
        } catch (Exception ex) {
            //DO nothing
        }
        
        //Load version
        try {    
            BufferedReader br = new BufferedReader(new FileReader("version.properties"));
            String line = br.readLine();
            while (!line.startsWith("version.number"))
                line = br.readLine();
            Version = line.split("=")[1];
        } catch (Exception ex) {
            Logger.getLogger(SS2Wizard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Start
        SS2Wizard wizard = new SS2Wizard();
        wizard.setVisible(true);
    }
}
