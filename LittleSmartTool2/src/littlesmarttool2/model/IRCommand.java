/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

/**
 *
 * @author Rasmus
 */
public class IRCommand extends Command {
    
    private int[] pulsedata;
    private int delayBetweenRepeats, repeats, frequency;
    
    public IRCommand(String name, String description, CameraModel[] models, int[] pulsedata, int delayBetweenRepeats, int repeats, int frequency)
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
