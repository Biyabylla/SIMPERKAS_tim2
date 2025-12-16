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
import simperkas.Home;
/**
 *
 * @author Nabila
 */
public class User {
    private String userName, userPassword, userFullName;
    private int userStatus;

    private final Connection conn;
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private String query;

    public User() {
        conn = koneksi.connect();
    }

    // Getter - Setter
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    // Tambah user
    public void tambahUser() throws SQLException {
        query = "INSERT INTO user (userName, userPassword, userFullName, userStatus) "
                + "VALUES (?,MD5(?),?,?)";

        ps = conn.prepareStatement(query);
        ps.setString(1, userName);
        ps.setString(2, userPassword);
        ps.setString(3, userFullName);
        ps.setInt(4, userStatus);
        ps.executeUpdate();
        ps.close();

        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan!");
    }

    public void hapusUser() {
        try {
            query = "DELETE FROM user WHERE userName = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, userName);
            ps.executeUpdate();
            ps.close();

            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal dihapus! " + e.getMessage());
        }
    }

    public void ubahUser() {
    try {
        Connection conn = koneksi.connect(); 
        String pwd = this.userPassword;
        if (pwd == null || pwd.isEmpty()) {
            String sqlOld = "SELECT userPassword FROM user WHERE userName=?";
            PreparedStatement pst = conn.prepareStatement(sqlOld);
            pst.setString(1, this.userName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                pwd = rs.getString("userPassword");
            }
            rs.close();
            pst.close();
        }

        String sql = "UPDATE user SET userPassword=?, userFullName=?, userStatus=? WHERE userName=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, pwd);
        pst.setString(2, this.userFullName);
        pst.setInt(3, this.userStatus);
        pst.setString(4, this.userName);
        pst.executeUpdate();
        pst.close();
        JOptionPane.showMessageDialog(null, "Data berhasil diubah");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Gagal mengubah data: " + e.getMessage());
    }
}

    public ResultSet tampilUser() {
        try {
            query = "SELECT * FROM user";
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal ditampilkan!");
        }
        return rs;
    }

    public void login() {
        try {
            query = "SELECT * FROM user WHERE userName = ? AND userPassword = MD5(?)";

            ps = conn.prepareStatement(query);
            ps.setString(1, userName);
            ps.setString(2, userPassword);
            rs = ps.executeQuery();

            if (rs.next()) {
                Session.setUserName(rs.getString("userName"));
                Session.setFullName(rs.getString("userFullName"));
                Session.setStatus("Aktif");

                new Home().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Username atau password salah!");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Login gagal: " + e.getMessage());
        }
    }

    public void logout() {
        Session.setUserName("");
        Session.setFullName("");
        Session.setStatus("");
    }
}

