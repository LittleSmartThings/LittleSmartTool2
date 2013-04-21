/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

import com.fasterxml.jackson.annotation.*;

/**
 *
 * @author amrcher89
 */
public class CameraBrand {

    private String brandName;
    private CameraModel[] models;

    @JsonCreator
    public CameraBrand(@JsonProperty("brandName") String brandName, @JsonProperty("models") CameraModel[] models){
        this.brandName = brandName;
        this.models = models;
    }
    
    public String getBrandName() {
        return brandName;
    }

    public CameraModel[] getModels() {
        return models;
    }
    
    @Override
    public String toString() {
        return getBrandName();
    }
}
