/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.comm;

/**
 *
 * @author Rasmus
 */
public interface ResponseListener {
    /**
     * Invoked when a response is received
     * @param command The (upper case) command that it is a response to
     * @param args The arguments of the response
     */
    void receiveResponse(char command, String[] args);
}
