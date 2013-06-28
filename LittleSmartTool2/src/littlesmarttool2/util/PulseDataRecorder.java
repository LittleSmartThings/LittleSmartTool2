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
    //private SerialCommand[] oldPulseData = new SerialCommand[0];
    private final int position;
    public PulseDataRecorder(SerialController controller, int position)
    {
        this.position = position;
        this.controller = controller;
    }
    
    /**
     * Backup the pulsedata at the given position to memory
     */
    /*public void backupPulseData()
    {
        try {
            oldPulseData = controller.getIRTimings(POSITION, 10000);
        } catch (IOException | TimeoutException ex) {
            System.out.println("Backup failed: " + ex.getMessage());
        }
    }*/
    
    /**
     * Set the pulsedata on the given position to some specific data
     * @param timings The pulsedata to store
     * @throws TimeoutException On timeout
     * @throws IOException On other errors
     */
    /*public void setPulseData(int[] timings) throws TimeoutException, IOException
    {
        for (int i = 0; i < timings.length; i+=2)
        {
            controller.send("I;" + POSITION + ";" + i +  ";" + timings[i] + ";" + timings[i+1], 5000);
        }
        controller.send("M", 5000);
    }*/
    
    /**
     * Restore the pulsedata backed up from backupPulseData()
     */
    /*public void restorePulseData()
    {
        for (SerialCommand cmd : oldPulseData)
        {
            try {
                controller.send(cmd, 1000);
            } catch (IOException | TimeoutException ex) {
                System.out.println("Restore failed: " + ex.getMessage());
            }
        }
        try {
            controller.send("M", 5000);
        } catch (IOException | TimeoutException ex) {
            
        }
    }*/
    
    public static class IRRecordException extends Exception
    {
        public IRRecordException(){}
        public IRRecordException(String msg) {super(msg);}
    }
    
    public static class IRPlaybackException extends Exception
    {
        public IRPlaybackException(){}
        public IRPlaybackException(String msg) {super(msg);}
    }
    
    /**
     * Record some pulsedata from the IR receiver
     * @return The recorded pulsedata
     * @throws IOException
     * @throws TimeoutException 
     */
    public int[] recordPulseData() throws IOException, TimeoutException, IRRecordException
    {
        //Record to position 1
        String response = controller.send("J;" + position, 10000);
        if (!response.equals("J;1")) throw new IRRecordException("The StratoSnapper timed out while waiting for IR input or the sequenze was too long");
        //Read last recorded
        SerialCommand[] pulseData = controller.sendMultiResponse("I;0","I;1",10000);
        
        //Convert timings
        int[] timings = new int[pulseData.length*2];
        for (int i = 0; i < pulseData.length; i++)
        {
            int[] args = pulseData[i].convertArgsToInt();
            timings[(args[1]-1)*2] = args[2];
            timings[(args[1]-1)*2+1] = args[3];
        }
        //Count zeros in pairs
        int zeros = 0;
        for (int i = timings.length-1; i > 0; i-=2)
            if (timings[i] == 0 && timings[i-1] == 0) zeros+=2;
        
        int[] reducedTimings = new int[timings.length-zeros];
        System.arraycopy(timings, 0, reducedTimings, 0, reducedTimings.length);
        
        return reducedTimings;
    }
    
    public void playbackRecording() throws TimeoutException, IOException, IRPlaybackException
    {
        String reply = controller.send("H;0", 5000);
        if (!"H;1".equals(reply))
            throw new IRPlaybackException("Unexpected answer from Stratosnapper when playing back recorded command: \""+reply+"\"");
    }
    
}
