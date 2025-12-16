/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package simperkas;

import java.awt.HeadlessException;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import kelas.User;


public class panelUser extends javax.swing.JPanel {

    /**
     * Creates new form panelUser
     */
    public panelUser() {
        initComponents();
    }

    void reset(){
        tUsername.setText("");
        tPassword.setText("");
        tNamalengkap.setText("");
        cStatus.setSelectedIndex(0);
        tUsername.setEditable(true);
    }
    void load_Table(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Username");
        model.addColumn("Nama Lengkap");
        model.addColumn("Status");
        
        try {
            User usr = new User();
            ResultSet result = usr.tampilUser();

            while (result.next()) {
                String status = (result.getInt("userStatus") == 1) ? "Active" : "Inactive";
                model.addRow(new Object[]{
                    result.getString("userName"),
                    result.getString("userFullName"),
                    status
                });
            }
            TableUser.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data : " + e.getMessage());
        }
        }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tNamalengkap = new javax.swing.JTextField();
        cStatus = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tUsername = new javax.swing.JTextField();
        tPassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableUser = new javax.swing.JTable();
        bhapus = new javax.swing.JButton();
        bTambah = new javax.swing.JButton();
        bReset = new javax.swing.JButton();
        bKembali = new javax.swing.JButton();
        bUbah = new javax.swing.JButton();
        Bguser = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tNamalengkap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tNamalengkapActionPerformed(evt);
            }
        });
        add(tNamalengkap, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, 340, 30));

        cStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(cStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 340, 30));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 153));
        jLabel1.setText("Nama Lengkap");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 180, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 51, 153));
        jLabel2.setText("Username ");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 120, 180, -1));

        tUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tUsernameActionPerformed(evt);
            }
        });
        add(tUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 340, 30));
        add(tPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 340, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 51, 153));
        jLabel3.setText("Status");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 180, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 51, 153));
        jLabel4.setText("Password");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, 180, -1));

        TableUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TableUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableUser);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 710, 250));

        bhapus.setBackground(new java.awt.Color(204, 0, 51));
        bhapus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bhapus.setForeground(new java.awt.Color(255, 255, 255));
        bhapus.setText("Hapus");
        bhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bhapusActionPerformed(evt);
            }
        });
        add(bhapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 250, 130, 30));

        bTambah.setBackground(new java.awt.Color(0, 51, 153));
        bTambah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bTambah.setForeground(new java.awt.Color(255, 255, 255));
        bTambah.setText("Tambah");
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });
        add(bTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 130, 30));

        bReset.setBackground(new java.awt.Color(0, 51, 153));
        bReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bReset.setForeground(new java.awt.Color(255, 255, 255));
        bReset.setText("Reset");
        bReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetActionPerformed(evt);
            }
        });
        add(bReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 250, 130, 30));

        bKembali.setBackground(new java.awt.Color(0, 51, 153));
        bKembali.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bKembali.setForeground(new java.awt.Color(255, 255, 255));
        bKembali.setText("Kembali");
        bKembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bKembaliActionPerformed(evt);
            }
        });
        add(bKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 250, 130, 30));

        bUbah.setBackground(new java.awt.Color(0, 51, 153));
        bUbah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bUbah.setForeground(new java.awt.Color(255, 255, 255));
        bUbah.setText("Ubah");
        bUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbahActionPerformed(evt);
            }
        });
        add(bUbah, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, 130, 30));

        Bguser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/Panel Transaksi.png"))); // NOI18N
        add(Bguser, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 820, 600));
    }// </editor-fold>//GEN-END:initComponents

    private void tNamalengkapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tNamalengkapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tNamalengkapActionPerformed

    private void tUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tUsernameActionPerformed

    private void bhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bhapusActionPerformed
        // TODO add your handling code here:
        String userName = tUsername.getText().trim();
        
        if (userName.isEmpty()){
            JOptionPane.showMessageDialog(null, "Pilih user yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Apakah Anda yakin ingin menghapus user \"" + userName + "\"?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {

                    
                    try {
                        User usr = new User();
                        usr.setUserName(userName);
                        usr.hapusUser();
                        JOptionPane.showMessageDialog(null, "User berhasi; dihapus!");
                        reset();
                        load_Table();
                    } catch (Exception e) {
                        JOptionPane.showConfirmDialog(null, "User gagal dihapus. Periksa koneksi database!");
                    }
                }
    }//GEN-LAST:event_bhapusActionPerformed

    private void bUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbahActionPerformed
        // TODO add your handling code here:
        String userName = tUsername.getText().trim();
        String password = tPassword.getText().trim();
        String fullName = tNamalengkap.getText().trim();
        Object selectedStatus = cStatus.getSelectedItem();
        
        if (userName.isEmpty() || password.isEmpty() || fullName.isEmpty()){
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
} 
           User usr = new User();
            usr.setUserName(userName);
            usr.setUserPassword(password);
            usr.setUserfullName(fullName);
            usr.setUserStatus("Active".equals(selectedStatus) ? 1 : 0);
            
            try {
                usr.ubahUser();
                JOptionPane.showMessageDialog(null, "Data berhasil diubah!");
                reset();
                load_Table();
            } catch (HeadlessException e) {
                JOptionPane.showMessageDialog(null, "Data gagal diubah. Periksa input atau koneksi database!");
            }
        
        
    }//GEN-LAST:event_bUbahActionPerformed

    private void bResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetActionPerformed
        // TODO add your handling code here:
        tUsername.setText(null);
        tPassword.setText(null);
        tNamalengkap.setText(null);
        cStatus.setSelectedItem(0);
    }//GEN-LAST:event_bResetActionPerformed

    private void bKembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bKembaliActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_bKembaliActionPerformed

    private void TableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableUserMouseClicked
        // TODO add your handling code here:
        int baris = TableUser.rowAtPoint(evt.getPoint());
        String userName = TableUser.getValueAt(baris, 0).toString();
        String fullName = TableUser.getValueAt(baris, 1).toString();
        String status = TableUser.getValueAt(baris, 2).toString();
        
        tUsername.setText(userName);
        tNamalengkap.setText(fullName);
        cStatus.setSelectedItem(status);
        tUsername.setEditable(false);
    }//GEN-LAST:event_TableUserMouseClicked

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
        // TODO add your handling code here:
        if (tUsername.getText().trim().isEmpty() ||
                tPassword.getText().trim().isEmpty() ||
                tNamalengkap.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            User usr = new User();
            usr.setUserName(tUsername.getText());
            usr.setUserPassword(tPassword.getText());
            usr.setUserfullName(tNamalengkap.getText());
            usr.setUserStatus(cStatus.getSelectedIndex() == 0 ? 1 : 0);
            
            usr.tambahUser();
            load_Table();
            reset();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data : " + e.getMessage());
        }
    }//GEN-LAST:event_bTambahActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Bguser;
    private javax.swing.JTable TableUser;
    private javax.swing.JButton bKembali;
    private javax.swing.JButton bReset;
    private javax.swing.JButton bTambah;
    private javax.swing.JButton bUbah;
    private javax.swing.JButton bhapus;
    private javax.swing.JComboBox<String> cStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tNamalengkap;
    private javax.swing.JPasswordField tPassword;
    private javax.swing.JTextField tUsername;
    // End of variables declaration//GEN-END:variables
}
