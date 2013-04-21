/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

/**
 *
 * @author Rasmus
 */
public class LANCCommand extends Command {
    
    private byte commandByte1, commandByte2;
    
    public LANCCommand(String name, String description, CameraModel[] models, byte commandByte1, byte commandByte2)
    {
        super(name, description, models);
        this.commandByte1 = commandByte1;
        this.commandByte2 = commandByte2;
    }
    
    public byte getCommandByte1()
    {
        return commandByte1;
    }
    
    public byte getCommByte2()
    {
        return commandByte2;
    }
}
