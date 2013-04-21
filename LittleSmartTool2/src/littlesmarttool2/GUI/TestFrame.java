/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI;

import java.util.ArrayList;
import littlesmarttool2.comm.AutoServoPuller;
import littlesmarttool2.comm.ResponseListener;
import littlesmarttool2.comm.SerialController;
import littlesmarttool2.model.Block;
import littlesmarttool2.model.Threshold;

/**
 *
 * @author Rasmus
 */
public class TestFrame extends javax.swing.JFrame implements ResponseListener {

    @Override
    public void receiveResponse(char command, String[] args) {
        if (command != 'S') return;
        if (args.length < 4) return;
        int value = Integer.parseInt(args[3]);
        stickInputViewer2.setValue((value - 800) / (2200-800.0));
        stickInputViewer2.repaint();
        nWayInputViewer1.setN(10);
        nWayInputViewer1.setValue((int)(((value - 800) / (2200-800.0)) * 10) % 10);
        nWayInputViewer1.repaint();
    }
    
    
    /**
     * Creates new form TestFrame
     */
    public TestFrame() {
        initComponents();
        
        SerialController controller = new SerialController();
        
        controller.addResponseListener(this);
        try
        {
            controller.connect(SerialController.getPortNames().get(7));
            AutoServoPuller.Start(controller);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        //Set up test for channel setting viewer
        ArrayList<Block> blocks = new ArrayList<>();
        ArrayList<Threshold> thresholds = new ArrayList<>();
        
        Threshold th = new Threshold(20, null, null);
        Threshold th2 = new Threshold(30, null, null);
        thresholds.add(th);
        thresholds.add(th2);
        Block b = new Block(null, null, th, 0);
        Block b2 = new Block(null, th, th2, 2);
        Block b3 = new Block(null, th2, null, 2);
        blocks.add(b);
        blocks.add(b2);
        blocks.add(b3);
        
        channelSettingViewer1.setBounds(0,100);
        channelSettingViewer1.setBlockList(blocks);
        channelSettingViewer1.setThresholdlist(thresholds);
        
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pushbuttonInputViewer2 = new littlesmarttool2.GUI.components.PushbuttonInputViewer();
        stickInputViewer2 = new littlesmarttool2.GUI.components.StickInputViewer();
        nWayInputViewer1 = new littlesmarttool2.GUI.components.NWayInputViewer();
        channelSettingViewer1 = new littlesmarttool2.GUI.components.ChannelSettingViewer();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1000, 700));

        javax.swing.GroupLayout pushbuttonInputViewer2Layout = new javax.swing.GroupLayout(pushbuttonInputViewer2);
        pushbuttonInputViewer2.setLayout(pushbuttonInputViewer2Layout);
        pushbuttonInputViewer2Layout.setHorizontalGroup(
            pushbuttonInputViewer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );
        pushbuttonInputViewer2Layout.setVerticalGroup(
            pushbuttonInputViewer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout stickInputViewer2Layout = new javax.swing.GroupLayout(stickInputViewer2);
        stickInputViewer2.setLayout(stickInputViewer2Layout);
        stickInputViewer2Layout.setHorizontalGroup(
            stickInputViewer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 153, Short.MAX_VALUE)
        );
        stickInputViewer2Layout.setVerticalGroup(
            stickInputViewer2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 29, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout nWayInputViewer1Layout = new javax.swing.GroupLayout(nWayInputViewer1);
        nWayInputViewer1.setLayout(nWayInputViewer1Layout);
        nWayInputViewer1Layout.setHorizontalGroup(
            nWayInputViewer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        nWayInputViewer1Layout.setVerticalGroup(
            nWayInputViewer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout channelSettingViewer1Layout = new javax.swing.GroupLayout(channelSettingViewer1);
        channelSettingViewer1.setLayout(channelSettingViewer1Layout);
        channelSettingViewer1Layout.setHorizontalGroup(
            channelSettingViewer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 324, Short.MAX_VALUE)
        );
        channelSettingViewer1Layout.setVerticalGroup(
            channelSettingViewer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pushbuttonInputViewer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(stickInputViewer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nWayInputViewer1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(channelSettingViewer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 44, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stickInputViewer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nWayInputViewer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pushbuttonInputViewer2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(channelSettingViewer1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TestFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TestFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private littlesmarttool2.GUI.components.ChannelSettingViewer channelSettingViewer1;
    private littlesmarttool2.GUI.components.NWayInputViewer nWayInputViewer1;
    private littlesmarttool2.GUI.components.PushbuttonInputViewer pushbuttonInputViewer2;
    private littlesmarttool2.GUI.components.StickInputViewer stickInputViewer2;
    // End of variables declaration//GEN-END:variables


}
