/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

/**
 * 
 * @author Rasmus
 */
public class Block {
    private Command command;
    private Threshold upThreshold, downThreshold;
    private int interval;
    public Block(Command command, Threshold upThreshold, Threshold downThreshold, int interval)
    {
        this.command = command;
        this.upThreshold = upThreshold;
        this.downThreshold = downThreshold;
        this.interval = interval;
    }
    
    public Command getCommand()
    {
        return command;
    }
    
    public void setCommand(Command command)
    {
        this.command = command;
    }
    
    public Threshold getUpThreshold()
    {
        return upThreshold;
    }
    
    public void setUpThreshold(Threshold threshold)
    {
        this.upThreshold = threshold;
    }
    
    public Threshold getDownThreshold()
    {
        return downThreshold;
    }
    
    public void setDownThreshold(Threshold threshold)
    {
        this.downThreshold = threshold;
    }
    
    public int getInterval()
    {
        return interval;
    }
    
    public void setInterval(int interval)
    {
        this.interval = interval;
    }
}
