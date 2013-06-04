/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

import java.util.*;
import static littlesmarttool2.model.ControlType.*;

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
    private final int closeness = 10;
    
    /**
     * Initialize a new Setting not specialized to any control type
     */
    public Setting()
    {
        blocks.add(new Block(Command.getNothingCommand(), null, null, 0));
    }
    
    public Setting(ArrayList<Block> blocks, ArrayList<Threshold> thresholds)
    {
        this.blocks = blocks;
        this.thresholds = thresholds;
    }
    
    public void defaultDivision(ControlType type)
    {
        blocks.clear();
        thresholds.clear();
        switch(type)
        {
            case Switch2:
            case PushButton:
                divide(2);
                break;
            case Switch3:
                divide(3);
                break;
            //case Stick:
                //divide(5);
                //break;
            default:
                blocks.add(new Block(Command.getNothingCommand(), null, null, 0));
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
            position = 500; //First threshold half way
        else
            position = (1000-lastThreshold.getValuePromille())/2 + lastThreshold.getValuePromille();
        
        if (position > 998) return; //Don't add too many
        
        Threshold newThreshold = new Threshold(position, Command.getNothingCommand(), Command.getNothingCommand());
        Block newBlock = new Block(Command.getNothingCommand(), newThreshold, null, 0);
        if (lastBlock != null) lastBlock.setUpperThreshold(newThreshold);
        
        thresholds.add(newThreshold);
        blocks.add(newBlock);
    }
    
    public void addThreshold(int valuePromille) {
        
        //Find the block affected
        Block affectedBlock = blocks.get(0);
        for (Block block : getBlocks()) {
            if(block.getLowerThreshold() == null || block.getLowerThreshold().getValuePromille() < valuePromille)
                affectedBlock = block;
            else break;
        }
        
        //Move the value if it is too close to others.
        if(affectedBlock.getLowerThreshold() != null && Math.abs(affectedBlock.getLowerThreshold().getValuePromille() - valuePromille) < closeness)
            valuePromille += closeness;
        
        if(affectedBlock.getUpperThreshold() != null && Math.abs(affectedBlock.getUpperThreshold().getValuePromille() - valuePromille) < closeness)
            valuePromille -= closeness;
        
        if(valuePromille > 1000) valuePromille = 1000;
        if(valuePromille < 0) valuePromille = 0;
        
        //Find (new) block to be split
        for (Block block : getBlocks()) {
            if(block.getLowerThreshold() == null || block.getLowerThreshold().getValuePromille() < valuePromille)
                affectedBlock = block;
            else break;
        }
        
        
        Threshold highThreshold = affectedBlock.getUpperThreshold();
        
        Threshold newThreshold = new Threshold(valuePromille, Command.getNothingCommand(), Command.getNothingCommand());
        affectedBlock.setUpperThreshold(newThreshold);
        
        Block newBlock = new Block(Command.getNothingCommand(), newThreshold, highThreshold, 0);
        
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
            ths[i+1] = new Threshold((int)((1000.0/sections)*(i+1)), Command.getNothingCommand(), Command.getNothingCommand());
            thresholds.add(ths[i+1]);
        }
        for (int i = 0; i < sections; i++)
        {
            blocks.add(new Block(Command.getNothingCommand(), ths[i], ths[i+1], 0));
        }
    }
    
    public void removeThreshold(Threshold threshold, boolean saveBefore)
    {
        Block before = null, after = null;
        for (Block b : blocks)
        {
            if (b.getLowerThreshold() == threshold) after = b;
            if (b.getUpperThreshold() == threshold) before = b;
        }
        //Save before
        if (before == null || after == null) 
            throw new IllegalStateException("The configuration was in an illegal state");
        thresholds.remove(threshold);
        if (saveBefore)
        {
            blocks.remove(after);
            before.setUpperThreshold(after.getUpperThreshold());
        }
        else
        {
            blocks.remove(before);
            after.setLowerThreshold(before.getLowerThreshold());
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
        Collections.sort(blocks);
        return blocks;
    }
    
    public ArrayList<Threshold> getThresholds()
    {
        Collections.sort(thresholds);
        return thresholds;
    }
    
    public ArrayList<CameraModel> getCameraModels()
    {
        return cameraModels;
    }
}
