/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.model;

/**
 *
 * @author Rasmus
 */
public abstract class Command {
    private String name;
    private String description;
    
    public Command(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
}
