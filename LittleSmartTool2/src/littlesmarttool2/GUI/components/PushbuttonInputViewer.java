/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author Rasmus
 */
public class PushbuttonInputViewer extends InputViewer {
    
    private static BasicStroke readingStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 10.0f);
    private final int diamReduction = 5;
    /**
     * Creates new form PushbuttonInputViewer
     */
    public PushbuttonInputViewer() {
        initComponents();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(readingStroke);
        g2.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(240,240,240));
        g2.fillRect(0, 0, getWidth(), getHeight());
        int dim = Math.min(getWidth()-1,getHeight()-1);
        //Pressed
        if (getValuePct() >= .5)
        {
            g2.setColor(new Color(200,200,255));
            g2.fillOval((getWidth()/2) - (dim/2) + diamReduction, (getHeight()/2) - (dim/2) + diamReduction, (dim-2)-diamReduction*2, (dim-2)-diamReduction*2);
        }
        //Border & lines
        g2.setColor(Color.black);
        g2.drawOval((getWidth()/2) - (dim/2) + diamReduction, (getHeight()/2) - (dim/2) + diamReduction, (dim-2)-diamReduction*2, (dim-2)-diamReduction*2);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 74, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
