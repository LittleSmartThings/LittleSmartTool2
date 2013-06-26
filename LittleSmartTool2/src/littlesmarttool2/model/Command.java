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
    private static Command nothingCommand = new WireCommand("Nothing","Do nothing",null,0,0);
    
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
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public CameraModel[] getCameraModels() {
        return cameraModels;
    }

    void setCameraModels(CameraModel[] models) {
        this.cameraModels = models;
    }
    
    public static Command getNothingCommand()
    {
        return nothingCommand;
    }
    
    /**
     * Almost an equal method. Compares action
     * @param other The other command to compare with
     * @return True if the two commands perform the same action
     */
    public abstract boolean sameAs(Command other);
    
    @Override
    public String toString() {
        return getName();
    }
}
