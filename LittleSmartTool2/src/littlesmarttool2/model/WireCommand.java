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
   
    private int pulseLength, pinConfig;
    
    public WireCommand(String name, String description, CameraModel[] models, int pulseLength, int pinConfig)
    {
        super(name, description, models);
        this.pulseLength = pulseLength;
        this.pinConfig = pinConfig;
    }
    
    public int getPulseLength() {
        return pulseLength;
    }
    
    public int getPinConfig() {
        return pinConfig;
    }
}
