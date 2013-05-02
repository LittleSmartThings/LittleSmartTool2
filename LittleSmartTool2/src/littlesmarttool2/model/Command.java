/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

import com.fasterxml.jackson.annotation.*;

/**
 *
 * @author Rasmus
 */
public abstract class Command {
    private String name;
    private String description;
    private CameraModel[] cameraModels;
    
    @JsonCreator
    public Command(@JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("cameraModels") CameraModel[] cameraModels)
    {
        this.name = name;
        this.description = description;
        this.cameraModels = cameraModels;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public CameraModel[] getCameraModels() {
        return cameraModels;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
