/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import littlesmarttool2.model.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author marcher89
 */
public class JSON {
    
    private static final String DATAFOLDER = "./data/";
    
    public static <T> T readObjectFromFile(String filename, Class<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // read from file, convert it to user class
            T value = mapper.readValue(new File(DATAFOLDER+filename), clazz);
            return value;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    
    /**
     * Reads all .json files from a directory.
     * @param <T> The type of object stored in each .json file in the directory
     * @param dirname The directory to read from.
     * @param clazz The class of the object stored in each .json file in the directory
     * @return A hashmap of all read objects as keys and the filename of the corresponding object (with the dirname: dirname"+"/"+filename).
     */
    public static <T> HashMap<T, String> readObjectsFromDir(String dirname, Class<T> clazz){
        HashMap<T, String> map = new HashMap<>();
        File dir = new File(DATAFOLDER+dirname);
        if (!dir.exists())
        {
            dir.mkdir();
        }
        for (File file : dir.listFiles()) {
            if(file.isFile()){
                if(file.getName().endsWith(".json"))
                    map.put(readObjectFromFile(dirname+"/"+file.getName(), clazz), dirname+"/"+file.getName());
            }
            else
                map.putAll(readObjectsFromDir(dirname+"/"+file.getName(), clazz));
        }
        return map;
    }

    public static boolean writeObjectToFile(Object object, String filename) {
	try {
                ObjectMapper mapper = new ObjectMapper();
 
		// convert user object to json string, and save to a file
		mapper.writeValue(new File(DATAFOLDER+filename), object);
 
                return true;
	} catch (IOException e) {
            return false;
	}
    } 
    
    public static boolean fileExists(String filename) {
        return new File(DATAFOLDER+filename).exists();
    }

    public static boolean deleteFile(String filename) {
        if(!fileExists(filename)) return false;
        return new File(DATAFOLDER+filename).delete();
    }
    
    public static void main(String[] args) {
        final String inputFile = null;//"LANC.txt";
        
        
        CameraBrand[] brandList = readObjectFromFile("cameraList.json", CameraBrand[].class);
        ArrayList<CameraModel> modelList = new ArrayList<>();
        for (CameraBrand cameraBrand : brandList) {
            modelList.addAll(Arrays.asList(cameraBrand.getModels()));
        }
        CameraModel[] models = new CameraModel[modelList.size()];
        int i = 0;
        for (CameraModel cameraModel : modelList) {
            models[i++] = cameraModel;
        }
        
        ArrayList<IRCommand> IRCommandList = new ArrayList<>();
        ArrayList<WireCommand> wireCommandList = new ArrayList<>();
        ArrayList<LANCCommand> LANCCommandList = new ArrayList<>();
        IRCommandList.addAll(Arrays.asList(readObjectFromFile("IRCommandList.json", IRCommand[].class)));
        wireCommandList.addAll(Arrays.asList(readObjectFromFile("WireCommandList.json", WireCommand[].class)));
        LANCCommandList.addAll(Arrays.asList(readObjectFromFile("LANCCommandList.json", LANCCommand[].class)));
        try{
            BufferedReader read = new BufferedReader(((inputFile==null)?new InputStreamReader(System.in):new FileReader(inputFile)));
            
            while(true){
                if(inputFile!=null){
                    String line = read.readLine();
                    String[] parts = line.split(";");
                    LANCCommandList.add(new LANCCommand(parts[1], parts[1], models, (byte)parts[0].charAt(0), (byte)parts[0].charAt(1)));
                    continue;
                }
                
                System.out.println("Choose command - 1: IR - 2: Wire - 3: LANC  --- 0: Exit and write to file");
                int commIndex = Integer.parseInt(read.readLine());
                if(commIndex==0)
                    break;
                System.out.println("Type name of command: ");
                String name = read.readLine().trim();
                System.out.println("Type description of command: ");
                String description = read.readLine().trim();
                switch(commIndex){
                    case 1:
                        IRCommandList.add(new IRCommand(name, description, models, new int[]{1,2,3,4,5,6,7,8,9,10}, 42, 2, 38000,true));
                        break;
                    case 2:
                        wireCommandList.add(new WireCommand(name, description, models, 74, 2));
                        break;
                    case 3:
                        LANCCommandList.add(new LANCCommand(name, description, models, (byte)'c', (byte)'r'));
                        break;
                }
                System.out.println("----------------------------------------------------------");
            }
        }
        catch (IOException ex) {
            Logger.getLogger(JSON.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            System.out.print("Writing "+IRCommandList.size()+" IR commands to file... ");
            Command[] commands = new Command[IRCommandList.size()];
            i = 0;
            for (Command command : IRCommandList) {
                commands[i++]=command;
            }
            boolean success = writeObjectToFile(commands, "IRCommandList.json");
            System.out.println(success?"Success!":"Failure!");
            
            System.out.print("Writing "+wireCommandList.size()+" wire commands to file... ");
            commands = new Command[wireCommandList.size()];
            i = 0;
            for (Command command : wireCommandList) {
                commands[i++]=command;
            }
            success = writeObjectToFile(commands, "WireCommandList.json");
            System.out.println(success?"Success!":"Failure!");
            
            System.out.print("Writing "+LANCCommandList.size()+" LANC commands to file... ");
            commands = new Command[LANCCommandList.size()];
            i = 0;
            for (Command command : LANCCommandList) {
                commands[i++]=command;
            }
            success = writeObjectToFile(commands, "LANCCommandList.json");
            System.out.println(success?"Success!":"Failure!");
        }
        System.exit(0);
        /*
        writeObjectToFile(list, "newCameraList.json");
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
        * */
    }
}
