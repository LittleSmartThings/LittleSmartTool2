/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import littlesmarttool2.model.Command;

/**
 *
 * @author Rasmus
 */
public class TimelapseTester extends javax.swing.JPanel {

    private Timer flashTimer;
    /**
     * 
     * @param command
     * @param interval In 1/10ths of seconds
     */
    public TimelapseTester(final Command command, int interval) {
        initComponents();
        new Timer(interval*100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flashText(command.getName());
            }
        }).start();
    }
    
    private void flashText(String text)
    {
        if (flashTimer != null) flashTimer.stop();
        outputArea.setText(text);
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

        jLabel1 = new javax.swing.JLabel();
        outputPanel = new javax.swing.JPanel();
        outputArea = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Timelapse: ");
        add(jLabel1, java.awt.BorderLayout.LINE_START);

        outputPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 5, 0, 0));
        outputPanel.setMinimumSize(new java.awt.Dimension(100, 20));
        outputPanel.setPreferredSize(new java.awt.Dimension(150, 300));
        outputPanel.setLayout(new javax.swing.BoxLayout(outputPanel, javax.swing.BoxLayout.LINE_AXIS));

        outputArea.setEditable(false);
        outputArea.setBorder(null);
        outputPanel.add(outputArea);

        add(outputPanel, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField outputArea;
    private javax.swing.JPanel outputPanel;
    // End of variables declaration//GEN-END:variables
}
