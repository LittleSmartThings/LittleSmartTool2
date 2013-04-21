/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

import java.util.ArrayList;

/**
 *
 * @author Rasmus
 */
public class Channel {
    private int id;
    private int calibLow = Integer.MAX_VALUE, calibHigh = 0;
    private ControlType controlType;
    
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Threshold> thresholds = new ArrayList<>();
    
    public Channel(int id)
    {
        this.id = id;
    }
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getCalibLow()
    {
        return calibLow;
    }
    
    public void setCalibLow(int value)
    {
        calibLow = value;
    }
    
    public int getCalibHigh()
    {
        return calibHigh;
    }
    
    public void setCalibHigh(int value)
    {
        calibHigh = value;
    }
    
    public ControlType getControlType()
    {
        return controlType;
    }
    
    public void setControlType(ControlType type)
    {
        controlType = type;
    }
    
    public ArrayList<Block> getBlocks()
    {
        return blocks;
    }
    
    public ArrayList<Threshold> getThresholds()
    {
        return thresholds;
    }
    
}
