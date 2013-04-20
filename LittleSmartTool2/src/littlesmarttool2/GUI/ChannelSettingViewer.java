/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import littlesmarttool2.model.Threshold;
import littlesmarttool2.model.Block;
/**
 *
 * @author Rasmus
 */
public class ChannelSettingViewer extends javax.swing.JPanel {
    
    private ArrayList<BlockPressedListener> blockPressedListeners = new ArrayList<>();
    private ArrayList<ThresholdPressedListener> thresholdPressedListeners = new ArrayList<>();
    
    private static BasicStroke readingStroke = new BasicStroke(2.0f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 10.0f, new float[]{5f}, 0.0f);
    
    private Color[] colors = new Color[] {
        new Color(0xFF, 0xA8, 0xA8),
        new Color(0xD0, 0xBC, 0xFE),
        new Color(0x4B, 0xFE, 0x78),
        new Color(0xB4, 0xD1, 0xB6),
        new Color(0xED, 0xEF, 0x85)
    };
    
    private int lowerBound = 0, upperBound = 100;
    private double readingValue = 0.5;
    private Block selectedBlock;
    private Threshold selectedThreshold;
    private ArrayList<Block> blocks = new ArrayList<>();
    private ArrayList<Threshold> thresholds = new ArrayList<>();
    
    
    /**
     * Creates new form ChannelSettingViewer
     */
    public ChannelSettingViewer() {
        initComponents();
    }

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
    
    public void setBounds(int lowerBound, int upperBound)
    {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
    
    private void drawReadingValue(Graphics2D g2)
    {
        Stroke defaultStroke = g2.getStroke();
        g2.setStroke(readingStroke);
        g2.setColor(Color.gray);
        g2.drawLine((int)(readingValue * getWidth()),0,(int)(readingValue * getWidth()),getHeight());
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
            double lower = (block.getLowerThreshold() == null) ? lowerBound : block.getLowerThreshold().getValue();
            double upper = (block.getUpperThreshold() == null) ? upperBound : block.getUpperThreshold().getValue();
            double width = ((upper - lower) / (upperBound-lowerBound)) * getWidth();
            System.out.println("Trying lower: " + lower);
            System.out.println("Trying upper: " + upper);
            System.out.println("Trying width: " + width);
            System.out.println("B: " + block.getInterval());
            g2.setColor(colors[i % colors.length]);
            g2.fillRect((int)prevWidth, 0, (int)width, getHeight());
            prevWidth += width;
        }
    }
    
    private void drawThresholds(Graphics2D g2)
    {
        for (int i = 0; i < thresholds.size(); i++)
        {
            Threshold threshold = thresholds.get(i);
            int x = (int)((threshold.getValue() * 1.0 / (upperBound-lowerBound)) * getWidth());
            g2.setColor(Color.black);
            g2.drawLine(x, 0, x, getHeight());
        }
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawReadingValue(g2);
        drawBlocks(g2);
        drawThresholds(g2);
        
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
