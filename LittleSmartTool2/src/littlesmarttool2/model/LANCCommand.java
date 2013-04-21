/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

import com.fasterxml.jackson.annotation.*;

/**
 *
 * @author Rasmus
 */
public class LANCCommand extends Command {
    
    private byte commandByte1, commandByte2;
    
    @JsonCreator
    public LANCCommand(@JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("models") CameraModel[] models, @JsonProperty("commandByte1") byte commandByte1, @JsonProperty("commandByte2") byte commandByte2)
    {
        super(name, description, models);
        this.commandByte1 = commandByte1;
        this.commandByte2 = commandByte2;
    }
    
    public byte getCommandByte1()
    {
        return commandByte1;
    }
    
    public byte getCommandByte2()
    {
        return commandByte2;
    }
}
