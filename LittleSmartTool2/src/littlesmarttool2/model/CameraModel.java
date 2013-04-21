/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package littlesmarttool2.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author marcher89
 */
public class CameraModel {
    private String identifier;
    private String modelName;
    
    @JsonCreator
    public CameraModel(@JsonProperty("identifier") String identifier, @JsonProperty("models") String modelName) {
        this.identifier = identifier;
        this.modelName = modelName;
    }

    public String getIdentifier() {
        return identifier;
    }
    
    public String getModelName() {
        return modelName;
    }
    
    @Override
    public String toString() {
        return getModelName();
    }

}
