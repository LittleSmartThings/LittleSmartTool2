/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

/**
 *
 * @author Rasmus
 */
public class Channel {
    private int id;
    private int calibLow = Integer.MAX_VALUE, calibHigh = 0;
    private ControlType controlType;
    private Setting setting;
    
    public Channel(int id)
    {
        this.id = id;
        setting = new Setting();
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
    
    public Setting getSetting()
    {
        return setting;
    }
    
    public Setting setSetting()
    {
        return setting;
    }
    
}
