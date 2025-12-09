/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelas;

import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import test_simperkas.Home;

public class User {
    private String userName, userPassword, userfullName;
    private int userStatus;
    
    private final Connection conn;
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private String query;
    
    public User(){
        conn = Koneksi.connect();    
    }
    //Setter n Getter
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

    public String getUserfullName() {
        return userfullName;
    }

    public void setUserfullName(String userfullName) {
        this.userfullName = userfullName;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public PreparedStatement getPs() {
        return ps;
    }

    public void setPs(PreparedStatement ps) {
        this.ps = ps;
    }

    public Statement getSt() {
        return st;
    }

    public void setSt(Statement st) {
        this.st = st;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
    //Tambah User
    public void tambahUser() throws SQLException{
        query = "INSERT INTO user (userName, userPassword, userFullName, userStatus)" + "VALUES (?, MD5(?), ?, ?)";
        
        ps = conn.prepareStatement(query);
        ps.setString(1, userName);
        ps.setString(2, userPassword);
        ps.setString(3, userfullName);
        ps.setInt(4, userStatus);
        ps.executeUpdate();
        ps.close();
        
        JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
    }
    //Hapus User
    public void hapusUser(){
        
        try {
            query = "DELETE FROM user WHERE userName = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, userName);
            ps.executeUpdate();
            ps.close();
            
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
        } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Data gagal dihapus!"+ e.getMessage());
        }
    }
    //Ubah User
    public void ubahUser(){
        
        try {
            Connection conn = Koneksi.connect();
            String pwd = this.userPassword;
            if (pwd == null || pwd.isEmpty()) {
                String sqlOld = "SELECT userPassword FROM user WHERE userName = ?";
                PreparedStatement pst = conn.prepareStatement(sqlOld);
                pst.setString(1, this.userName);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    pwd = rs.getString("userPassword");
                }
                rs.close();
                pst.close();
            }
            String sql = " UPDATE user WHERE userPassword = ?, userFullName = ?, userStatus = ? WHERE userName = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, pwd);
            pst.setString(2, this.userfullName);
            pst.setInt(3, this.userStatus);
            pst.setString(4, this.userName);
            pst.execute();
            pst.close();
            
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Data gagal diubah : " + e.getMessage());
        }
    }   
    //Tampil User
    public ResultSet tampilUser(){
        
        try {
            query = "SELECT * FROM user";
            st = conn.createStatement();
            rs = st.executeQuery(query);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data gagal ditampilkan!");
        }
        return rs;
    }
    //Login User 
    public void login(){
        
        try {
            query = "SELECT * FROM user WHERE userName = ? AND userPassword = MD5(?)";
            
            ps = conn.prepareStatement(query);
            ps.setString(1, userName);
            ps.setString(2, userPassword);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                Sesion.setUserName(rs.getString("userName"));
                Sesion.setFullName(rs.getString("userFullName"));
                Sesion.setStatus("Aktif");
                
                new Home().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Username atau password salah!");
            }
        } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Login gagal : " + e.getMessage());
        }
    }
    //Logout User
    public void logOut(){
        Sesion.setUserName("");
        Sesion.setFullName("");
        Sesion.setStatus("");             
    }
    }
    
       

