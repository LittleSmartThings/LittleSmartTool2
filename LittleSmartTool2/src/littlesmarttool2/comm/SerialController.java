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
    private ArrayList<ResponseListener> responseListeners = new ArrayList<>();
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
    }
    
    /**
     * Add a response listener which will get invoked when a 
     * response from the StratoSnapper i received
     */
    public void addResponseListener(ResponseListener listener){
        if (!responseListeners.contains(listener))
            responseListeners.add(listener);
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
        reader = new SerialCommReader(responseListeners);
        reader.setInputStream(port.getInputStream());
        outStream = port.getOutputStream();
        reader.setDaemon(true);
        reader.start();
    }
    
    public void disconnect()
    {
        if (!connected) return;
        connected = false;
        reader.stopListening();
        port.close();
    }
    
    /**
     * Get a value indicating if this controller is connected
     */
    public boolean connected()
    {
        if (reader == null || !reader.isAlive()) connected = false;
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
        String tmp = "" + command;
        if (args != null)
            for (String s : args)
                tmp += ";" + s;
        send(tmp);
    }
    
    public void send(String message) throws IOException
    {
        try
        {
            outStream.write((message + "<").getBytes());
            outStream.flush();
        }
        catch (IOException e)
        {
            connected = false;
            throw e;
        }
    }
    
    private static class SerialCommReader extends Thread {
        private InputStream inStream;
        private ArrayList<ResponseListener> responseListeners;
        private boolean run = true;
        public SerialCommReader(ArrayList<ResponseListener> listeners){
            responseListeners = listeners;
        }
        public void stopListening()
        {
            run = false;
        }
        public void setInputStream(InputStream in){
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
                    if (!run)
                    {
                        return;
                    }
                    String read;
                    try{
                    read = bufferedReader.readLine();
                    }
                    catch (Exception e) { continue; }
                    if (read == null) {
                        Thread.sleep(10); //wait for just a little bit
                        continue;
                    }
                    String[] parts = read.split(";");
                    if (parts[0].length() == 0) continue; //Failure
                    char cmd = parts[0].charAt(0);
                    String[] args = new String[parts.length-1];
                    for (int i = 0; i < args.length; i++)
                        args[i] = parts[i+1];
                    for (ResponseListener l : responseListeners)
                        if (l != null)
                            l.receiveResponse(cmd, args);
                }
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException in SerialCommReader: " + ex.getMessage());
            } /*catch (IOException ex) {
                System.out.println("IOException in SerialCommReader: " + ex.getMessage());
            }*/
        }
    }
}
