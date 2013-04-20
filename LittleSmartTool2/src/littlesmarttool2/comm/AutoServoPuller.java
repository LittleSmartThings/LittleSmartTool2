/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.comm;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rasmus
 */
public class AutoServoPuller implements ResponseListener {
    private static AutoServoPuller singleton = new AutoServoPuller();
    private boolean running = false;
    private SerialController controller;
    private AutoServoPuller()
    {}
    public static AutoServoPuller getSingleton()
    {
        return singleton;
    }
    
    public void Start(SerialController controller) throws IOException
    {
        if (running) return;
        this.controller = controller;
        controller.addResponseListener(this);
        controller.send('S', null);
        running = true;
    }

    public void Stop()
    {
        running = false;
    }
    
    @Override
    public void receiveResponse(char command, String[] args) {
        if (!running) return;
        try {
            controller.send('S', null);
        } catch (IOException ex) {
            running = false;
        }
    }
}
