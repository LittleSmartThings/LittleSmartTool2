/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.commold;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.io.*;
/**
 *
 * @author Rasmus
 */
public class CommTester {
    
    public CommTester() throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException, InterruptedException
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier("COM6");
        SerialPort serialPort = (SerialPort) portIdentifier.open("LittleSmartTool 2", 1000);
        serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
        Thread.sleep(1500);
        serialPort.getOutputStream().write(new byte[]{'S', '>'});
        while (true)
        {
            String read = bufferedReader.readLine();
            if (read == null) {
                Thread.sleep(10);
                continue;
            }
            System.out.println(read);
            serialPort.getOutputStream().write(new byte[]{'S', '>'});
        }
        
    }

    public static void main(String args[]) throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException, InterruptedException
    {
        new CommTester();
    }
}
