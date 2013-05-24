/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

import java.util.ArrayList;
import java.util.HashMap;
import littlesmarttool2.util.JSON;

/**
 *
 * @author marcher89
 */
public class ModelUtil {
    
    static CameraBrand[] cameraBrands;
    static IRCommand[] irCommands;
    static WireCommand[] wireCommands;
    static LANCCommand[] lancCommands;
    
    public static void LoadData() {
        cameraBrands = JSON.readObjectFromFile("../data/cameraList.json", CameraBrand[].class);
        
        HashMap<String, CameraModel> map = new HashMap<>();
        
        //Mapping identifiers to camera models
        for (CameraBrand cameraBrand : cameraBrands) {
            for (CameraModel cameraModel : cameraBrand.getModels()) {
                map.put(cameraModel.getIdentifier(), cameraModel);
            }
        }
        
        CameraModel[] models;
        int i;
        
        irCommands = JSON.readObjectFromFile("../data/IRCommandList.json", IRCommand[].class);
        wireCommands = JSON.readObjectFromFile("../data/WireCommandList.json", WireCommand[].class);
        lancCommands = JSON.readObjectFromFile("../data/LANCCommandList.json", LANCCommand[].class);
        
        for (IRCommand command : irCommands) {
            models = new CameraModel[command.getCameraModels().length];
            i=0;
            for (CameraModel cModel : command.getCameraModels()) {
                models[i++] = map.get(cModel.getIdentifier());
                map.get(cModel.getIdentifier()).addIRCommand(command);
            }
            command.setCameraModels(models);
        }
        
        for (WireCommand command : wireCommands) {
            models = new CameraModel[command.getCameraModels().length];
            i=0;
            for (CameraModel cModel : command.getCameraModels()) {
                models[i++] = map.get(cModel.getIdentifier());
                map.get(cModel.getIdentifier()).addWireCommand(command);
            }
            command.setCameraModels(models);
        }
        
        for (LANCCommand command : lancCommands) {
            models = new CameraModel[command.getCameraModels().length];
            i=0;
            for (CameraModel cModel : command.getCameraModels()) {
                models[i++] = map.get(cModel.getIdentifier());
                map.get(cModel.getIdentifier()).addLANCCommand(command);
            }
            command.setCameraModels(models);
        }
    }
}
