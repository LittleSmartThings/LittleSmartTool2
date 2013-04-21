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
public class IRCommand extends Command {
    
    private int[] pulsedata;
    private int delayBetweenRepeats, repeats, frequency;
    
    @JsonCreator
    public IRCommand(@JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("models") CameraModel[] models, @JsonProperty("pulsedata") int[] pulsedata, @JsonProperty("delayBetweenRepeats") int delayBetweenRepeats, @JsonProperty("reapeats") int repeats, @JsonProperty("frequency") int frequency)
    {
        super(name, description, models);
        this.pulsedata = pulsedata;
        this.delayBetweenRepeats = delayBetweenRepeats;
        this.repeats = repeats;
        this.frequency = frequency;
    }
    
    public int[] getPulsedata()
    {
        return pulsedata;
    }
    
    public int getDelayBetweenRepeats() {
        return delayBetweenRepeats;
    }
    
    public int getRepeats() {
        return repeats;
    }
    
    public int getFrequency() {
        return frequency;
    }
}
