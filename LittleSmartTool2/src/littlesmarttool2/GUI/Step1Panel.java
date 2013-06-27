/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import littlesmarttool2.GUI.components.ConnectionTypeBox;
import littlesmarttool2.model.*;

/**
 *
 * @author marcher89
 */
public class Step1Panel extends StepPanel {
    private final ConnectionTypeBox[] connBoxes;
    private final ButtonGroup connGroup;

    /**
     * Creates new form Step1Panel
     */
    public Step1Panel(final SS2Wizard wizard) {
        super(wizard);
        initComponents();
        
        CameraBrand[] brands = CameraBrand.getArray();
        
        //Intialize camera brand list
        brandList.setHeadLine("Camera");
        brandList.getList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (brandList.getSelectedElement() == null) return;
                if (e.getValueIsAdjusting()) return;
                if (brandList.getSelectedElement() == wizard.getConfiguration().getCameraBrand()) return;
                if (wizard.getConfiguration().hasCommandsAssigned())
                {
                    int answer = JOptionPane.showConfirmDialog(wizard, "If you change camera after making a configuration, the selected commands will be removed.\r\nDo you still want to change camera?", "Clear configuration?", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        wizard.getConfiguration().removeAllCommands();
                    }
                    else
                    {
                        brandList.getList().setSelectedValue(wizard.getConfiguration().getCameraBrand(), true);
                        return;
                    }
                }
                wizard.getConfiguration().setCameraBrand((CameraBrand)brandList.getSelectedElement());
                connGroup.clearSelection();
                for (ConnectionTypeBox connBox : connBoxes) {
                    connBox.setEnabled(false);
                }
                modelList.setElements(((CameraBrand)brandList.getSelectedElement()).getModels());
            }
        });
        brandList.setElements(brands);
        
        //Intialize camera model list
        modelList.setHeadLine("Model");
        modelList.getList().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (modelList.getSelectedElement() == null) return;
                if (e.getValueIsAdjusting()) return;
                if (modelList.getSelectedElement() == wizard.getConfiguration().getCameraModel()) return;
                if (wizard.getConfiguration().hasCommandsAssigned())
                {
                    int answer = JOptionPane.showConfirmDialog(wizard, "If you change camera after making a configuration, the selected commands will be removed.\r\nDo you still want to change camera?", "Clear configuration?", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION)
                    {
                        wizard.getConfiguration().removeAllCommands();
                    }
                    else
                    {
                        modelList.getList().setSelectedValue(wizard.getConfiguration().getCameraModel(), true);
                        return;
                    }
                }
                CameraModel selected = (CameraModel)modelList.getSelectedElement();
                wizard.getConfiguration().setCameraModel(selected);
                if(selected==null) return;
                
                for (ConnectionTypeBox connBox : connBoxes) {
                    connBox.setEnabled(Arrays.asList(selected.getConnectionTypes()).contains(connBox.getConnectionType()));
                    if(selected.getConnectionTypes().length==1 && selected.getConnectionTypes()[0] == connBox.getConnectionType())
                        connBox.setSelected(true);
                }
            }
        });
        
        //Initialize connection type boxes
        connBoxes = new ConnectionTypeBox[ConnectionType.values().length];
        connGroup = new ButtonGroup();
        
        int i = 0;
        for (ConnectionType connectionType : ConnectionType.values()) {
            System.out.println(connectionType);
            connBoxes[i] = new ConnectionTypeBox(connectionType);
            connBoxes[i].setEnabled(false);
            connBoxes[i].addToGroup(connGroup);
            connBoxes[i].addItemListener(new ItemListener() {

                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.DESELECTED) return;
                    for (ConnectionTypeBox connectionTypeBox : connBoxes) {
                        if(connectionTypeBox.isSelected() && connectionTypeBox.getConnectionType() == wizard.getConfiguration().getOutputType()) return;
                    }
                    
                    if (wizard.getConfiguration().hasCommandsAssigned())
                    {
                        int answer = JOptionPane.showConfirmDialog(wizard, "If you change output connection type after making a configuration, the selected commands will be removed.\r\nDo you still want to change output connection type?", "Clear configuration?", JOptionPane.YES_NO_OPTION);
                        if (answer == JOptionPane.YES_OPTION)
                        {
                            wizard.getConfiguration().removeAllCommands();
                        }
                        else
                        {
                            for (ConnectionTypeBox b : connBoxes)
                            {
                                b.setSelected(b.getConnectionType() == wizard.getConfiguration().getOutputType());
                            }
                            return;
                        }
                    }
                    wizard.setNextEnabled(connGroup.getSelection() != null);
                    wizard.getConfiguration().setOutputType(null);
                    for (ConnectionTypeBox connectionTypeBox : connBoxes) {
                        if(connectionTypeBox.isSelected()) wizard.getConfiguration().setOutputType(connectionTypeBox.getConnectionType());
                    }
                }
            });
            
            outputconnectionsPanel.add(connBoxes[i++]);
        }
    }
        
    @Override
    public void onDisplay() {
        wizard.setNextEnabled(connGroup.getSelection() != null);
    }

    @Override
    public void onHide() {
        Configuration conf = wizard.getConfiguration();
        System.out.println("Camera Brand: "+conf.getCameraBrand());
        System.out.println("Camera Model: "+conf.getCameraModel());
        System.out.println("Output type: "+ conf.getOutputType());
        System.out.println("Number of commands found: "+conf.getRelevantCommands().size());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cameraPanel = new javax.swing.JPanel();
        brandList = new littlesmarttool2.GUI.components.ListWithHeadline();
        modelList = new littlesmarttool2.GUI.components.ListWithHeadline();
        outputPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        outputconnectionsPanel = new javax.swing.JPanel();

        setName("Choose camera and output type"); // NOI18N
        setLayout(new java.awt.GridLayout(2, 0));

        cameraPanel.setLayout(new java.awt.GridLayout(1, 2));
        cameraPanel.add(brandList);
        cameraPanel.add(modelList);

        add(cameraPanel);

        outputPanel.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(this.headlineFont);
        jLabel1.setText("Choose output connection type");
        jLabel1.setBorder(this.headlineBorder);
        outputPanel.add(jLabel1, java.awt.BorderLayout.NORTH);

        outputconnectionsPanel.setLayout(new java.awt.GridLayout(1, 0, 10, 0));
        outputPanel.add(outputconnectionsPanel, java.awt.BorderLayout.CENTER);

        add(outputPanel);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private littlesmarttool2.GUI.components.ListWithHeadline brandList;
    private javax.swing.JPanel cameraPanel;
    private javax.swing.JLabel jLabel1;
    private littlesmarttool2.GUI.components.ListWithHeadline modelList;
    private javax.swing.JPanel outputPanel;
    private javax.swing.JPanel outputconnectionsPanel;
    // End of variables declaration//GEN-END:variables

}
