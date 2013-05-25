/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI.components;

/**
 *
 * @author Rasmus
 */
public abstract class InputViewer extends javax.swing.JPanel {
    private int value = 50, lowerBound = 0, upperBound = 100;
    
    public void updateValue(int value)
    {
        this.value = value;
        repaint();
    }
    public void updateLowerBound(int lowerBound)
    {
        this.lowerBound = lowerBound;
        repaint();
    }
    public void updateUpperBound(int upperBound)
    {
        this.upperBound = upperBound;
        repaint();
    }
    public void updateBounds(int lowerBound, int upperBound)
    {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        repaint();
    }
    protected double getValuePct()
    {
        double span = upperBound - lowerBound;
        return (value - lowerBound) / span;
    }
}
