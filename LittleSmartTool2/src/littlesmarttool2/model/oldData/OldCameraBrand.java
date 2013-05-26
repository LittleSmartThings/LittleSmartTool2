/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model.oldData;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin Donovan
 */
class OldCameraBrand {

    private String brandName;
    private OldCameraProfile[] profiles;

    public List getModelList() {
        ArrayList<String> modelList = new ArrayList<String>(30);
        for (OldCameraProfile cameraProfile : profiles) {
            modelList.add(cameraProfile.getModels());
        }
        return modelList;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public OldCameraProfile[] getProfiles() {
        return profiles;
    }

    public void setProfiles(OldCameraProfile[] profiles) {
        this.profiles = profiles;
    }

    public String toString() {
        return getBrandName();
    }
}
