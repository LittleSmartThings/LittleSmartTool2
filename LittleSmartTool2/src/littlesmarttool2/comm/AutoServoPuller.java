package littlesmarttool2.comm;

import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Rasmus
 */
public class AutoServoPuller implements ResponseListener {
    private static HashMap<SerialController, AutoServoPuller> pullers = new HashMap<>();
    private boolean running = false;
    private SerialController controller;
    private AutoServoPuller(SerialController controller)
    {
        this.controller = controller;
    }
    
    /**
     * Start automatically pulling servo information from a given controller
     * @return True if the puller started successfully
     */
    public static boolean Start(SerialController controller)
    {
        AutoServoPuller puller;
        if (pullers.containsKey(controller))
        {
            return true;
        }
        else
        {
            puller = new AutoServoPuller(controller);
            controller.addResponseListener(puller);
            pullers.put(controller, puller);
        }
        try {        
            controller.send('S', null);
            puller.running = true;
        } catch (IOException ex) {
            puller.running = false;
            return false;
        }
        return true;
    }
    
    public static boolean isRunning(SerialController controller)
    {
        if (pullers.containsKey(controller))
        {
            return pullers.get(controller).running;
        }
        return false;
    }

    public static void Stop(SerialController controller)
    {
        if (pullers.containsKey(controller))
        {
            controller.removeResponseListener(pullers.get(controller));
            pullers.get(controller).running = false;
            pullers.remove(controller);
        }
    }
    
    @Override
    public void receiveResponse(char command, String[] args) {
        if (!running) return;
        if (command != 'S') return;
        try {
            controller.send('S', null);
        } catch (IOException ex) {
            running = false;
        }
    }
}
