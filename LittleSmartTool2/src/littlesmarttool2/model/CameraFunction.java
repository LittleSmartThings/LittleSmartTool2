/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package littlesmarttool2.model;

/**
 *
 * @author Kevin Donovan
 */
public class CameraFunction {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String toString() {
        return getId();
    }
}
