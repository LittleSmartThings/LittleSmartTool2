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
    private CameraModel[] models;
    
    @JsonCreator
    public Command(@JsonProperty("name") String name, @JsonProperty("description") String description, @JsonProperty("models") CameraModel[] models)
    {
        this.name = name;
        this.description = description;
        this.models = models;
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
        return models;
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
