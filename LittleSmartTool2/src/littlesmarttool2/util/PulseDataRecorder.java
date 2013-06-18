/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import littlesmarttool2.comm.SerialCommand;
import littlesmarttool2.comm.SerialController;

/**
 *
 * @author Rasmus
 */
public class PulseDataRecorder {
    private SerialController controller;
    private SerialCommand[] oldPulseData = new SerialCommand[0];
    private int position;
    public PulseDataRecorder(SerialController controller, int position)
    {
        this.controller = controller;
        this.position = position;
    }
    
    public void backupPulseData()
    {
        try {
            oldPulseData = controller.getIRTimings(position, 10000);
        } catch (IOException | TimeoutException ex) {
            System.out.println("Backup failed: " + ex.getMessage());
        }
    }
    
    public void setPulseData(int[] timings) throws TimeoutException, IOException
    {
        for (int i = 0; i < timings.length; i+=2)
        {
            controller.send("I;" + position + ";" + i +  ";" + timings[i] + ";" + timings[i+1], 5000);
        }
    }
    
    public void restorePulseData()
    {
        for (SerialCommand cmd : oldPulseData)
        {
            try {
                controller.send(cmd, 1000);
            } catch (IOException | TimeoutException ex) {
                System.out.println("Restore failed: " + ex.getMessage());
            }
        }
    }
    
    public int[] recordPulseData() throws IOException, TimeoutException
    {
        String response = controller.send("J;" + position, 10000);
        if (!response.equals("J;1")) throw new TimeoutException("The StratoSnapper timed out while waiting for IR input");

        SerialCommand[] pulseData = controller.getIRTimings(position, 10000);
        int[] timings = new int[pulseData.length*2];
        for (int i = 0; i < pulseData.length; i++)
        {
            int[] args = pulseData[i].convertArgsToInt();
            timings[args[1]] = args[2];
            timings[args[1]+1] = args[3];
        }
        return timings;
    }
}
