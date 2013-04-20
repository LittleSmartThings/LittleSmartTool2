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
    
    public IRCommand(String name, String description, int[] pulsedata)
    {
        super(name, description);
        this.pulsedata = pulsedata;
    }
    
    public int[] getPulsedata()
    {
        return pulsedata;
    }
}
