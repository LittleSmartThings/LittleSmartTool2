/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import littlesmarttool2.GUI.components.BlockConfigPanel;
import littlesmarttool2.GUI.components.ChannelTabPanel;
import littlesmarttool2.GUI.components.CommandChangedListener;
import littlesmarttool2.comm.ConnectionListener;
import littlesmarttool2.comm.ResponseListener;
import littlesmarttool2.comm.SerialController;
import littlesmarttool2.model.CameraModel;
import littlesmarttool2.model.Channel;
import littlesmarttool2.model.Configuration;
import littlesmarttool2.model.Setting;
import littlesmarttool2.util.ConfigurationDumpReader;

/**
 *
 * @author marcher89
 */
public class Step3Panel extends StepPanel implements ResponseListener, ConnectionListener {

    //ChannelTabPanel[] tabs = new ChannelTabPanel[4];
    HashMap<Integer,ChannelTabPanel> tabs = new HashMap<>();
    private final CommandChangedListener cmdChangedListener = new CommandChangedListener() {
        @Override
        public void CommandChanged() {
            updateSpaceRemainingLabels();
        }
    };
    /**
     * Creates new form Step1Panel
     */
    public Step3Panel(SS2Wizard wizard) {
        super(wizard);
        initComponents();
        
    }
    
    @Override
    public void onDisplay() {
        loadButton.setVisible(false);
        jTabbedPane1.removeAll();
        
        if(wizard.getConfiguration().isTimelapse())
            showTimelapseTab();
        else
            showChannelTabs();
        
        wizard.getSerialController().addConnectionListener(this);
        updateCustomIRButton();
    }

    @Override
    public void onHide() {
        wizard.getSerialController().removeConnectionListener(this);
    }
    
    private void showTimelapseTab() {
        jTabbedPane1.addTab("Timelapse", new BlockConfigPanel(wizard.getConfiguration(), cmdChangedListener));
        
        remainingSpaceLabel.setVisible(false);
        thresholdsLabel.setVisible(false);
        thresholdsRemainingLabel.setVisible(false);
        blocksLabel.setVisible(false);
        blocksRemainingLabel.setVisible(false);
        commandsLabel.setVisible(false);
        commandsRemainingLabel.setVisible(false);
    }

    private void showChannelTabs() {
        
        ArrayList<Channel> channels = wizard.getConfiguration().getChannels();
        
        for (int i = 0; i < channels.size(); i++)
        {
            if (!channels.get(i).isCalibrated())
                continue;
            tabs.put(i, new ChannelTabPanel(wizard.getConfiguration(), cmdChangedListener));
            tabs.get(i).setChannel(channels.get(i));
            jTabbedPane1.addTab("Channel " + (i+1), tabs.get(i));
        }
        
        remainingSpaceLabel.setVisible(true);
        thresholdsLabel.setVisible(true);
        thresholdsRemainingLabel.setVisible(true);
        blocksLabel.setVisible(true);
        blocksRemainingLabel.setVisible(true);
        
        switch(wizard.getConfiguration().getOutputType()){
            case Wire:
                commandsLabel.setVisible(false);
                commandsRemainingLabel.setVisible(false);
                break;
            case IR:
                commandsLabel.setVisible(true);
                commandsRemainingLabel.setVisible(true);
                break;
            case LANC:
                commandsLabel.setVisible(true);
                commandsRemainingLabel.setVisible(true);
                break;
        }
        
        updateSpaceRemainingLabels();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        remainingSpaceLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        blocksLabel = new javax.swing.JLabel();
        blocksRemainingLabel = new javax.swing.JLabel();
        thresholdsLabel = new javax.swing.JLabel();
        thresholdsRemainingLabel = new javax.swing.JLabel();
        commandsLabel = new javax.swing.JLabel();
        commandsRemainingLabel = new javax.swing.JLabel();
        loadButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        connectLabel = new javax.swing.JLabel();
        customIRButton = new javax.swing.JButton();

        setName("Configure triggers and actions"); // NOI18N
        setLayout(new java.awt.BorderLayout());
        add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());

        remainingSpaceLabel.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        remainingSpaceLabel.setText("Remaining space on the StratoSnapper");
        jPanel2.add(remainingSpaceLabel, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.X_AXIS));

        blocksLabel.setText("Blocks:");
        blocksLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 5));
        jPanel1.add(blocksLabel);

        blocksRemainingLabel.setText("0");
        jPanel1.add(blocksRemainingLabel);

        thresholdsLabel.setText("Thresholds:");
        thresholdsLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 5));
        jPanel1.add(thresholdsLabel);

        thresholdsRemainingLabel.setText("0");
        jPanel1.add(thresholdsRemainingLabel);

        commandsLabel.setText("Commands:");
        commandsLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 10, 1, 5));
        jPanel1.add(commandsLabel);

        commandsRemainingLabel.setText("0");
        jPanel1.add(commandsRemainingLabel);

        jPanel2.add(jPanel1, java.awt.BorderLayout.CENTER);

        loadButton.setText("Load configuration from Stratosnapper");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });
        jPanel2.add(loadButton, java.awt.BorderLayout.EAST);

        add(jPanel2, java.awt.BorderLayout.SOUTH);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        connectLabel.setText("Please connect to the StratoSnapper");
        jPanel3.add(connectLabel);

        customIRButton.setText("Custom IR commands");
        customIRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                customIRButtonActionPerformed(evt);
            }
        });
        jPanel3.add(customIRButton);

        add(jPanel3, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        wizard.stopAutoServoPulling();
        try {
            ArrayList<CameraModel> possibleModels = ConfigurationDumpReader.LoadDumpInto(wizard.getConfiguration(), wizard.getSerialController().sendMultiResponse("D","D;1",20000));
            CameraModel chosenModel = null;
            if (possibleModels.isEmpty())
            {
                JOptionPane.showMessageDialog(wizard, "The setting stored on the StratoSnapper seems to be invalid and cannot be loaded.","Load error", JOptionPane.ERROR_MESSAGE);
                //Reset config:
                ArrayList<Channel> channels = wizard.getConfiguration().getChannels();
                for (Channel ch : channels)
                {
                    ch.setSetting(new Setting());
                }
            }
            else if (possibleModels.size() == 1)
            {
                chosenModel = possibleModels.get(0);
            }
            else
            {
                chosenModel = (CameraModel) JOptionPane.showInputDialog(wizard, "The loaded configuration fits multiple cameras.\r\nChoose which camera your are using from the list below.", "Choose camera", JOptionPane.INFORMATION_MESSAGE, null, possibleModels.toArray(), possibleModels.get(0));
            }
            if (chosenModel != null)
            {
                wizard.getConfiguration().setCameraModel(chosenModel);
                //TODO: Set camera brand!
            }
            
        } catch (IOException | TimeoutException ex) {
            Logger.getLogger(Step3Panel.class.getName()).log(Level.SEVERE, null, ex);
        }
        onDisplay();
        wizard.startAutoServoPulling();
    }//GEN-LAST:event_loadButtonActionPerformed

    private void customIRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_customIRButtonActionPerformed
        SerialController controller = wizard.getSerialController();
        IRManageDialog diag = new IRManageDialog(wizard, wizard);
        
        try {
            wizard.stopAutoServoPulling();
            String answer;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                
            }
            //answer = controller.send("O;1", 5000);
            answer = controller.send("O;1", 5000);
            if (!"O;1".equals(answer)) throw new IOException("Unexpected answer from StratoSnapper while setting output type. Answer: " + answer);
            answer = controller.send("N;0", 5000);
            if (!"N;1".equals(answer)) throw new IOException("Unexpected answer from StratoSnapper while enabling output. Answer: " + answer);
                    
            diag.setVisible(true);
            if (diag.hasCommError())
            {
                return; //Diag shows error message
            }
            
            answer = controller.send("N;1", 5000);
            if (!"N;1".equals(answer)) throw new IOException("Unexpected answer from StratoSnapper while disabling output. Answer: " + answer);
            
            wizard.startAutoServoPulling();
        } catch (IOException | TimeoutException ex) {
            diag.setVisible(false);
            wizard.connectionLost(ex.getMessage());
        }
        
        onDisplay();
    }//GEN-LAST:event_customIRButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blocksLabel;
    private javax.swing.JLabel blocksRemainingLabel;
    private javax.swing.JLabel commandsLabel;
    private javax.swing.JLabel commandsRemainingLabel;
    private javax.swing.JLabel connectLabel;
    private javax.swing.JButton customIRButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton loadButton;
    private javax.swing.JLabel remainingSpaceLabel;
    private javax.swing.JLabel thresholdsLabel;
    private javax.swing.JLabel thresholdsRemainingLabel;
    // End of variables declaration//GEN-END:variables


    @Override
    public void connectionStateChanged(boolean connected) {
        updateCustomIRButton();
    }

    private void updateCustomIRButton() {
        connectLabel.setVisible(!wizard.getSerialController().connected());
        customIRButton.setEnabled(wizard.getSerialController().connected());
    }
    
    @Override
    public void receiveResponse(char command, String[] args) {
        if (command != 'S') return;
        if (args.length < 4) return;
        try
        {
            for (Integer i : tabs.keySet())
            {
                tabs.get(i).updateChannelReading(Integer.parseInt(args[i]));
            }
        }
        catch (Exception e)
        {}
    }

    private void updateSpaceRemainingLabels() {
        Configuration conf = wizard.getConfiguration();
        if(!wizard.getSerialController().connected()){
            blocksRemainingLabel.setText("-");
            thresholdsRemainingLabel.setText("-");
            commandsRemainingLabel.setText("-");
            return;
        }
        
        
        blocksRemainingLabel.setText(conf.getRemainingRanges()+"/"+conf.getMaxRanges());
        thresholdsRemainingLabel.setText(conf.getRemainingTriggers()+"/"+conf.getMaxTriggers());
        
        switch(conf.getOutputType()){
            case IR:
                commandsRemainingLabel.setText(conf.getRemainingIR()+"/"+conf.getMaxIR());
                break;
            case LANC:
                commandsRemainingLabel.setText(conf.getRemainingLANC()+"/"+conf.getMaxLANC());
                break;
        }
    }
}
