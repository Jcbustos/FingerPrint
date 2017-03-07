
import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.UareUGlobal;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.xml.bind.DatatypeConverter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author juanko
 */
public class WindowInterface extends javax.swing.JFrame {

    public Reader reader;
    public Engine engine;
    public Reader.CaptureResult result;
    public Fmd fmd;
    public Operator operator;
    
    /**
     * Creates new form Interface
     * @param reader
     * @param operator
     */
    public WindowInterface(Reader reader, Operator operator) {
        this.reader = reader;
        this.operator = operator;
        this.setTitle("Operador "+operator.name+", rut: "+operator.rut);
        try {
            this.reader.Open(Reader.Priority.COOPERATIVE);
        } catch (UareUException ex) {
            Logger.getLogger(WindowInterface.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        initComponents();
        if(operator.finger == null){
            VerificarBtn.setEnabled(false);
        }else{
            EnrolarBtn.setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        EnrolarBtn = new javax.swing.JButton();
        VerificarBtn = new javax.swing.JButton();
        SalirBtn = new javax.swing.JButton();
        imagePanel = new ImagePanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        message = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        EnrolarBtn.setText("Enrolar");
        EnrolarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnrolarBtnActionPerformed(evt);
            }
        });

        VerificarBtn.setText("Verificar");
        VerificarBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VerificarBtnActionPerformed(evt);
            }
        });

        SalirBtn.setText("Salir");
        SalirBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalirBtnActionPerformed(evt);
            }
        });

        imagePanel.setBackground(new java.awt.Color(247, 245, 245));

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 231, Short.MAX_VALUE)
        );

        message.setEditable(false);
        message.setColumns(20);
        message.setRows(5);
        jScrollPane1.setViewportView(message);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(EnrolarBtn)
                        .addGap(139, 139, 139)
                        .addComponent(VerificarBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 193, Short.MAX_VALUE)
                        .addComponent(SalirBtn))
                    .addComponent(imagePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(imagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EnrolarBtn)
                    .addComponent(VerificarBtn)
                    .addComponent(SalirBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EnrolarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnrolarBtnActionPerformed
        EnrolarBtn.setEnabled(false);
        Enrollment enrollment = new Enrollment(reader,message,imagePanel, this);
        enrollment.start();
        EnrolarBtn.setEnabled(false);
        VerificarBtn.setEnabled(true);
    }//GEN-LAST:event_EnrolarBtnActionPerformed

    private void VerificarBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VerificarBtnActionPerformed
        VerificarBtn.setEnabled(false);
        Verification verification = new Verification(reader,message,imagePanel, this);
        if(verification.start()){
            System.exit(0);
        }
        //enrollFingerprint();
        VerificarBtn.setEnabled(true);
    }//GEN-LAST:event_VerificarBtnActionPerformed

    private void SalirBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalirBtnActionPerformed
        operator.closeConnection();
        System.exit(1);
    }//GEN-LAST:event_SalirBtnActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EnrolarBtn;
    private javax.swing.JButton SalirBtn;
    private javax.swing.JButton VerificarBtn;
    private ImagePanel imagePanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea message;
    // End of variables declaration//GEN-END:variables
}