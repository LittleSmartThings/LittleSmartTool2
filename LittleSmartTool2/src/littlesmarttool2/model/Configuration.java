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
    private ArrayList<Command> relevantCommands;
    private int irFreq;
    
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
    
}
