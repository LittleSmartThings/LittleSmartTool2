/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import littlesmarttool2.model.*;

/**
 *
 * @author marcher89
 */
public class JSON {
    
    public static <T> T readObjectFromFile(String filename, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            InputStream stream = JSON.class.getResourceAsStream("../data/"+filename);

            // read from file, convert it to user class
            T value = mapper.readValue(stream, clazz);
            //camList = mapper.readValue(new File(file), CameraList.class);
            return value;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean writeObjectToFile(Object object, String filename) {
	try {
                ObjectMapper mapper = new ObjectMapper();
 
		// convert user object to json string, and save to a file
		mapper.writeValue(new File(filename), object);
 
                return true;
	} catch (IOException e) {
            return false;
	}
    } 
    
    public static void main(String[] args) {
        /*CameraModel[] models = new CameraModel[]{new CameraModel("Sony - NEX Series","NEX Series"), new CameraModel("Sony - A230, A330", "A230, A330")};
        Command[] list2 = new Command[]{
            new IRCommand("IR 1", "This number one", models, new int[]{1,2,3,4,5,6,8,4,2,5,7,4}, 42, 2, 38000),
            new WireCommand("Wire 2", "This is two too", models, 74, 2),
            new LANCCommand("LANC 3", "THIS IS LANC!!!", models, (byte)'c', (byte)'r')
        };
        writeObjectToFile(list2, "comanndList.json");
        
        System.exit(0);
        CameraBrand[] list = new CameraBrand[]{
            new CameraBrand("Sony", new CameraModel[]{
                new CameraModel("Sony - NEX Series","NEX Series"), new CameraModel("Sony - A230, A330", "A230, A330")
            })
        };
        writeObjectToFile(list, "newCameraList.json");*/
        CameraBrand[] list = readObjectFromFile("camera-list.json", CameraBrand[].class);
        for (CameraBrand cameraBrand : list) {
            for (CameraModel cameraModel : cameraBrand.getModels()) {
                String name = cameraBrand.getBrandName();
                if(name.equals("Sony"))
                    cameraModel.connectionTypes = new ConnectionType[]{ConnectionType.IR, ConnectionType.Wire, ConnectionType.LANC};
                else if(name.equals("Canon")||name.equals("Nikon")||name.equals("Minolta"))
                    cameraModel.connectionTypes = new ConnectionType[]{ConnectionType.IR, ConnectionType.Wire};
                else if(name.equals("Pentax"))
                    cameraModel.connectionTypes = new ConnectionType[]{ConnectionType.IR};
                else 
                    cameraModel.connectionTypes = new ConnectionType[]{ConnectionType.Wire};
            }
        }
        writeObjectToFile(list, "cameraList.json");
    }
}
