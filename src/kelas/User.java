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

 public void ubahUser() throws SQLException {
        PreparedStatement ps;

        if (userPassword == null || userPassword.isEmpty()) {
            String sql = "UPDATE user SET userFullName=?, userStatus=? WHERE userName=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userFullName);
            ps.setInt(2, userStatus);
            ps.setString(3, userName);
        } else {
            String sql = "UPDATE user SET userPassword=MD5(?), userFullName=?, userStatus=? WHERE userName=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, userPassword);
            ps.setString(2, userFullName);
            ps.setInt(3, userStatus);
            ps.setString(4, userName);
        }
        ps.executeUpdate();
        ps.close();
    }

    public ResultSet tampilUser() throws SQLException {
        Statement st = conn.createStatement();
        return st.executeQuery("SELECT * FROM user");
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

