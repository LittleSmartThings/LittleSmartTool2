/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import littlesmarttool2.GUI.SS2Wizard;

/**
 *
 * @author Rasmus
 */
public class UpdateUtil {
    
    public static final int FirmwareMain = 1; //{Main.Sub}
    public static final int FirmwareSub = 0;
        
    public static boolean UpdateFirmware(String port)
    {
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        int result;
        String prog = (isWindows) ? "avrdude-win/avrdude.exe" : "avrdude-mac/avrdude";
        String conf = (isWindows) ? "avrdude-win/avrdude.conf" : "avrdude-mac/avrdude.conf";
        //String filename = "src/littlesmarttool2/firmware/Blink_10.cpp.hex";
        //String filename = "src/littlesmarttool2/firmware/StratoSnapper_v22.cpp.hex";
        String filename = "firmware/StratoSnapper_v22.cpp.hex";
        StringBuilder sb = new StringBuilder();
        try {
            Process p = Runtime.getRuntime().exec(prog+" -C"+conf+" -v -v -v -v -patmega328p -carduino -P"+port+" -b57600 -D -Uflash:w:"+filename+":i");
            BufferedReader r = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line;
            while ((line = r.readLine()) != null)
            {
                sb.append(line);
            }
            result = p.waitFor();
            
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(SS2Wizard.class.getName()).log(Level.SEVERE, sb.toString(), ex);
            result = -1;
        }
        return result == 0;
    }
}
