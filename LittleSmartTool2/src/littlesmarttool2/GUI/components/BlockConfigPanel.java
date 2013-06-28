/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI.components;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import littlesmarttool2.model.Block;
import littlesmarttool2.model.Command;
import littlesmarttool2.model.Configuration;
import littlesmarttool2.model.ConnectionType;
import littlesmarttool2.util.StringUtil;

/**
 *
 * @author Rasmus
 */
public class BlockConfigPanel extends javax.swing.JPanel {

    DefaultListModel listModel = new DefaultListModel();
    Block block;
    private final CommandChangedListener changeListener;
    private Configuration configuration;
    int[] timelapseDelayValues = new int[] {0,1,2,3,4,5,6,7,8,9,10,20,30,40,50,60,70,80,90,100,200,300,400,500,600,1200,1800,2400,3000,3600,4200,4800,5400,6000,9000,12000,15000,18000,21000,24000,27000,30000,33000,36000};
    
    /**
     * Use this when configuring a block/range
     */
    public BlockConfigPanel(Configuration config, Block block, CommandChangedListener changeListener) {
        initComponents();
        this.configuration = config;
        populate(config);
        this.block = block;
        this.changeListener = changeListener;
        jList1.setSelectedValue(block.getCommand(), true);
        jSlider1.setValue(block.getInterval()/100); //Stored as ms
    }
    
    /**
     * Use this when configuring timelapse 
     */
    public BlockConfigPanel(Configuration config, CommandChangedListener changeListener) {
        this.configuration = config;
        int timelapseDelay = configuration.getTimelapseDelay();
        initComponents();
        jLabel1.setText("Select an action to be performed using timelapse");
        jLabel3.setText("Select how often to repeat the action");
        populate(config);
        this.changeListener = changeListener;
        jList1.setSelectedValue(configuration.getTimelapseCommand(), true);
        jSlider1.setMaximum(timelapseDelayValues.length-1);
        jSlider1.setMinorTickSpacing(1);
        jSlider1.setValue(delayToSlider(timelapseDelay));
    }

    private int sliderToDelay(int sliderValue)
    {
        return timelapseDelayValues[sliderValue];
    }
    private int delayToSlider(int delayValue)
    {
        for (int i = 0; i < timelapseDelayValues.length; i++)
        {
            if (delayValue == timelapseDelayValues[i]) return i;
        }
        return 0;
    }
    
    private void populate(Configuration config)
    {
        listModel.removeAllElements();
        listModel.add(0,Command.getNothingCommand());
        int i = 1;
        for (Command c : config.getRelevantCommands())
        {
            listModel.add(i++, c);
        }
    }
    private ListModel getListModel() 
    {
        return listModel;
    }
    public Command getSelectedCommand()
    {
        return (Command)jList1.getSelectedValue();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jSlider1 = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        repetitionsLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 0, 0, 0));
        setLayout(new java.awt.BorderLayout());

        jLabel1.setText("Select an action to be performed while this block is active");
        add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jList1.setModel(getListModel());
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSlider1.setPaintTicks(true);
        jSlider1.setValue(0);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        jLabel3.setText("Select how often to repeat the action while the block is active");

        jLabel4.setText("Repetition: ");

        repetitionsLabel.setText("continously");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(repetitionsLabel))
                    .addComponent(jSlider1, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(repetitionsLabel))
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void jList1ValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jList1ValueChanged
        if (jList1.getSelectedValue() == null) return;
        
        if(block != null) //Configuring block
            block.setCommand((Command)jList1.getSelectedValue());
        else //Configuring timelapse
            configuration.setTimelapseCommand((Command)jList1.getSelectedValue());

        if (configuration.getRemainingRanges() < 0 ||
                (configuration.getOutputType() == ConnectionType.IR && configuration.getRemainingIR() < 0) ||
                (configuration.getOutputType() == ConnectionType.LANC && configuration.getRemainingLANC() < 0))
        {
            
            if (configuration.getRemainingRanges() < 0)
            {
                JOptionPane.showMessageDialog(this, "You have used all possible blocks.\r\nLook at the memory information in the bottom left.", "Not enough room", JOptionPane.WARNING_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(this, "There is no more room for "+(configuration.getOutputType() == ConnectionType.IR ? "IR":"LANC")+" commands.\r\nLook at the memory information in the bottom left.", "Not enough room", JOptionPane.WARNING_MESSAGE);
            }
            jList1.setSelectedIndex(0);
        }
        else
        {
            changeListener.CommandChanged();
        }
    }//GEN-LAST:event_jList1ValueChanged

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        
        if(block != null) //Configuring block
            block.setInterval(jSlider1.getValue()*100); 
        else //Configuring timelapse
            configuration.setTimelapseDelay(sliderToDelay(jSlider1.getValue()));
        
        if (configuration.isTimelapse())
        {
            repetitionsLabel.setText(StringUtil.TimelapseDelayToString(sliderToDelay(jSlider1.getValue())));
        }
        else
        {
            if (jSlider1.getValue() == 0)
                repetitionsLabel.setText("continously");
            else if (jSlider1.getValue() < 10)
                repetitionsLabel.setText("every " + jSlider1.getValue() + "00 ms");
            else
                repetitionsLabel.setText(String.format("every %.1f seconds", (jSlider1.getValue()/10.0)));
        }
    }//GEN-LAST:event_jSlider1StateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLabel repetitionsLabel;
    // End of variables declaration//GEN-END:variables
}
