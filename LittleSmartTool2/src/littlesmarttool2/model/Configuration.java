/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

import java.util.*;
import static littlesmarttool2.model.ConnectionType.*;

/**
 *
 * @author Rasmus
 */
public class Configuration {
    private CameraBrand cameraBrand;
    private CameraModel cameraModel;
    private ConnectionType outputType;
    private ArrayList<Channel> channels = new ArrayList<>();
    private int irFreq;
    private int maxIRPulse, maxIR, maxLANC, maxTriggers, maxRanges;
    
    private boolean timelapse;
    private Command timelapseCommand = IRCommand.getNothingCommand();
    private int timelapseDelay = 10; // 1/10th seconds, 10 = 1 second
    
    public Configuration()
    {
        channels.add(new Channel(1));
        channels.add(new Channel(2));
        channels.add(new Channel(3));
        channels.add(new Channel(4));
    }

    public CameraBrand getCameraBrand() {
        return cameraBrand;
    }
    
    public void setCameraBrand(CameraBrand cameraBrand) {
        this.cameraBrand = cameraBrand;
    }
    
    public CameraModel getCameraModel() {
        return cameraModel;
    }
    
    public void setCameraModel(CameraModel cameraModel) {
        this.cameraModel = cameraModel;
    }
    
    public ConnectionType getOutputType()
    {
        return outputType;
    }
    
    public void setOutputType(ConnectionType type)
    {
        this.outputType = type;
    }
    
    public boolean isTimelapse() {
        return timelapse;
    }

    public void setTimelapse(boolean timelapse) {
        this.timelapse = timelapse;
    }

    public Command getTimelapseCommand() {
        return timelapseCommand;
    }

    public void setTimelapseCommand(Command timelapseCommand) {
        this.timelapseCommand = timelapseCommand;
    }

    public int getTimelapseDelay() {
        return timelapseDelay;
    }

    public void setTimelapseDelay(int timelapseDelayMs) {
        this.timelapseDelay = timelapseDelayMs;
    }
    
    /**
     * Returns true, if the command is used anywhere in the configuration.
     */
    public boolean isCommandUsed(Command command) {
        if(command==null) return false;
        if(isTimelapse())
            return command.sameAs(getTimelapseCommand());
        
        for (Channel channel : getChannels()) {
            for (Block block : channel.getSetting().getBlocks()) {
                if(command.sameAs(block.getCommand())) return true;
            }
            for (Threshold threshold : channel.getSetting().getThresholds()) {
                if(command.sameAs(threshold.getUpCommand())) return true;
                if(command.sameAs(threshold.getDownCommand())) return true;
            }
        }
        
        return false;
    }
    
    /**
     * Returns true if any commands are assigned
     */
    public boolean hasCommandsAssigned()
    {
        for (Channel channel : getChannels()) {
            for (Block block : channel.getSetting().getBlocks()) {
                if(Command.getNothingCommand() != block.getCommand()) return true;
            }
            for (Threshold threshold : channel.getSetting().getThresholds()) {
                if(Command.getNothingCommand() != threshold.getUpCommand()) return true;
                if(Command.getNothingCommand() != threshold.getDownCommand()) return true;
            }
        }
        return false;
    }
    
    /**
     * Removes all assigned commands making them "Nothing" commands instead
     */
    public void removeAllCommands()
    {
        if (isTimelapse()) setTimelapseCommand(Command.getNothingCommand());
        for (Channel channel : getChannels()) {
            for (Block block : channel.getSetting().getBlocks()) {
                block.setCommand(Command.getNothingCommand());
            }
            for (Threshold threshold : channel.getSetting().getThresholds()) {
                threshold.setUpCommand(Command.getNothingCommand());
                threshold.setDownCommand(Command.getNothingCommand());
            }
        }
    }
    
    /**
     * Delete all occurrences of the given command in the configuration.
     */
    public void deleteCommand(Command command) {
        if(command==null) return;
        if(command.sameAs(getTimelapseCommand())) setTimelapseCommand(IRCommand.getNothingCommand());
        for (Channel channel : getChannels()) {
            for (Block block : channel.getSetting().getBlocks()) {
                if(command.sameAs(block.getCommand())) 
                    block.setCommand(IRCommand.getNothingCommand());
            }
            for (Threshold threshold : channel.getSetting().getThresholds()) {
                if(command.sameAs(threshold.getUpCommand()))   
                    threshold.setUpCommand(IRCommand.getNothingCommand());
                if(command.sameAs(threshold.getDownCommand())) 
                    threshold.setDownCommand(IRCommand.getNothingCommand());
            }
        }
    }
    
    public ArrayList<Channel> getChannels()
    {
        return channels;
    }
    
    public List<? extends Command> getRelevantCommands() {
        if(getCameraModel() == null)
            return null; //And order pizza
        switch(getOutputType()){
            case Wire:
                return getCameraModel().getWireCommands();
            case IR:
                return getCameraModel().getIRCommands();
            case LANC:
                return getCameraModel().getLANCCommands();
        }
        return null; //And order pizza
    }
    
    public int getIRFreq()
    {
        return irFreq;
    }
    public void setIRFreq(int freq)
    {
        this.irFreq = freq;
    }
    

    public int getMaxRanges() {
        return this.maxRanges;
    }
    public void setMaxRanges(int maxRanges) {
        this.maxRanges = maxRanges;
    }
    public int getRemainingRanges(){
        int nb = 0;
        for (Channel channel : getChannels()) {
            for (Block block : channel.getSetting().getBlocks()) {
                if(block.getCommand() != Command.getNothingCommand()) nb++;
            }
        }
        return maxRanges - nb;
    }

    public int getMaxTriggers() {
       return this.maxTriggers;
    }
    public void setMaxTriggers(int maxTriggers) {
        this.maxTriggers = maxTriggers;
    }
    public int getRemainingTriggers(){
        int nb = 0;
        for (Channel channel : getChannels()) {
            for (Threshold threshold : channel.getSetting().getThresholds()) {
                if(threshold.getUpCommand() != Command.getNothingCommand()) nb++;
                if(threshold.getDownCommand() != Command.getNothingCommand()) nb++;
            }
        }
        return maxTriggers - nb;
    }

    
    public int getMaxLANC() {
        return this.maxLANC;
    }
    public void setMaxLANC(int maxLANC) {
        this.maxLANC = maxLANC;
    }
    public int getRemainingLANC(){
        HashSet<Command> commands = new HashSet<>();
        int nb = 0;
        for (Channel channel : getChannels()) {
            for (Block block : channel.getSetting().getBlocks()) {
                if(block.getCommand().getClass() == LANCCommand.class &&
                   !commands.contains(block.getCommand())) {
                    commands.add(block.getCommand());
                    nb++;
                }
            }
            
            for (Threshold threshold : channel.getSetting().getThresholds()) {
                if(threshold.getUpCommand().getClass() == LANCCommand.class &&
                   !commands.contains(threshold.getUpCommand())) {
                    commands.add(threshold.getUpCommand());
                    nb++;
                }
                if(threshold.getDownCommand().getClass() == LANCCommand.class &&
                   !commands.contains(threshold.getDownCommand())) {
                    commands.add(threshold.getDownCommand());
                    nb++;
                }
            }
        }
        return maxLANC - nb;
    }

    
    public int getMaxIR() {
        return this.maxIR;
    }
    public void setMaxIR(int maxIR) {
        this.maxIR = maxIR;
    }
    public int getRemainingIR(){
        HashSet<Command> commands = new HashSet<>();
        int nb = 0;
        for (Channel channel : getChannels()) {
            for (Block block : channel.getSetting().getBlocks()) {
                if(block.getCommand().getClass() == IRCommand.class &&
                   !commands.contains(block.getCommand())) {
                    commands.add(block.getCommand());
                    nb++;
                }
            }
            
            for (Threshold threshold : channel.getSetting().getThresholds()) {
                if(threshold.getUpCommand().getClass() == IRCommand.class &&
                   !commands.contains(threshold.getUpCommand())) {
                    commands.add(threshold.getUpCommand());
                    nb++;
                }
                if(threshold.getDownCommand().getClass() == IRCommand.class &&
                   !commands.contains(threshold.getDownCommand())) {
                    commands.add(threshold.getDownCommand());
                    nb++;
                }
            }
        }
        return maxIR - nb;
    }

    
    public int getMaxIRPulse() {
        return this.maxIRPulse;
    }
    public void setMaxIRPulse(int maxIRPulse) {
        this.maxIRPulse = maxIRPulse;
    }
}
