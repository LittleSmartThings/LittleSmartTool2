/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author marcher89
 */
public class Tester {
    public static void main(String[] args) throws IOException, InterruptedException{
        String prog = "avrdude-mac/avrdude";
        String port = "/dev/tty.usbserial-DB006092";
        String filename = "Blink_10.cpp.hex";
        Process p = Runtime.getRuntime().exec(prog+" -v -v -v -v -patmega328p -carduino -P"+port+" -b57600 -D -Uflash:w:firmware/"+filename+":i");
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String line = null;
        while ((line = r.readLine()) != null)
        {
            System.out.println(line);
        }
        int result = p.waitFor();
        System.out.println("------------------------------ RESULT: "+result);
    }
}
