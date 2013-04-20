/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.comm;

import gnu.io.CommPortIdentifier;  
import gnu.io.SerialPort;  
   
public class Connection {
    public SerialPort serialPort;
     
    public SerialPort connectToSerial(String portName) throws Exception {  
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);  
   
        if (portIdentifier.isCurrentlyOwned()) {  
            System.out.println("Port in use!");  
        } else {  
            // points who owns the port and connection timeout  
            serialPort = (SerialPort) portIdentifier.open("Little Smart Tool", 2000);  
              
            // setup connection parameters  
            serialPort.setSerialPortParams(  
                57600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            //serialPort.setFlowControlMode(serialPort.FLOWCONTROL_NONE);
            
            // setup serial port writer  
            CommPortSender.setWriterStream(serialPort.getOutputStream());  
              
            // setup serial port reader  
            new CommPortReceiver(serialPort.getInputStream()).start();  
            
            // A pause to allow the connection to setup before use
            Thread.sleep(1500);            
        }  
        return serialPort;
    }
      
    public static void main(String[] args) throws Exception {
        SerialPort serPort;
        String port = "COM6";
        String message = "#?!";
        
        if (args != null && args.length == 2) {          
            port = args[0];
            message = args[1];
        }
            
        // connects to the port which name (e.g. COM1) is in the first arg  
        serPort = new Connection().connectToSerial(port);
          
        // send message in second arg through serial port using protocol implementation  
        CommPortSender.send(new ProtocolImpl().getMessage(message));
        
        //serPort.close();
    }  
}  