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
            return null;
        }

    }

    public static boolean writeObjectToFile(Object object, String filename) {
	try {
                ObjectMapper mapper = new ObjectMapper();
 
		// convert user object to json string, and save to a file
		mapper.writeValue(new File("data/"+filename), object);
 
                return true;
	} catch (IOException e) {
            return false;
	}
    } 
}
