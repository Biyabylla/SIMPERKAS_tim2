
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Nabila
 */

public class Perkakas extends koneksi {

    private String id;
    private String nama;
    private int jumlah;
    private int jumlah_awal;
    private String status;

    private Connection Koneksi;
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private String query;

    public Perkakas() {
        Koneksi = super.connect();
    }

    public Perkakas(String id, String nama, int jumlah) {
        this();
        this.id = id;
        this.nama = nama;
        this.jumlah = jumlah;
    }

    public void setIdBarang(String id) {
        this.id = id;
    }

    public String getIdBarang() {
        return id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNamaBarang() {
        return nama;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlahAwal(int jumlah_awal) {
        this.jumlah_awal = jumlah_awal;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void tambahBarang() {
        query = "INSERT INTO barang (id_barang, nama_barang, "
                + "jumlah, jumlah_awal, status) VALUES(?,?,?,?,?)";
        try {
            ps = Koneksi.prepareStatement(query);
            ps.setString(1, id);
            ps.setString(2, nama);
            ps.setInt(3, jumlah);
            ps.setInt(4, jumlah_awal);
            ps.setString(5, status);

            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal ditambah!\n" + e.getMessage());
        }
    }

    public void ubahBarang() {
        query = "UPDATE barang SET nama_barang=?, jumlah=?, status=? WHERE id_barang=?";

        try {
            ps = Koneksi.prepareStatement(query);
            ps.setString(1, nama);
            ps.setInt(2, jumlah);
            ps.setString(3, status);
            ps.setString(4, id);

            ps.executeUpdate();
            ps.close();

            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal diubah!\n" + e.getMessage());
        }
    }

    public void Hapus() {
        query = "DELETE FROM barang WHERE nama_barang=?";
        try {
            ps = Koneksi.prepareStatement(query);
            ps.setString(1, nama);

            int rows = ps.executeUpdate();
            ps.close();

            if (rows > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal menghapus data!\n" + e.getMessage());
        }
    }

    public ResultSet tampilBarang() {
        query = "SELECT id_barang, nama_barang, jumlah, jumlah_awal, status FROM barang";
        try {
            st = Koneksi.createStatement();
            rs = st.executeQuery(query);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data gagal ditampilkan!\n" + e.getMessage());
        }

        return rs;
    }

    public void tambahJumlah(int qty) {
        if (qty > 0) {
            this.jumlah += qty;
        }
    }

    public void kurangiJumlah(int qty) {
        if (qty > 0 && this.jumlah >= qty) {
            this.jumlah -= qty;
        }
    }

    public boolean bisaDipinjam(int qty) {
        return qty > 0 && this.jumlah >= qty;
    }
}
