/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

/**
 *
 * @author Rasmus
 */
public class Threshold {
    private int value;
    private Command upCommand, downCommand;
    
    public Threshold(int value, Command upCommand, Command downCommand)
    {
        this.value = value;
        this.upCommand = upCommand;
        this.downCommand = downCommand;
    }
    
    public Command getUpCommand()
    {
        return upCommand;
    }
    
    public void setUpCommand(Command command)
    {
        this.upCommand = command;
    }
    
    public Command getDownCommand()
    {
        return downCommand;
    }
    
    public void setDownCommand(Command command)
    {
        this.downCommand = command;
    }
    
    public int getValue()
    {
        return value;
    }
    
    public void setValue(int value)
    {
        this.value = value;
    }
}
