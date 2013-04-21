/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package littlesmarttool2.model;

/**
 *
 * @author marcher89
 */
public class CameraModel {
    private String name;
    
    public CameraModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return getName();
    }

}
