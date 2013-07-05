/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package littlesmarttool2.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcher89
 */
public class CameraModel {
    private String identifier;
    private String modelName;
    public ConnectionType[] connectionTypes;
    
    @JsonCreator
    public CameraModel(@JsonProperty("identifier") String identifier, @JsonProperty("models") String modelName, @JsonProperty("connectionTypes") ConnectionType[] connectionTypes) {
        this.identifier = identifier;
        this.modelName = modelName;
        this.connectionTypes = connectionTypes;
    }

    public String getIdentifier() {
        return identifier;
    }
    
    public String getModelName() {
        return modelName;
    }
    
    public ConnectionType[] getConnectionTypes() {
        int i = 0;
        if(getWireCommands().size() > 0) i++;
        if(getIRCommands().size() > 0) i++;
        if(getLANCCommands().size() > 0) i++;
        connectionTypes = new ConnectionType[i];
        i = 0;
        if(getWireCommands().size() > 0) connectionTypes[i++] = ConnectionType.Wire;
        if(getIRCommands().size() > 0) connectionTypes[i++] = ConnectionType.IR;
        if(getLANCCommands() .size() > 0) connectionTypes[i++] = ConnectionType.LANC;
        return connectionTypes;
    }
    
    @JsonBackReference
    public List<WireCommand> getWireCommands(){
        return ModelUtil.getWireCommandsForCamera(this);
    }
    
    @JsonBackReference
    public List<IRCommand> getIRCommands(){
        return ModelUtil.getIRCommandsForCamera(this);
    }
    
    @JsonBackReference
    public List<IRCommand> getCustomIRCommands(){
        List<IRCommand> commands = new ArrayList<IRCommand>();
        for (IRCommand command : getIRCommands()) {
            if(command.getCustom())
                commands.add(command);
        }
        return commands;
    }
    
    @JsonBackReference
    public List<LANCCommand> getLANCCommands(){
        return ModelUtil.getLANCCommandsForCamera(this);
    }
    
    @Override
    public String toString() {
        return identifier;
    }
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof CameraModel))
            return false;
        CameraModel other = (CameraModel) obj;
        return other.getIdentifier().equals(this.getIdentifier());
    }

}
