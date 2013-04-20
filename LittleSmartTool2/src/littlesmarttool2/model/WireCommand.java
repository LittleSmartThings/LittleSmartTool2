/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

/**
 *
 * @author Rasmus
 */
public class WireCommand extends Command {
   
    private int commandId;
    
    public WireCommand(String name, String description, int commandId)
    {
        super(name, description);
        this.commandId = commandId;
    }
    
    public int getCommandId()
    {
        return commandId;
    }
}
