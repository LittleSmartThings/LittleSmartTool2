/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

import com.fasterxml.jackson.annotation.*;
import littlesmarttool2.util.JSON;

/**
 *
 * @author amrcher89
 */
public class CameraBrand {

    
    public static CameraBrand[] getArray() {
        if(null==ModelUtil.cameraBrands) 
            ModelUtil.LoadData();
        return ModelUtil.cameraBrands;
    }

    private String brandName;
    private CameraModel[] models;

    @JsonCreator
    private CameraBrand(@JsonProperty("brandName") String brandName, @JsonProperty("models") CameraModel[] models){
        this.brandName = brandName;
        this.models = models;
    }

    private CameraBrand() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
