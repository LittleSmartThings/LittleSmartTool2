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
import java.util.Enumeration;

/**
 * Intended use order:
 *  create new
 *  set up listeners
 *  connect (via data from static getPortNames)
 * @author Rasmus
 */
public class SerialController {
    private boolean connected = false;
    private SerialCommReader reader;
    private SerialPort port;
    private OutputStream outStream;
    
    /**
     * Create a new Serial connection controller
     * Used for managing a connection on a given port
     */
    public SerialController()
    {
        reader = new SerialCommReader();
    }
    
    /**
     * Add a response listener which will get invoked when a 
     * response from the StratoSnapper i received
     */
    public void addResponseListener(ResponseListener listener)
    {
        reader.addResponseListener(listener);
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
     * Connect to a portname
     * Response listeners should be added before this method is invoked
     * @param portName The name of the port to connect to (list can be obtained via getPortNames())
     * @throws NoSuchPortException
     * @throws PortInUseException
     * @throws UnsupportedCommOperationException
     * @throws IOException 
     */
    public void connect(String portName) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException
    {
        if (connected) return;
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        port = (SerialPort) portIdentifier.open("LittleSmartTool 2", 1000);
        port.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        connected = true;
        reader.setInputStream(port.getInputStream());
        outStream = port.getOutputStream();
        reader.start();
    }
    
    /**
     * Get a value indicating if this controller is connected
     */
    public boolean connected()
    {
        if (!reader.isAlive()) connected = false;
        return connected;
    }
    
    /**
     * Send a command to the connected StratoSnapper
     * @param command The command to send
     * @param args The argument to send along
     * @throws IOException 
     */
    public void send(char command, String[] args) throws IOException
    {
        try
        {
            String tmp = "" + command;
            for (String s : args)
                tmp += ";" + s;
            outStream.write((tmp+">").getBytes());
        }
        catch (IOException e)
        {
            connected = false;
            throw e;
        }
    }
}
