/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Internal serial port reader running it's own thread reading as much as possible
 * Invokes interpret method in SerialController after each successful read
 * @author Rasmus
 */
public class SerialCommReader extends Thread {
    
    private InputStream inStream;
    private ArrayList<ResponseListener> responseListeners = new ArrayList<>();
    
    public void addResponseListener(ResponseListener listener)
    {
        responseListeners.add(listener);
    }
    
    public void setInputStream(InputStream in)
    {
        this.inStream = in;
    }
    
    @Override
    public void run()
    {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream));
            Thread.sleep(1500);
            while (true)
            {
                String read = bufferedReader.readLine();
                if (read == null) {
                    Thread.sleep(10);
                    continue;
                }
                
                if (read.length() == 0) continue;
                String[] parts = read.split(";");
                if (parts[0].length() == 0) continue;
                
                char cmd = parts[0].charAt(0);
                String[] args = new String[parts.length-1];
                for (int i = 0; i < args.length; i++)
                    args[i] = parts[i+1];
                for (ResponseListener l : responseListeners)
                    l.receiveResponse(cmd, args);
            }
        } catch (InterruptedException ex) {
            System.out.println("InterruptedException in SerialCommReader: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("IOException in SerialCommReader: " + ex.getMessage());
        }
    }
}
