/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

/**
 * 
 * @author Rasmus
 */
public class Block implements Comparable<Block>{
    private Command command;
    private Threshold lowerThreshold, upperThreshold;
    private int interval;
    public Block(Command command, Threshold lowerThreshold, Threshold upperThreshold, int interval)
    {
        this.command = command;
        this.lowerThreshold = lowerThreshold;
        this.upperThreshold = upperThreshold;
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
    
    public Threshold getLowerThreshold()
    {
        return lowerThreshold;
    }
    
    public void setLowerThreshold(Threshold threshold)
    {
        this.lowerThreshold = threshold;
    }
    
    public Threshold getUpperThreshold()
    {
        return upperThreshold;
    }
    
    public void setUpperThreshold(Threshold threshold)
    {
        this.upperThreshold = threshold;
    }
    
    public int getInterval()
    {
        return interval;
    }
    
    public void setInterval(int interval)
    {
        this.interval = interval;
    }
    
    @Override
    public int compareTo(Block o) {
        if (this.getLowerThreshold() == null) return -1;
        if (o.getLowerThreshold() == null) return 1;
        if (this.getLowerThreshold().getValuePromille() < o.getLowerThreshold().getValuePromille()) return -1; 
        if (this.getLowerThreshold().getValuePromille() == o.getLowerThreshold().getValuePromille()) return 0; 
        return 1;
    }
}
