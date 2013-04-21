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
public class Configuration {
    private CameraBrand cameraBrand;
    private CameraModel cameraModel;
    private ConnectionType outputType;
    private ArrayList<Channel> channels = new ArrayList<>();
    private int irFreq;
    
    public Configuration()
    {
        
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
    
    public int getIRFreq()
    {
        return irFreq;
    }
    public void setIRFreq(int freq)
    {
        this.irFreq = freq;
    }
    
}
