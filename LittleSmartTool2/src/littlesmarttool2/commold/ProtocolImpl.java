/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.commold;

/**
 *
 * @author Administrator
 */
class ProtocolImpl implements Protocol {
    byte[] buffer = new byte[1024];  
    int tail = 0;  

    public ProtocolImpl() {
    }
    
    /**
     * Method onReceive defines a simple protocol which buffers bytes until 
     * a newline sewquence is recognized, upon which the message is forced
     * toString conversion and printed to System.out
     * @param b , a byte
     */
    public void onReceive(byte b) {  
        // simple protocol: each message ends with new line  
        if (b=='\n') {  
            onMessage();  
        } else {  
            buffer[tail] = b;  
            tail++;  
        }  
    }  
   
    /**
     * Method onStrreamClosed calls onMessage to send remaining buffer to System.out
     */
    public void onStreamClosed() {  
        onMessage();  
    }  
      
    /**
     * 
     * Method onMessage is invoked  
     */  
    private void onMessage() {  
        if (tail!=0) {  
            // constructing message  
            String message = getMessage(buffer, tail);  
            System.out.println("RECEIVED MESSAGE: " + message);  
                
            tail = 0;  
        }  
    }  
      
    // helper methods   
    public byte[] getMessage(String message) {  
        return (message+"\n").getBytes();  
    }  
      
    public String getMessage(byte[] buffer, int len) {  
        return new String(buffer, 0, tail);  
    }
}
