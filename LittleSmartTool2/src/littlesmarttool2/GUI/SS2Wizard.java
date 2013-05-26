/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import littlesmarttool2.comm.AutoServoPuller;
import littlesmarttool2.comm.ResponseListener;
import littlesmarttool2.comm.SerialController;
import littlesmarttool2.model.Configuration;

/**
 *
 * @author marcher89
 */
public class SS2Wizard extends javax.swing.JFrame {

    private StepPanel[] stepPanels;
    private int currentStep = -1;
    private boolean hasUploaded = false;
    private Configuration configuration;
    private SerialController controller;
    private final String selectPortMsg = "Select port:";
    
    /**
     * Creates new form SS2Wizard
     */
    public SS2Wizard() {
        try {
            UIManager.setLookAndFeel(new MetalLookAndFeel());
            //UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            
        }
        try {
            Logger logger = Logger.getLogger(SS2Wizard.class.getName());
            FileHandler fh = new FileHandler("SSErrorLog.txt", true);
            logger.addHandler(fh);
            logger.setLevel(Level.ALL);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (Exception ex) {
            //DO nothing
        }
        
        
        configuration = new Configuration();
        controller = new SerialController();
        
        initComponents();
        
        //Initialize port chooser
        refreshPortList();
        
        //Initialize step panels
        stepPanels = new StepPanel[]{new Step1Panel(this), new Step2Panel(this), new Step3Panel(this), new Step4Panel(this)};
        
        for (StepPanel step : stepPanels) {
            cardPanel.add(step, step.getName());
        }
        controller.addResponseListener((ResponseListener)stepPanels[1]); //Page two need to be a listener
        controller.addResponseListener((ResponseListener)stepPanels[2]); //Page three need to be a listener
        controller.addResponseListener((ResponseListener)stepPanels[3]); //Page four need to be a listener
        goToStep(0);
    }
    
    public Configuration getConfiguration(){
        return configuration;
    }
    
    public SerialController getSerialController(){
        return controller;
    }
    
    public void setHasUploaded(boolean hasUploaded){
        this.hasUploaded = hasUploaded;
    }
    
    public boolean getHasUploaded(){
        return hasUploaded;
    }
    
    public void setBackEnabled(boolean val) { backButton.setEnabled(val);}
    
    public void setNextEnabled(boolean val) { nextButton.setEnabled(val);}
    
    private void goToStep(int index) {
        backButton.setEnabled((index <= 0) ? false : true);
        nextButton.setEnabled(true);
        
        nextButton.setText((index >= stepPanels.length-1) ? "Close" : "Next");
        if(currentStep > -1) stepPanels[currentStep].onHide();
        ((CardLayout)cardPanel.getLayout()).show(cardPanel, stepPanels[index].getName());
        stepPanels[index].onDisplay();
        
        headline.setText(stepPanels[index].getName());
        currentStep = index;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPanel = new javax.swing.JPanel();
        buttonPanel = new javax.swing.JPanel();
        backButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        cardPanel = new javax.swing.JPanel();
        upperPanel = new javax.swing.JPanel();
        headline = new javax.swing.JLabel();
        portPanel = new javax.swing.JPanel();
        portLabel = new javax.swing.JLabel();
        portChooser = new javax.swing.JComboBox();
        refreshPortListButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("StratoSnapper 2");
        setMinimumSize(new java.awt.Dimension(680, 570));
        setPreferredSize(new java.awt.Dimension(680, 570));

        contentPanel.setPreferredSize(new java.awt.Dimension(100, 700));
        contentPanel.setLayout(new java.awt.BorderLayout());

        buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 5, 10));
        buttonPanel.setLayout(new java.awt.BorderLayout());

        backButton.setText("Back");
        backButton.setActionCommand("");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(backButton, java.awt.BorderLayout.WEST);

        nextButton.setText("Next");
        nextButton.setActionCommand("");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(nextButton, java.awt.BorderLayout.EAST);

        contentPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);

        cardPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cardPanel.setLayout(new java.awt.CardLayout());
        contentPanel.add(cardPanel, java.awt.BorderLayout.CENTER);

        upperPanel.setLayout(new java.awt.BorderLayout());

        headline.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        headline.setText("StratoSnapper 2");
        headline.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        upperPanel.add(headline, java.awt.BorderLayout.CENTER);

        portPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 10));
        portPanel.setLayout(new javax.swing.BoxLayout(portPanel, javax.swing.BoxLayout.LINE_AXIS));

        portLabel.setText("Choose port:");
        portPanel.add(portLabel);

        portChooser.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 5, 10, 5));
        portChooser.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                portChooserItemStateChanged(evt);
            }
        });
        portPanel.add(portChooser);

        refreshPortListButton.setText("Refresh");
        refreshPortListButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshPortListButtonActionPerformed(evt);
            }
        });
        portPanel.add(refreshPortListButton);

        upperPanel.add(portPanel, java.awt.BorderLayout.EAST);

        contentPanel.add(upperPanel, java.awt.BorderLayout.NORTH);

        getContentPane().add(contentPanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        goToStep(currentStep-1);
    }//GEN-LAST:event_backButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        if(currentStep < stepPanels.length-1)
            goToStep(currentStep+1);
        else{
            if(hasUploaded || JOptionPane.showConfirmDialog(this, "Your configuration has not been uploaded to the StratoSnapper2.\nAre you sure, you want to quit?", "Really quit?", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.YES_OPTION)
            System.exit(0);
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void portChooserItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_portChooserItemStateChanged
        if (evt.getStateChange() == ItemEvent.DESELECTED)
        {
            controller.disconnect();
            System.out.println("tried disconnecting, connected : " + controller.connected());
            return;
        }
        if (evt.getItem() == selectPortMsg) 
        {
            return;
        }
        try {
            //Connect to the StratoSnapper and begin polling 
            System.out.println("Connecting: " + evt.getItem());
            controller.connect(evt.getItem().toString());
            System.out.println("Connected : " + controller.connected());
            AutoServoPuller.Start(controller);
        } catch (NoSuchPortException ex) {
            Logger.getLogger(SS2Wizard.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("An invalid port was selected ( " + ex.getMessage() + ")");
            JOptionPane.showMessageDialog(this, "The selected port was invalid.\r\nEnsure that you selected the right one or try to connect the Stratosnapper to another port","Invalid port", JOptionPane.ERROR_MESSAGE);
            refreshPortList();
        } catch (PortInUseException ex) {
            Logger.getLogger(SS2Wizard.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Selected port was in use ( " + ex.getMessage() + ")");
            JOptionPane.showMessageDialog(this, "The selected port was in use.\r\nEnsure that you selected the right port, and that no other software uses it.\r\nOn Mac computers, this can happen as a result of fault in the serial driver. To solve this, run the following commands:\r\nmkdir /var/lock\r\nchmod 777 /var/lock","Port in use", JOptionPane.ERROR_MESSAGE);
        } catch (UnsupportedCommOperationException | IOException ex) {
            Logger.getLogger(SS2Wizard.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Exception : " + ex.getClass().getName() + " ( " + ex.getMessage() + ")");
            JOptionPane.showMessageDialog(this, "An error occured while connecting to the Stratosnapper.\r\nPlease try again or use another port if the error persists\r\nMessage from system: \"" + ex.getMessage() + "\"","Connection error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_portChooserItemStateChanged

    private void refreshPortList()
    {
        ArrayList<String> portNames = SerialController.getPortNames();
        portChooser.removeAllItems();
        portNames.add(0, selectPortMsg);
        portChooser.setModel(new DefaultComboBoxModel(portNames.toArray()));
        System.out.println("Reloaded port list");
    }
    
    private void refreshPortListButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshPortListButtonActionPerformed
        refreshPortList();
    }//GEN-LAST:event_refreshPortListButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel cardPanel;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JLabel headline;
    private javax.swing.JButton nextButton;
    private javax.swing.JComboBox portChooser;
    private javax.swing.JLabel portLabel;
    private javax.swing.JPanel portPanel;
    private javax.swing.JButton refreshPortListButton;
    private javax.swing.JPanel upperPanel;
    // End of variables declaration//GEN-END:variables
}
