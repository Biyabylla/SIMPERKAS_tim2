/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package simperkas;

import kelas.Perkakas;
import kelas.transaksiPerkakas;
import java.sql.*;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import kelas.koneksi;

/**
 *
 * @author Nabila
 */
public class panelTransaksi extends javax.swing.JPanel {

    DefaultTableModel modelTransaksi;

    /**
     * Creates new form panelTransaksi
     */
    public panelTransaksi() {
        initComponents();
        modelTransaksi = new DefaultTableModel(new Object[]{
            "ID", "Nama Santri", "Nomor Kamar", "Nama Barang", "Jumlah", "Waktu Pinjam", "Waktu Kembali"
        }, 0);

        tableTransaksi.setModel(modelTransaksi);

        tableTransaksi.getColumnModel().getColumn(0).setMinWidth(0);
        tableTransaksi.getColumnModel().getColumn(0).setMaxWidth(0);
        tableTransaksi.getColumnModel().getColumn(0).setWidth(0);

        tampilkanData();

    }

    private void loadData() {
        transaksiPerkakas t = new transaksiPerkakas();
        modelTransaksi.setRowCount(0);
        t.tampilkanData(modelTransaksi);
    }

    private void tampilkanData() {
        modelTransaksi.setRowCount(0);
        String sql
                = "SELECT t.id_peminjaman, s.nama_santri, s.nomor_kamar, "
                + "b.nama_barang, t.jumlah_pinjam, t.tanggal_pinjam, t.tanggal_kembali "
                + "FROM transaksi t "
                + "LEFT JOIN santri s ON t.id_santri = s.id_santri "
                + "LEFT JOIN barang b ON t.id_barang = b.id_barang "
                + "ORDER BY t.tanggal_pinjam DESC";

        try (Connection conn = koneksi.connect(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                modelTransaksi.addRow(new Object[]{
                    rs.getString("id_peminjaman"),
                    rs.getString("nama_santri"),
                    rs.getString("nomor_kamar"),
                    rs.getString("nama_barang"),
                    rs.getInt("jumlah_pinjam"),
                    rs.getString("tanggal_pinjam"),
                    rs.getString("tanggal_kembali")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan data: " + e.getMessage());
        }
    }

    private Perkakas ambilPerkakasByName(String namaBarang) {
        String sql = "SELECT id_barang, nama_barang, jumlah FROM barang WHERE LOWER(nama_barang)=LOWER(?)";
        try (Connection conn = koneksi.connect(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, namaBarang);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Perkakas(rs.getString("id_barang"), rs.getString("nama_barang"), rs.getInt("jumlah"));
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal ambil barang: " + e.getMessage());
        }
        return null;
    }

    private Integer getOrCreateSantriId(Connection conn, String namaSantri, int nomorKamar) throws SQLException {
        String q = "SELECT id_santri FROM santri WHERE nama_santri = ? AND nomor_kamar = ?";
        try (PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, namaSantri);
            ps.setInt(2, nomorKamar);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_santri");
                }
            }
        }

        String insert = "INSERT INTO santri (nama_santri, nomor_kamar) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, namaSantri);
            ps.setInt(2, nomorKamar);
            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet gk = ps.getGeneratedKeys()) {
                    if (gk.next()) {
                        return gk.getInt(1);
                    }
                }
            }
        }
        return null;
    }

    private boolean validasiPengembalian() {
        if (jdcTgl_Kembali.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Kolom Tanggal Pengembalian harus diisi terlebih dahulu!");
            jdcTgl_Kembali.requestFocus();
            return false; // tidak boleh lanjut
        }
        return true; // boleh lanjut
    }

    private void reset() {
        tNamaSantri.setText("");
        tNoKamar.setText("");
        tNamaBarang.setText("");
        tJumlah.setText("");
        jdcTgl_Pinjam.setDate(null);
        jdcTgl_Kembali.setDate(null);
        tableTransaksi.clearSelection();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        tNamaSantri = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tNoKamar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tNamaBarang = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tJumlah = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jdcTgl_Pinjam = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jdcTgl_Kembali = new com.toedter.calendar.JDateChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTransaksi = new javax.swing.JTable();
        bTambah = new javax.swing.JButton();
        bUbah = new javax.swing.JButton();
        bReset = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        bCetakPdf = new javax.swing.JButton();
        bResetData = new javax.swing.JButton();
        bRefresh = new javax.swing.JButton();
        bCari = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setLayout(null);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama Santri             :");
        add(jLabel2);
        jLabel2.setBounds(40, 110, 210, 20);
        add(tNamaSantri);
        tNamaSantri.setBounds(40, 130, 210, 22);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nomor Kamar          :");
        add(jLabel3);
        jLabel3.setBounds(40, 160, 140, 20);
        add(tNoKamar);
        tNoKamar.setBounds(40, 180, 210, 22);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nama Barang           :");
        add(jLabel4);
        jLabel4.setBounds(40, 210, 210, 20);
        add(tNamaBarang);
        tNamaBarang.setBounds(40, 230, 210, 22);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Jumlah                     :");
        add(jLabel5);
        jLabel5.setBounds(40, 260, 210, 20);
        add(tJumlah);
        tJumlah.setBounds(40, 280, 210, 22);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tanggal Pinjam       :");
        add(jLabel6);
        jLabel6.setBounds(40, 310, 210, 20);
        add(jdcTgl_Pinjam);
        jdcTgl_Pinjam.setBounds(40, 330, 210, 22);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Tanggal Kembli       :");
        add(jLabel7);
        jLabel7.setBounds(40, 360, 210, 20);
        add(jdcTgl_Kembali);
        jdcTgl_Kembali.setBounds(40, 380, 210, 22);

        tableTransaksi.setModel(new javax.swing.table.DefaultTableModel(
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
        tableTransaksi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableTransaksiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableTransaksi);

        add(jScrollPane1);
        jScrollPane1.setBounds(310, 100, 750, 380);

        bTambah.setBackground(new java.awt.Color(51, 255, 51));
        bTambah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bTambah.setText("Tambah");
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });
        add(bTambah);
        bTambah.setBounds(40, 430, 90, 30);

        bUbah.setBackground(new java.awt.Color(102, 153, 255));
        bUbah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bUbah.setText("Ubah");
        bUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbahActionPerformed(evt);
            }
        });
        add(bUbah);
        bUbah.setBounds(150, 430, 90, 30);

        bReset.setBackground(new java.awt.Color(255, 153, 51));
        bReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bReset.setText("Reset");
        bReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetActionPerformed(evt);
            }
        });
        add(bReset);
        bReset.setBounds(40, 490, 90, 30);

        bHapus.setBackground(new java.awt.Color(255, 51, 51));
        bHapus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bHapus.setText("Hapus");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });
        add(bHapus);
        bHapus.setBounds(150, 490, 90, 30);

        bCetakPdf.setBackground(new java.awt.Color(102, 102, 255));
        bCetakPdf.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bCetakPdf.setText("Cetak PDF");
        bCetakPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetakPdfActionPerformed(evt);
            }
        });
        add(bCetakPdf);
        bCetakPdf.setBounds(330, 510, 90, 30);

        bResetData.setBackground(new java.awt.Color(255, 153, 51));
        bResetData.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bResetData.setText("Reset Data");
        bResetData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetDataActionPerformed(evt);
            }
        });
        add(bResetData);
        bResetData.setBounds(430, 510, 100, 30);

        bRefresh.setBackground(new java.awt.Color(255, 204, 0));
        bRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bRefresh.setText("Refresh");
        bRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshActionPerformed(evt);
            }
        });
        add(bRefresh);
        bRefresh.setBounds(890, 510, 74, 30);

        bCari.setBackground(new java.awt.Color(204, 204, 0));
        bCari.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bCari.setText("Cari");
        bCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariActionPerformed(evt);
            }
        });
        add(bCari);
        bCari.setBounds(980, 510, 72, 30);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/simperkas/4.png"))); // NOI18N
        jLabel1.setText("jLabel1");
        add(jLabel1);
        jLabel1.setBounds(0, 0, 1090, 600);
    }// </editor-fold>//GEN-END:initComponents

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
        // TODO add your handling code here:
        String namaSantri = tNamaSantri.getText().trim();
        String namaBarang = tNamaBarang.getText().trim();
        String jumlahStr = tJumlah.getText().trim();
        String nomorKamarStr = tNoKamar.getText().trim();

        if (namaSantri.isEmpty() || namaBarang.isEmpty() || jumlahStr.isEmpty()
                || nomorKamarStr.isEmpty() || jdcTgl_Pinjam.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
            return;
        }

        int jumlahPinjam;
        try {
            jumlahPinjam = Integer.parseInt(jumlahStr);
            if (jumlahPinjam <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!");
            return;
        }

        int nomorKamar;
        try {
            nomorKamar = Integer.parseInt(nomorKamarStr);
            if (nomorKamar <= 0) {
                JOptionPane.showMessageDialog(this, "Nomor kamar harus lebih dari 0!");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Nomor kamar harus berupa angka!");
            return;
        }

        Perkakas barang = ambilPerkakasByName(namaBarang);
        if (barang == null) {
            JOptionPane.showMessageDialog(this, "Barang tidak ditemukan!");
            return;
        }

        if (jumlahPinjam > barang.getJumlah()) {
            JOptionPane.showMessageDialog(this, "Jumlah pinjam melebihi stok!");
            return;
        }

        transaksiPerkakas t = new transaksiPerkakas();
        t.setNamaSantri(namaSantri);
        t.setNomorKamar(nomorKamar);
        t.setNamaBarang(namaBarang);
        t.setJumlahPinjam(jumlahPinjam);
        t.setTanggalPinjam(jdcTgl_Pinjam.getDate());

        if (t.Tambah()) {
            JOptionPane.showMessageDialog(this, "Transaksi berhasil ditambahkan!");
            loadData();
            reset();
        } else {
            JOptionPane.showMessageDialog(this, "Transaksi gagal. Periksa stok atau data input.");
        }

    }//GEN-LAST:event_bTambahActionPerformed

    private void bUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbahActionPerformed
        // TODO add your handling code here:
        int row = tableTransaksi.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data transaksi!");
            return;
        }

        String idTransaksi = tableTransaksi.getValueAt(row, 0).toString();
        Date tglPinjam = jdcTgl_Pinjam.getDate();
        Date tglKembali = jdcTgl_Kembali.getDate();

        if (tglKembali == null) {
            JOptionPane.showMessageDialog(this, "Pilih tanggal pengembalian!");
            return;
        }

        if (tglKembali.before(tglPinjam)) {
            JOptionPane.showMessageDialog(this, "Tanggal pengembalian tidak boleh sebelum tanggal pinjam!");
            return;
        }

        transaksiPerkakas t = new transaksiPerkakas();
        t.setIdPeminjaman(idTransaksi);
        t.setTanggalKembali(tglKembali);

        if (t.Kembalikan()) {
            JOptionPane.showMessageDialog(this, "Barang berhasil dikembalikan!");
            loadData();
            reset();
        } else {
            JOptionPane.showMessageDialog(this, "Pengembalian gagal! Periksa tanggal atau data transaksi.");
        }

    }//GEN-LAST:event_bUbahActionPerformed

    private void bResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_bResetActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
        // TODO add your handling code here:
        int row = tableTransaksi.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data transaksi!");
            return;
        }

        String idTransaksi = tableTransaksi.getValueAt(row, 0).toString();
        transaksiPerkakas t = new transaksiPerkakas();
        t.setIdPeminjaman(idTransaksi);

        if (t.Hapus()) {
            JOptionPane.showMessageDialog(this, "Transaksi berhasil dihapus!");
            loadData();
            reset();
        } else {
        }

    }//GEN-LAST:event_bHapusActionPerformed

    private void bCetakPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetakPdfActionPerformed
        // TODO add your handling code here:
        transaksiPerkakas t = new transaksiPerkakas();
        t.cetakPDF(tableTransaksi);
    }//GEN-LAST:event_bCetakPdfActionPerformed

    private void bResetDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetDataActionPerformed
        // TODO add your handling code here:
        int konfirmasi = JOptionPane.showConfirmDialog(
                this,
                "Apakah Anda yakin ingin mereset semua data?\nHanya transaksi yang sudah dikembalikan akan dihapus.",
                "Konfirmasi Reset",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (konfirmasi != JOptionPane.YES_OPTION) {
            return;
        }

        transaksiPerkakas t = new transaksiPerkakas();
        int rowsDeleted = t.resetData();

        if (rowsDeleted == -1) {
            return;
        }

        String msg = rowsDeleted > 0
                ? "Berhasil mereset transaksi yang sudah dikembalikan."
                : "Tidak ada transaksi yang sudah dikembalikan untuk dihapus.";
        JOptionPane.showMessageDialog(this, msg);

        tampilkanData();

    }//GEN-LAST:event_bResetDataActionPerformed

    private void bRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshActionPerformed
        // TODO add your handling code here:
        tampilkanData();
    }//GEN-LAST:event_bRefreshActionPerformed

    private void bCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariActionPerformed
        // TODO add your handling code here:
        String keyword = JOptionPane.showInputDialog(this, "Masukkan Nama Santri / Nomor Kamar / Nama Barang:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            transaksiPerkakas t = new transaksiPerkakas();
            t.cariData(modelTransaksi, keyword.trim());
        } else {
            JOptionPane.showMessageDialog(this, "Masukkan kata kunci pencarian terlebih dahulu!");
        }
    }//GEN-LAST:event_bCariActionPerformed

    private void tableTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTransaksiMouseClicked
        // TODO add your handling code here:
        int row = tableTransaksi.getSelectedRow();
        if (row == -1) {
            return;
        }

        tNamaSantri.setText(String.valueOf(modelTransaksi.getValueAt(row, 1)));
        tNoKamar.setText(String.valueOf(modelTransaksi.getValueAt(row, 2)));
        tNamaBarang.setText(String.valueOf(modelTransaksi.getValueAt(row, 3)));
        tJumlah.setText(String.valueOf(modelTransaksi.getValueAt(row, 4)));

        try {
            String tglPinjam = String.valueOf(modelTransaksi.getValueAt(row, 5));
            jdcTgl_Pinjam.setDate(tglPinjam != null && !tglPinjam.equals("null") ? java.sql.Date.valueOf(tglPinjam) : null);
        } catch (Exception e) {
            jdcTgl_Pinjam.setDate(null);
        }

        try {
            Object tglK = modelTransaksi.getValueAt(row, 6);
            jdcTgl_Kembali.setDate(tglK != null && !String.valueOf(tglK).equals("null") ? java.sql.Date.valueOf(String.valueOf(tglK)) : null);
        } catch (Exception e) {
            jdcTgl_Kembali.setDate(null);
        }
    }//GEN-LAST:event_tableTransaksiMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCari;
    private javax.swing.JButton bCetakPdf;
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bRefresh;
    private javax.swing.JButton bReset;
    private javax.swing.JButton bResetData;
    private javax.swing.JButton bTambah;
    private javax.swing.JButton bUbah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcTgl_Kembali;
    private com.toedter.calendar.JDateChooser jdcTgl_Pinjam;
    private javax.swing.JTextField tJumlah;
    private javax.swing.JTextField tNamaBarang;
    private javax.swing.JTextField tNamaSantri;
    private javax.swing.JTextField tNoKamar;
    private javax.swing.JTable tableTransaksi;
    // End of variables declaration//GEN-END:variables
}
