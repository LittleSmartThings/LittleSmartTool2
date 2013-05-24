/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

import java.util.ArrayList;

/**
 * A setting is a set of thresholds and blocks defining the complete behavior
 * of a unspecified channel
 * When serialized and saved to disk this is known as a preset
 * @author Rasmus
 */
public class Setting {
    private ArrayList<ControlType> types = new ArrayList<>(); 
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Threshold> thresholds = new ArrayList<>();
    private ArrayList<CameraModel> cameraModels = new ArrayList<>();
    //TODO: If this is to be serialized, should it have a no args constructor 
    //that doesn't add a block?
    
    /**
     * Initialize a new Setting not specialized to any control type
     */
    public Setting()
    {
        blocks.add(new Block(null, null, null, 0));
    }
    
    /**
     * Initialize a new Setting specialized to a specific control type
     * @param type The ControlType to initialize this setting for
     */
    public Setting(ControlType type)
    {
        switch(type)
        {
            case Switch2:
            case PushButton:
                divide(2);
                break;
            case Switch3:
                divide(3);
                break;
            case Stick:
                divide(5);
                break;
            default:
                blocks.add(new Block(null, null, null, 0));
        }
    }
    
    /**
     * Add a new threshold + block to this setting
     * The new section will be added as half this width of the rightmost
     * current section
     */
    public void addSection()
    {
        Threshold lastThreshold = (thresholds.isEmpty()) ? null : thresholds.get(thresholds.size()-1);
        Block lastBlock = (blocks.isEmpty()) ? null : blocks.get(blocks.size()-1);
        
        
        int position;
        if (lastThreshold == null)
            position = 500; //First th half way
        else
            position = (1000-lastThreshold.getValuePromille())/2 + lastThreshold.getValuePromille();
        
        Threshold newThreshold = new Threshold(position, null, null);
        Block newBlock = new Block(null, newThreshold, null, 0);
        if (lastBlock != null) lastBlock.setUpperThreshold(newThreshold);
        
        thresholds.add(newThreshold);
        blocks.add(newBlock);
    }
    
    /**
     * Divide the area in sections of equal size
     * @param sections How many sections to create (more than 0)
     */
    private void divide(int sections)
    {
        Threshold[] ths = new Threshold[sections+1]; //null in both ends
        for (int i = 0; i < sections-1;i++)
        {
            ths[i+1] = new Threshold((int)((1000.0/sections)*(i+1)), null, null);
            thresholds.add(ths[i+1]);
        }
        for (int i = 0; i < sections; i++)
        {
            blocks.add(new Block(null, ths[i], ths[i+1], 0));
        }
    }
    
    /**
     * Get the list of ControlTypes that fits to this setting
     */
    public ArrayList<ControlType> getTypes()
    {
        return types;
    }
    
    public ArrayList<Block> getBlocks()
    {
        return blocks;
    }
    
    public ArrayList<Threshold> getThresholds()
    {
        return thresholds;
    }
    
    public ArrayList<CameraModel> getCameraModels()
    {
        return cameraModels;
    }
}
