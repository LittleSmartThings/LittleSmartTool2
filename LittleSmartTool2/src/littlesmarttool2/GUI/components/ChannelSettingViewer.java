/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI.components;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.MouseInputListener;
import littlesmarttool2.model.Threshold;
import littlesmarttool2.model.Block;
/**
 *
 * @author Rasmus
 */
public class ChannelSettingViewer extends javax.swing.JPanel implements MouseInputListener {
    
    private ArrayList<BlockPressedListener> blockPressedListeners = new ArrayList<>();
    private ArrayList<ThresholdPressedListener> thresholdPressedListeners = new ArrayList<>();
    
    private static BasicStroke readingStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 10.0f, new float[]{5f}, 0.0f);
    
    private static BasicStroke thresholdStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 10.0f);
    
    private static BasicStroke selectedThresholdStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 10.0f);
    
    private static Color selectedColor = new Color(200,200,255);
    
    private Color[] colors = new Color[] {
        new Color(0xFF, 0xFF, 0xFF)
    };
    
    //Most is in promille. Only the display of current reading is in values between lowerBound to upperBound
    private int value = 0, lowerBound = 0, upperBound = 1; //For displaying reading only!
    private Block selectedBlock;
    private Threshold selectedThreshold;
    private int dragMin, dragMax;
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Threshold> thresholds = new ArrayList<>();
    private final int thresholdSelectionWidth = 5;
    private final int thresholdMinimumSpacing = 15;
    
    /**
     * Creates new form ChannelSettingViewer
     */
    public ChannelSettingViewer() {
        initComponents();
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        try {
            Robot rbt = new Robot();
            int mousePromille = (int)(((e.getX()*1.0)/getWidth())*1000);
            if (selectedThreshold != null)
            {
                if (mousePromille < dragMin)
                {
                    rbt.mouseMove(e.getXOnScreen()+1, e.getYOnScreen());
                    return;
                }
                if(mousePromille > dragMax)
                {
                    rbt.mouseMove(e.getXOnScreen()-1, e.getYOnScreen());
                    return;
                }
                selectedThreshold.setValuePromille((int)(((e.getX()*1.0)/getWidth())*1000));
                repaint();
            }
        } catch (AWTException ex) {
            Logger.getLogger(ChannelSettingViewer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mousePromille = (int)(((e.getX()*1.0)/getWidth())*1000);
        boolean found = false;
        for (Threshold t : thresholds)
        {
            if (Math.abs(mousePromille - t.getValuePromille()) < thresholdSelectionWidth)
            {
                setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
                found = true;
            }
        }
        if (!found)
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mousePromille = (int)(((e.getX()*1.0)/getWidth())*1000);
        selectedBlock = null;
        selectedThreshold = null;
        dragMin = 0 + thresholdMinimumSpacing;
        dragMax = 1000 - thresholdMinimumSpacing;
        for (int i = 0; i < thresholds.size(); i++)
        {
            Threshold t = thresholds.get(i);
            if (Math.abs(mousePromille - t.getValuePromille()) < thresholdSelectionWidth)
            {
                selectedThreshold = t;
                if (i < thresholds.size()-1)
                    dragMax = thresholds.get(i+1).getValuePromille() - thresholdMinimumSpacing;
                thresholdPressed(t);
                repaint();
                return;
            }
            dragMin = t.getValuePromille() + thresholdMinimumSpacing;
        }
        for (Block b : blocks)
        {
            int lowerP = (b.getLowerThreshold() == null) ? 0 : b.getLowerThreshold().getValuePromille();
            int upperP = (b.getUpperThreshold()== null) ? 1000 : b.getUpperThreshold().getValuePromille();
            if (mousePromille >= lowerP && mousePromille < upperP)
            {
                selectedBlock = b;
                blockPressed(b);
                repaint();
                return;
            }
        }
    }
    
    // <editor-fold desc="Unimplemented mouse listener methods"> 
    @Override
    public void mouseReleased(MouseEvent e) {
        //
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //
    }
    // </editor-fold>
    
    public static interface BlockPressedListener
    {
        void blockPressed(Block block);
    }
    public void addBlockPressedListener(BlockPressedListener listener)
    {
        blockPressedListeners.add(listener);
    }
    public static interface ThresholdPressedListener
    {
        void thresholdPressed(Threshold threshold);
    }
    public void addThresholdPressedListener(ThresholdPressedListener listener)
    {
        thresholdPressedListeners.add(listener);
    }
    private void blockPressed(Block block)
    {
        for (BlockPressedListener bpl : blockPressedListeners)
        {
            bpl.blockPressed(block);
        }
    }
    private void thresholdPressed(Threshold threshold)
    {
        for (ThresholdPressedListener tpl : thresholdPressedListeners)
        {
            tpl.thresholdPressed(threshold);
        }
    }
    
    public void setBlockList(ArrayList<Block> blocks)
    {
        this.blocks = blocks;
    }
    
    public void setThresholdlist(ArrayList<Threshold> thresholds)
    {
        this.thresholds = thresholds;
    }
     
    public void updateBounds(int lowerBound, int upperBound)
    {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        repaint();
    }
    public void updateValue(int value)
    {
        this.value = value;
        repaint();
    }
    private double getValuePct()
    {
        double span = upperBound - lowerBound;
        return (value - lowerBound) / span;
    }
    
    private void drawReadingValue(Graphics2D g2)
    {
        Stroke defaultStroke = g2.getStroke();
        g2.setStroke(readingStroke);
        g2.setColor(Color.gray);
        int x = (int)(getValuePct() * getWidth());
        g2.drawLine(x,0,x,getHeight());
        g2.setStroke(defaultStroke);
    }
    
    private void drawBorder(Graphics2D g2)
    {
        g2.setColor(Color.black);
        g2.drawRect(0, 0, getWidth()-1, getHeight()-1);
    }
    
    private void drawBlocks(Graphics2D g2)
    {
        double prevWidth = 0;
        for (int i = 0; i < blocks.size(); i++)
        {
            Block block = blocks.get(i);
            double lower = (block.getLowerThreshold() == null) ? 0 : block.getLowerThreshold().getValuePromille();
            double upper = (block.getUpperThreshold() == null) ? 1000 : block.getUpperThreshold().getValuePromille();
            double width = ((upper - lower) / 1000) * getWidth();
            g2.setColor(colors[i % colors.length]);
            if (block == selectedBlock)
            {
                g2.setColor(selectedColor);
            }
            g2.fillRect((int)prevWidth, 0, (int)width, getHeight());
            prevWidth += width;
        }
    }
    
    private void drawThresholds(Graphics2D g2)
    {
        Stroke defaultStroke = g2.getStroke();
        for (int i = 0; i < thresholds.size(); i++)
        {
            Threshold threshold = thresholds.get(i);
            int x = (int)((threshold.getValuePromille() * 1.0 / (1000)) * getWidth());
            if (threshold == selectedThreshold)
            {
                g2.setStroke(selectedThresholdStroke);
                g2.setColor(Color.blue);
            }
            else
            {
                g2.setStroke(thresholdStroke);
                g2.setColor(Color.black);
            }
            g2.drawLine(x, 0, x, getHeight());
        }
        g2.setStroke(defaultStroke);
    }
    
    
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawBlocks(g2);
        drawThresholds(g2);
        drawReadingValue(g2);
        drawBorder(g2);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 690, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 75, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
