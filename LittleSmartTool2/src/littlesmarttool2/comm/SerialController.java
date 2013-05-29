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
    //private List<ResponseListener> responseListeners = Collections.synchronizedList(new ArrayList<ResponseListener>());
    private ArrayList<ConnectionListener> connectionListeners = new ArrayList<ConnectionListener>();
    private boolean connected = false;
    //private SerialCommReader reader;
    private SerialPort port;
    private OutputStream outStream;
    private BufferedReader inReader;
    //private int syncTimeout;
    private static final String INIT_STRING = "StratoSnapper 2 Init.";
    
    //public enum ConnectionStatus{
    //    DISCONNECTED, CONNECTING, CONNECTED
    //}
    
    /**
     * Create a new Serial connection controller
     * Used for managing a connection on a given port
     */
    //public SerialController()
    //{
        
    //}
    
    /**
     * Add a response listener which will get invoked when a 
     * response from the StratoSnapper i received
     */
    /*
    public void addResponseListener(ResponseListener listener){
        if (!responseListeners.contains(listener))
            responseListeners.add(listener);
    }
    public void removeResponseListener(ResponseListener listener)
    {
        responseListeners.remove(listener);
    }
    */
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

        invokeConnectionChangedListeners(true);
        connected = true;
        
        return cmd;
    }
    
    private void invokeConnectionChangedListeners(boolean value)
    {
        for (ConnectionListener l : connectionListeners)
            if (l != null)
                l.connectionStateChanged(value);
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
        System.out.println("Sending: >>>" + message + "<<<");
        long endTime = System.currentTimeMillis() + timeOut;
        
        //Clear eventual abandoned messages
        while (inReader.ready()) inReader.readLine();
        
        String read = INIT_STRING;
        while (INIT_STRING.equals(read))
        {
            if (System.currentTimeMillis() > endTime)
                throw new TimeoutException("Connection timeout exceeded");
            
            //Actually send message
            outStream.write((message + ">").getBytes());
            outStream.flush();

            try {
                read = inReader.readLine();
                if (read.charAt(0) != message.charAt(0))
                {
                    System.out.println("\tDiscarding: >>>" + read + "<<<");
                    read = INIT_STRING; //Nothing was read (*woosh*)
                    //NOTE: U's are also ignored
                }
            }
            catch (IOException ex)
            {
                //Nothing to read
            }
            //The write - tryRead cycle continues until timeout
        }
        System.out.println("\tReceived: >>>" + read + "<<<");
        return read;
    }
    
    public void disconnect()
    {
        if (port == null || !connected) return;
        connected = false;
        invokeConnectionChangedListeners(false);
        port.close();
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
    /*
    public void connect(String portName) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException
    {
        if (connected) return;
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        port = (SerialPort) portIdentifier.open("LittleSmartTool 2", 1000);
        port.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        connected = true;
        reader = new SerialCommReader(responseListeners, connectionListeners);
        reader.setInputStream(port.getInputStream());
        outStream = port.getOutputStream();
        reader.setDaemon(true);
        reader.start();
        for (ConnectionListener l : connectionListeners)
            if (l != null)
                l.connectionStateChanged(ConnectionStatus.CONNECTING);
    }
    
    public void disconnect()
    {
        if (!connected) return;
        connected = false;
        reader.stopListening();
        port.close();
        for (ConnectionListener l : connectionListeners)
            if (l != null)
                l.connectionStateChanged(ConnectionStatus.DISCONNECTED);
    }*/
    
    /**
     * Get a value indicating if this controller is connected
     */
    
    public boolean connected()
    {
        return connected;
    }
    
    /**
     * Send a command to the connected StratoSnapper
     * @param command The command to send
     * @param args The argument to send along
     * @throws IOException 
     */
    /*
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
            outStream.write((message + ">").getBytes());
            outStream.flush();
        }
        catch (IOException e)
        {
            System.out.println("IOException");
            disconnect();
            throw e;
        }
    }
    
    String response;
    static boolean hasResponse = false;
    public String sendSync(String message, int timeoutms) throws IOException
    {
        hasResponse = false;
        addResponseListener(this);
        response = null;
        send(message);
        long endTime = System.currentTimeMillis() + timeoutms;
        while(!hasResponse){
            if (System.currentTimeMillis() > endTime) 
            {
                removeResponseListener(this);
                return null;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {}
        }
        removeResponseListener(this);
        return response;
    }
    
    public String sendSync(char command, String[] args, int timeoutms) throws IOException{
        String tmp = "" + command;
        if (args != null)
            for (String s : args)
                tmp += ";" + s;
        return sendSync(tmp, timeoutms);
    }
    
    public String sendSync(String message) throws IOException {
        return sendSync(message, syncTimeout);
    }
    
    public String sendSync(char command, String[] args) throws IOException{
        return sendSync(command, args, syncTimeout);
    }
    
    public void setSyncTimeout(int timeoutms){
        this.syncTimeout = timeoutms;
    }

    @Override
    public void receiveResponse(char command, String[] args) {
        response = command + "";
        for (String arg : args) response += ";" + arg;
        hasResponse = true;
    }
    
    private static class SerialCommReader extends Thread {
        private InputStream inStream;
        private List<ResponseListener> responseListeners;
        private List<ConnectionListener> connectionListeners;;
        private boolean run = true;
        public SerialCommReader(List<ResponseListener> responseListeners, List<ConnectionListener> connectionListeners){
            this.responseListeners = responseListeners;
            this.connectionListeners = connectionListeners;
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
                Thread.sleep(5000);
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
                    if (read.equals(INIT_STRING))
                    {
                        for (ConnectionListener l : connectionListeners)
                            if (l != null)
                                l.connectionStateChanged(ConnectionStatus.CONNECTED);
                        continue;
                    }
                    String[] parts = read.split(";");
                    if (parts[0].length() != 1) continue; //Failure
                    char cmd = parts[0].charAt(0);
                    String[] args = new String[parts.length-1];
                    for (int i = 0; i < args.length; i++)
                        args[i] = parts[i+1];
                    for (ResponseListener l : responseListeners)
                        if (l != null)
                            l.receiveResponse(cmd, args);
                }
            } catch (InterruptedException ex) {
                //Don't do anything
            }
        }
    }*/
}
