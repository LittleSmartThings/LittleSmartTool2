/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import littlesmarttool2.model.Block;
import littlesmarttool2.model.Channel;
import littlesmarttool2.model.Command;
import static littlesmarttool2.model.ControlType.*;
import littlesmarttool2.model.Threshold;

/**
 *
 * @author Rasmus
 */
public class ChannelTester extends javax.swing.JPanel implements ActionListener {

    private Timer flashTimer, intervalTimer;
    private InputViewer viewer;
    private Channel channel;
    private Block currentBlock;
    private int lowerBound;
    private int upperBound;
    /**
     * Creates new form ChannelTester
     */
    public ChannelTester() {
        initComponents();
    }
    
    public void setChannel(Channel channel)
    {
        this.channel = channel;
        idLabel.setText("Ch. " + channel.getId());
        switch(channel.getControlType())
        {
            case PushButton:
                viewer = new PushbuttonInputViewer();
                break;
            case Stick:
                viewer = new StickInputViewer();
                break;
            case Switch2:
                viewer = new NWayInputViewer(2);
                break;
            case Switch3:
                viewer = new NWayInputViewer(3);
                break;
        }
        viewerPanel.add(viewer, BorderLayout.CENTER);
        updateBounds(channel.getCalibLow(), channel.getCalibHigh());
        intervalTimer = new Timer(1500, this);
        intervalTimer.start();
    }
    
    public void updateBounds(int lowerBound, int upperBound)
    {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        viewer.updateBounds(lowerBound, upperBound);
    }
    private Block getBlockFromValue(int value)
    {
        ArrayList<Block> blocks = channel.getSetting().getBlocks();
        int promille = ((value - lowerBound) * 1000) / (upperBound - lowerBound);
        promille = Math.min(999, Math.max(0,promille)); //Stay inside bounds
        for (Block b : blocks)
        {
            int blockLower = (b.getLowerThreshold() == null) ? 0 : b.getLowerThreshold().getValuePromille();
            int blockUpper = (b.getUpperThreshold() == null) ? 1000 : b.getUpperThreshold().getValuePromille();
            if (promille >= blockLower && promille < blockUpper)
                return b;
        }
        return null;
    }
    public void updateValue(int value)
    {
        viewer.updateValue(value);
        //Block
        Block newBlock = getBlockFromValue(value);
        if (currentBlock == newBlock) return; //If its the same block nothing has changed
        intervalTimer.stop();
        if (newBlock.getInterval() != 0){
            intervalTimer.setDelay(newBlock.getInterval());
            intervalTimer.setInitialDelay(newBlock.getInterval());
            intervalTimer.start();
        }
        else
        {
            intervalTimer.setDelay(1);
            intervalTimer.setInitialDelay(1);
            intervalTimer.start();
        }
        if (currentBlock == null) //If not leaving a block, no threshold can be triggered
        {
            currentBlock = newBlock;
            return;
        }
        //Threshold
        //TODO: Hysteresis
        Threshold threshold = null;
        boolean goingUp = false;
        if (currentBlock.getLowerThreshold() == newBlock.getUpperThreshold())
        {
            threshold = currentBlock.getLowerThreshold();
            goingUp = false;
        }
        if (currentBlock.getUpperThreshold() == newBlock.getLowerThreshold() && threshold == null)
        {
            threshold = currentBlock.getUpperThreshold();
            goingUp = true;
        }
        currentBlock = newBlock;
        if (threshold == null) return; //No threshold passed
        Command cmd = (goingUp) ? threshold.getUpCommand() : threshold.getDownCommand();
        if (cmd == Command.getNothingCommand()) return; //Don't show nothing
        flashText(cmd.getName());
    }
    private void flashText(String text)
    {
        if (flashTimer != null) flashTimer.stop();
        //outputLabel.setText(text);
        outputArea.setText(text);
        //outputLabel.setForeground(new Color(0,0,0,254));
        outputArea.setForeground(new Color(0,0,0,254));
        flashTimer = new Timer(10,null);
        flashTimer.addActionListener(new ActionListener() {
            int i = 254;
            @Override
            public void actionPerformed(ActionEvent e) {
                //outputLabel.setForeground(new Color(0,0,0,i));
                outputArea.setForeground(new Color(0,0,0,i));
                i -= 5;
                if (i <0){
                    //outputLabel.setText("");
                    outputArea.setText("");
                    flashTimer.stop();
                }
            }
        });
        flashTimer.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        idPanel = new javax.swing.JPanel();
        idLabel = new javax.swing.JLabel();
        viewerPanel = new javax.swing.JPanel();
        outputPanel = new javax.swing.JPanel();
        outputArea = new javax.swing.JTextArea();

        setMinimumSize(new java.awt.Dimension(114, 60));
        setLayout(new java.awt.BorderLayout());

        idPanel.setLayout(new javax.swing.BoxLayout(idPanel, javax.swing.BoxLayout.LINE_AXIS));

        idLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        idLabel.setText("Ch. 1");
        idPanel.add(idLabel);

        add(idPanel, java.awt.BorderLayout.LINE_START);

        viewerPanel.setLayout(new java.awt.BorderLayout());
        add(viewerPanel, java.awt.BorderLayout.CENTER);

        outputPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        outputPanel.setMaximumSize(new java.awt.Dimension(200, 14));
        outputPanel.setMinimumSize(new java.awt.Dimension(100, 14));
        outputPanel.setPreferredSize(new java.awt.Dimension(150, 14));
        outputPanel.setLayout(new javax.swing.BoxLayout(outputPanel, javax.swing.BoxLayout.LINE_AXIS));

        outputArea.setEditable(false);
        outputArea.setBackground(new java.awt.Color(240, 240, 240));
        outputArea.setColumns(20);
        outputArea.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        outputArea.setLineWrap(true);
        outputArea.setRows(5);
        outputArea.setWrapStyleWord(true);
        outputPanel.add(outputArea);

        add(outputPanel, java.awt.BorderLayout.LINE_END);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel idLabel;
    private javax.swing.JPanel idPanel;
    private javax.swing.JTextArea outputArea;
    private javax.swing.JPanel outputPanel;
    private javax.swing.JPanel viewerPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * Interval timer tick
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (currentBlock == null) {
            intervalTimer.stop();
            return;
        }
        if (currentBlock.getCommand() != Command.getNothingCommand())
            flashText(currentBlock.getCommand().getName());
    }
}
