/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.util;

/**
 *
 * @author Rasmus
 */
public class StringUtil {
    public static String TimelapseDelayToString(int timelapseDelay)
    {
        if (timelapseDelay == 0)
            return "continously";
        else if (timelapseDelay < 600)
            return (String.format("every %.1f second(s)", timelapseDelay/10.0));
        else if (timelapseDelay < 36000)
            return (String.format("every %.0f minute(s)", timelapseDelay/600.0));
        else
            return (String.format("every %.0f hour(s)", timelapseDelay/36000.0));
    }
}
