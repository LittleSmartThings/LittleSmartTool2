/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package littlesmarttool2.GUI;

/**
 *
 * @author marcher89
 */
public class Step4Panel extends StepPanel {
    private SS2Wizard wizard;

    /**
     * Creates new form Step1Panel
     */
    public Step4Panel(SS2Wizard wizard) {
        super(wizard);
        initComponents();
    }
    
    @Override
    public void onDisplay() {
        wizard.setNextEnabled(false);
        //TODO: Do anything??
    }

    @Override
    public void onHide() {
        //TODO: Do anything??
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

        setName("Test and upload"); // NOI18N

        jLabel1.setText("Step 4");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(159, 159, 159)
                .add(jLabel1)
                .addContainerGap(202, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(141, 141, 141)
                .add(jLabel1)
                .addContainerGap(143, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}