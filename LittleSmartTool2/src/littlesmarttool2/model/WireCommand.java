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
public class WireCommand extends Command {
   
    private int pulseLength, pinConfig;
    
    @JsonCreator
    public WireCommand(@JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("models") CameraModel[] models, @JsonProperty("pulseLength") int pulseLength, @JsonProperty("pinConfig") int pinConfig)
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
