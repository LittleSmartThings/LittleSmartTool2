/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package littlesmarttool2.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin Donovan
 */
public class CameraProfile {
    private String models;
    private CameraFunction [] functions;
    
    public List<String> getFunctionList() {
        ArrayList<String> functionList = new ArrayList<String>();
        for (CameraFunction cameraFunction : functions) {
            functionList.add(cameraFunction.getId());
        }
        return functionList;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public CameraFunction[] getFunctions() {
        return functions;
    }

    public void setFunctions(CameraFunction[] functions) {
        this.functions = functions;
    }
    
    @Override
    public String toString() {
        return getModels();
    }

}
