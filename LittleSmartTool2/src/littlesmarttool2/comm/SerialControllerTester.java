/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rasmus
 */
public class SerialControllerTester implements ResponseListener {
    SerialController c;
    @Override
    public void receiveResponse(char command, String[] args) {
        if (command == 'S')
        {
            String s = "";
            for (String ss : args)
                s += ";" + ss;
            System.out.println(s);
        }
        try {
            c.send('S', null);
        } catch (IOException ex) {
            System.out.println("Send failed");
        }
    }
    
    public SerialControllerTester()
    {
        c = new SerialController();
        ArrayList<String> ports = SerialController.getPortNames();
        for (int i = 0; i < ports.size(); i++)
            System.out.println(i + ": " + ports.get(i));
        System.out.println("Connecting to: " + ports.get(0));
        c.addResponseListener(this);
        try
        {
            c.connect(ports.get(0));
            c.send('S', null);
        }
        catch (Exception e)
        {
            System.out.println("Failed: " + e.getMessage());
        }
    }
    
    public static void main(String[] args)
    {
        new SerialControllerTester();
        
    }

}
