/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

import com.fasterxml.jackson.annotation.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import littlesmarttool2.util.JSON;

/**
 *
 * @author Rasmus
 */
public class IRCommand extends Command {
    
    private int[] pulsedata;
    private int delayBetweenRepeats, repeats, frequency;
    private final boolean custom;
    
    public static IRCommand[] getArray() {
        if(null==ModelUtil.standardIRCommands) 
            ModelUtil.LoadData();
        IRCommand[] array = new IRCommand[ModelUtil.standardIRCommands.length + ModelUtil.customIRCommands.size()];
        int i = 0;
        for (IRCommand command : ModelUtil.standardIRCommands) {
            array[i++] = command; 
        }
        for (IRCommand command : ModelUtil.customIRCommands.keySet()) {
            array[i++] = command;
        }
        return array;
    }
    
    public static List<IRCommand> getCustomCommandsList() {
        if(null==ModelUtil.customIRCommands) 
            ModelUtil.LoadData();
        return new ArrayList(ModelUtil.customIRCommands.keySet());
    }
    
    @JsonCreator
    public IRCommand(@JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("models") CameraModel[] models, @JsonProperty("pulsedata") int[] pulsedata, @JsonProperty("delayBetweenRepeats") int delayBetweenRepeats, @JsonProperty("reapeats") int repeats, @JsonProperty("frequency") int frequency, @JsonProperty("custom") boolean custom)
    {
        super(name, description, models);
        this.pulsedata = pulsedata;
        this.delayBetweenRepeats = delayBetweenRepeats;
        this.repeats = repeats;
        this.frequency = frequency;
        this.custom = custom;
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
    
    public boolean getCustom(){
        return custom;
    }

    @Override
    public boolean sameAs(Command other) {
        if (other == null) return false;
        if (other.getClass() != getClass()) return false;
        IRCommand otherIR = (IRCommand) other;
        if (getFrequency() != otherIR.getFrequency()) return false;
        if (getDelayBetweenRepeats() != otherIR.getDelayBetweenRepeats()) return false;
        if (getRepeats() != otherIR.getRepeats()) return false;
        if (!Arrays.equals(getPulsedata(), otherIR.getPulsedata())) return false;
        return true;
    }
}
