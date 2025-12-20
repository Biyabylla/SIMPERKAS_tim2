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
public class panelTransaksii extends javax.swing.JPanel {

    DefaultTableModel modelTransaksi;//Menyimpan struktur tabel sebelum ditampilkan ke JTable

    /**
     * Creates new form panelTransaksi
     */
    public panelTransaksii() {
        initComponents();
        
        tJumlah.addActionListener(e -> bTambah.doClick());
        tNoKamar.addActionListener(e -> bTambah.doClick());
        
        modelTransaksi = new DefaultTableModel(new Object[]{//Membuat struktur kolom tabel
            "ID", "Nama Santri", "Nomor Kamar", "Nama Barang", "Jumlah", "Waktu Pinjam", "Waktu Kembali"
        }, 0);

        tableTransaksi.setModel(modelTransaksi);//Menghubungkan model ke JTable

        //Menyembunyikan kolom ID
        tableTransaksi.getColumnModel().getColumn(0).setMinWidth(0);
        tableTransaksi.getColumnModel().getColumn(0).setMaxWidth(0);
        tableTransaksi.getColumnModel().getColumn(0).setWidth(0);

        tampilkanData();//memanggil method tampilkan data

    }

    private void loadData() {
        transaksiPerkakas t = new transaksiPerkakas();//Membuat object transaksiPerkakas
        modelTransaksi.setRowCount(0);//kosongkan semua baris yang ada
        t.tampilkanData(modelTransaksi);//Panggil method untuk memasukkan data ke tabel
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

        try (Connection conn = koneksi.connect(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {//menjalankan query

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
            ps.setString(1, namaBarang);//isi parameter
            try (ResultSet rs = ps.executeQuery()) {//hasil query
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
        String q = "SELECT id_santri FROM santri WHERE nama_santri = ? AND nomor_kamar = ?";//SQL untuk mencari santri berdasarkan nama + nomor kamar
        try (PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setString(1, namaSantri);
            ps.setInt(2, nomorKamar);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_santri");
                }
            }
        }

        String insert = "INSERT INTO santri (nama_santri, nomor_kamar) VALUES (?, ?)";//query umtuk menambah data santri
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
        return null;//kembalikkan null jika barang tdk ditemukan
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
        //Mengosongkan semua textfield
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

        lNamaSantri = new javax.swing.JLabel();
        tNoKamar = new javax.swing.JTextField();
        lNomorKamar = new javax.swing.JLabel();
        tNamaSantri = new javax.swing.JTextField();
        tJumlah = new javax.swing.JTextField();
        lJumlah = new javax.swing.JLabel();
        tNamaBarang = new javax.swing.JTextField();
        lNamaBarang = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableTransaksi = new javax.swing.JTable();
        jdcTgl_Pinjam = new com.toedter.calendar.JDateChooser();
        jdcTgl_Kembali = new com.toedter.calendar.JDateChooser();
        lWaktuPinjam = new javax.swing.JLabel();
        lWaktuKembali = new javax.swing.JLabel();
        bTambah = new javax.swing.JButton();
        bUbah = new javax.swing.JButton();
        bHapus = new javax.swing.JButton();
        bReset = new javax.swing.JButton();
        bCetakPDF = new javax.swing.JButton();
        bResetData = new javax.swing.JButton();
        bCari = new javax.swing.JButton();
        bRefresh = new javax.swing.JButton();
        lTransaksi = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lNamaSantri.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lNamaSantri.setText("Nama Lengkap Santri   :");
        add(lNamaSantri, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, -1, -1));

        tNoKamar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        add(tNoKamar, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 120, 90, -1));

        lNomorKamar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lNomorKamar.setText("Nomor Kamar   :");
        add(lNomorKamar, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 120, -1, -1));

        tNamaSantri.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        add(tNamaSantri, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 340, -1));

        tJumlah.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        add(tJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 160, 90, -1));

        lJumlah.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lJumlah.setText("Jumlah Pinjam  :");
        add(lJumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 160, -1, -1));

        tNamaBarang.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        add(tNamaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 160, 340, -1));

        lNamaBarang.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lNamaBarang.setText("Nama Barang                :");
        add(lNamaBarang, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, -1, -1));

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

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 740, 250));
        add(jdcTgl_Pinjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 198, 180, -1));
        add(jdcTgl_Kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 200, 180, -1));

        lWaktuPinjam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lWaktuPinjam.setText("Waktu Peminjaman      :");
        add(lWaktuPinjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, -1, -1));

        lWaktuKembali.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lWaktuKembali.setText("Waktu Pengembalian    :");
        add(lWaktuKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 200, -1, -1));

        bTambah.setBackground(new java.awt.Color(0, 51, 153));
        bTambah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bTambah.setForeground(new java.awt.Color(255, 255, 255));
        bTambah.setText("Tambah");
        bTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambahActionPerformed(evt);
            }
        });
        add(bTambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 160, 30));

        bUbah.setBackground(new java.awt.Color(0, 51, 153));
        bUbah.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bUbah.setForeground(new java.awt.Color(255, 255, 255));
        bUbah.setText("Ubah");
        bUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbahActionPerformed(evt);
            }
        });
        add(bUbah, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 230, 180, 30));

        bHapus.setBackground(new java.awt.Color(204, 0, 51));
        bHapus.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bHapus.setForeground(new java.awt.Color(255, 255, 255));
        bHapus.setText("Hapus");
        bHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapusActionPerformed(evt);
            }
        });
        add(bHapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 230, 190, 30));

        bReset.setBackground(new java.awt.Color(0, 51, 153));
        bReset.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bReset.setForeground(new java.awt.Color(255, 255, 255));
        bReset.setText("Reset");
        bReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetActionPerformed(evt);
            }
        });
        add(bReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 230, 180, 30));

        bCetakPDF.setBackground(new java.awt.Color(0, 51, 153));
        bCetakPDF.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bCetakPDF.setForeground(new java.awt.Color(255, 255, 255));
        bCetakPDF.setText("Cetak PDF");
        bCetakPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetakPDFActionPerformed(evt);
            }
        });
        add(bCetakPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 530, 110, 30));

        bResetData.setBackground(new java.awt.Color(204, 0, 51));
        bResetData.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bResetData.setForeground(new java.awt.Color(255, 255, 255));
        bResetData.setText("Reset Data");
        bResetData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bResetDataActionPerformed(evt);
            }
        });
        add(bResetData, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 530, 110, 30));

        bCari.setBackground(new java.awt.Color(0, 51, 153));
        bCari.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bCari.setForeground(new java.awt.Color(255, 255, 255));
        bCari.setText("Cari");
        bCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCariActionPerformed(evt);
            }
        });
        add(bCari, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 530, 110, 30));

        bRefresh.setBackground(new java.awt.Color(0, 51, 153));
        bRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        bRefresh.setForeground(new java.awt.Color(255, 255, 255));
        bRefresh.setText("Refresh");
        bRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bRefreshActionPerformed(evt);
            }
        });
        add(bRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 530, 110, 30));

        lTransaksi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/Panel transaksi2.png"))); // NOI18N
        add(lTransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void bCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCariActionPerformed
        String keyword = JOptionPane.showInputDialog(this, "Masukkan Nama Santri / Nomor Kamar / Nama Barang:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            transaksiPerkakas t = new transaksiPerkakas();
            t.cariData(modelTransaksi, keyword.trim());
        } else {
            JOptionPane.showMessageDialog(this, "Masukkan kata kunci pencarian terlebih dahulu!");
        }
    }//GEN-LAST:event_bCariActionPerformed

    private void bTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambahActionPerformed
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

        if (t.Kembalikan()) {//panggil method kembalikan
            JOptionPane.showMessageDialog(this, "Barang berhasil dikembalikan!");
            loadData();
            reset();
        } else {
            JOptionPane.showMessageDialog(this, "Pengembalian gagal! Periksa tanggal atau data transaksi.");
        }
    }//GEN-LAST:event_bUbahActionPerformed

    private void bHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapusActionPerformed
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

    private void bResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetActionPerformed
        reset();
    }//GEN-LAST:event_bResetActionPerformed

    private void tableTransaksiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableTransaksiMouseClicked
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

    private void bRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bRefreshActionPerformed
        tampilkanData();
    }//GEN-LAST:event_bRefreshActionPerformed

    private void bResetDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bResetDataActionPerformed
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

    private void bCetakPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetakPDFActionPerformed
        transaksiPerkakas t = new transaksiPerkakas();
        t.cetakPDF(tableTransaksi);
    }//GEN-LAST:event_bCetakPDFActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bCari;
    private javax.swing.JButton bCetakPDF;
    private javax.swing.JButton bHapus;
    private javax.swing.JButton bRefresh;
    private javax.swing.JButton bReset;
    private javax.swing.JButton bResetData;
    private javax.swing.JButton bTambah;
    private javax.swing.JButton bUbah;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcTgl_Kembali;
    private com.toedter.calendar.JDateChooser jdcTgl_Pinjam;
    private javax.swing.JLabel lJumlah;
    private javax.swing.JLabel lNamaBarang;
    private javax.swing.JLabel lNamaSantri;
    private javax.swing.JLabel lNomorKamar;
    private javax.swing.JLabel lTransaksi;
    private javax.swing.JLabel lWaktuKembali;
    private javax.swing.JLabel lWaktuPinjam;
    private javax.swing.JTextField tJumlah;
    private javax.swing.JTextField tNamaBarang;
    private javax.swing.JTextField tNamaSantri;
    private javax.swing.JTextField tNoKamar;
    private javax.swing.JTable tableTransaksi;
    // End of variables declaration//GEN-END:variables
}
