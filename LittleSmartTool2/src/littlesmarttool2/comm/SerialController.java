/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.comm;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.util.ArrayList;
import java.io.*;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOException;

/**
 * Intended use order:
 *  create new
 *  set up listeners
 *  connect (via data from static getPortNames)
 * @author Rasmus
 */
public class SerialController {
    private ArrayList<ConnectionListener> connectionListeners = new ArrayList<ConnectionListener>();
    private boolean connected = false;
    private SerialPort port;
    private OutputStream outStream;
    private BufferedReader inReader;
    private static final String INIT_STRING = "StratoSnapper 2 Init.";

    /**
     * Add a connection listener which will get invoked when a 
     * a connection is either started or stopped.
     */
    
    public void addConnectionListener(ConnectionListener listener){
        if (!connectionListeners.contains(listener))
            connectionListeners.add(listener);
    }
    public void removeConnectionListener(ConnectionListener listener)
    {
        connectionListeners.remove(listener);
    }
    
    /**
     * Get a list of existing ports in the systems
     * @return 
     */
    public static ArrayList<String> getPortNames()
    {
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();
        ArrayList<String> portNames = new ArrayList<>();
        while(ports.hasMoreElements()) {
            String portName = ((CommPortIdentifier)ports.nextElement()).getName();
            portNames.add(portName);
        }     
        return portNames;
    }

    /**
     * Connect to a StratoSnapper on a given Serial Port
     * @param portName The portname to connect to
     * @param timeOut The timout in ms to wait for a connection
     * @return The Firmware version of the connected StratoSnapper
     * @throws NoSuchPortException
     * @throws PortInUseException
     * @throws UnsupportedCommOperationException
     * @throws IOException
     * @throws TimeoutException 
     */
    public synchronized SerialCommand connect(String portName, int timeOut) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException, TimeoutException
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        port = (SerialPort) portIdentifier.open("LittleSmartTool 2", 1000);
        port.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        outStream = port.getOutputStream();
        inReader = new BufferedReader(new InputStreamReader(port.getInputStream()));
        
        String answer = send("V",timeOut);
        SerialCommand cmd = SerialCommand.fromMessage(answer);
        
        if (cmd.getCommand() != 'V')
            throw new IOException("Connected device gave unknown answer");

        connected = true;
        invokeConnectionChangedListeners(true);
        
        return cmd;
    }
    
    private void invokeConnectionChangedListeners(boolean value)
    {
        for (ConnectionListener l : connectionListeners)
            if (l != null)
                l.connectionStateChanged(value);
    }
    
    public String send(String msg, int timeOut, int maxRepetitions) throws IOException, TimeoutException
    {
        while (maxRepetitions > 0)
        {
            maxRepetitions--;
            try {
                return send(msg, timeOut);
            } catch (IOException | TimeoutException ex) {
                if (maxRepetitions == 0)
                    throw ex;
            }
        }
        throw new TimeoutException("Timed out");
    }
    
    public String send(char command, String[] args, int timeOut, int maxRepetitions) throws IOException, TimeoutException
    {
        String msg = command + "";
        for (String s : args) msg += ";" + s;
        return send(msg,timeOut, maxRepetitions);
    }
    
    public String send(char command, String[] args, int timeOut) throws IOException, TimeoutException
    {
        String msg = command + "";
        for (String s : args) msg += ";" + s;
        return send(msg,timeOut);
    }
    
    /**
     * Send a message to the connected StratoSnapper
     * @param message The message to send (e.g O;1)
     * @param timeOut The timout in ms to wait for a response
     * @return The answer from the StratoSnapper
     * @throws IOException On error
     * @throws TimeoutException On timeout
     */
    public synchronized String send(String message, int timeOut) throws IOException, TimeoutException
    {
        long endTime = System.currentTimeMillis() + timeOut;
        message = tryFixServoID(message);
        //System.err.println("Sending >>>" + message + "<<<");
        
        try {
            port.enableReceiveTimeout(timeOut);
        } catch (UnsupportedCommOperationException ex) {
        }
        
        String read = INIT_STRING;
        while (INIT_STRING.equals(read))
        {           
            //Actually send message
            outStream.write((message + ">").getBytes());
            outStream.flush();
            
            while(true)
            {
                if (System.currentTimeMillis() > endTime)
                    throw new TimeoutException("Connection timeout exceeded");
                try {
                    read = inReader.readLine();
                    break;
                }
                catch (IOException ex)
                {
                    continue; //Nothing to read at this time
                }
            }
        }
        if (read.startsWith("S;"))
            read = fixServoReadingOrder(read);
        //System.err.println("Received >>>" + read + "<<<");
        return read;
    }
    
    private String tryFixServoID(String message)
    {
        if (!message.startsWith("R;") &&!message.startsWith("T;"))
            return message;
        
        String[] parts = message.split(";");
        String[] result = message.split(";");
        
        result[2] = convertServoIDtoSSorder(Integer.parseInt(parts[2])) + "";
        
        StringBuilder sb = new StringBuilder(result[0]);
        for (int i = 1; i < result.length; i++)
            sb.append(";").append(result[i]);
        return sb.toString();
    }
    
    private int convertServoIDtoSSorder(int id)
    {
        switch (id)
        {
            case 1: return 3;
            case 2: return 4;
            case 3: return 1;
            case 4: return 2;
        }
        throw new IllegalArgumentException("Only IDs between 1-4. Received: " + id);
    }
    
    private String fixServoReadingOrder(String message)
    {
        String[] parts = message.split(";");
        String[] result = message.split(";");
        result[1] = parts[3];
        result[3] = parts[1];
        result[2] = parts[4];
        result[4] = parts[2];
        StringBuilder sb = new StringBuilder(result[0]);
        for (int i = 1; i < result.length; i++)
            sb.append(";").append(result[i]);
        return sb.toString();
    }
    
    public void disconnect()
    {
        if (port == null || !connected) return;
        connected = false;
        invokeConnectionChangedListeners(false);
        port.close();
    }
    
    /**
     * Get a value indicating if this controller is connected
     */
    public boolean connected()
    {
        return connected;
    }
    
}
