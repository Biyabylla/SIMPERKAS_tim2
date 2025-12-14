package kelas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import simperkas.Home;
import test_simperkas.Home;
/**
 *
 * @author Angga Sanjaya
 */
public class User {
    String userName, userPassword, userFullName;
    private int userStatus;
    
    private final Connection conn;
    private PreparedStatement ps;
    private Statement st;
    private ResultSet rs;
    private String query;
    
    public User(){
        conn =koneksi.connect();
    };
    
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
    public void tambahUser() throws SQLException{
        query = "INSERT INTO user (userName, userPassword, userFullName, userStatus)" + "VALUES (?,MD5 (?),?,?)";
        
        ps = conn.prepareStatement(query);
        ps.setString(1, userName);
        ps.setString(2, userPassword);
        ps.setString(3, userFullName);
        ps.setInt(4, userStatus);
        ps.executeUpdate();
        ps.close();
        
        JOptionPane.showMessageDialog(null, "Data Berhasil Ditambahkan!");
    }
    
    // Hapus User
    public void hapusUser(){
        try {
            query = "DELETE FROM user WHERE userName = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, userName);
            ps.executeUpdate();
            ps.close();
            
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus!");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Dihapus! " + e.getMessage());
        }
    }
    
    // Ubah User
    public void ubahUser(){
        try {
            Connection conn = koneksi.connect();
            String pwd      = this.userPassword;
            if (pwd == null || pwd.isEmpty()) {
                String sqlold = "SELECT userpassword FROM user WHERE userName=?";
                PreparedStatement pst = conn.prepareStatement(sqlold);
                pst.setString(1, this.userName);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    pwd = rs.getString("userPassword");
                }
                rs.close();
                pst.close();
            }
            
            String sql = "UPDATE user SET userPassword=?, userFullName=?, userStatus=?, WHERE userName=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, pwd);
            pst.setString(2, this.userFullName);
            pst.setInt(3, this.userStatus);
            pst.setString(4, this.userName);
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Mengubah Data: " + e.getMessage());
        }
    }
    
    public ResultSet tampianUser(){
        try {
            query   = "SELECT * FROM user";
            st      = conn.createStatement();
            rs      = st.executeQuery(query);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Data Gagal Ditampilkan!");
        }
        return rs;
    }
    
    public void login(){
        try {
            query = "SELECT * FROM user WHERE userName = ? AND userPassword = MD5 (?)";
            
            ps  = conn.prepareStatement(query);
            ps.setString(1, userName);
            ps.setString(2, userPassword);
            rs  = ps.executeQuery();
            
            if (rs.next()) {
                Session.setUserName (rs.getString("userName"));
                Session.setFullName (rs.getString("userFullName"));
                Session.setStatus   ("Aktif");
                
                new Home().setVisible(true);
                
            } else {
                JOptionPane.showMessageDialog(null, "Username atau Password Salah!");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Login Gagal: " + e.getMessage());
        }
    }
    
    public void logout(){
        Session.setUserName("");
        Session.setFullName("");
        Session.setStatus("");
    }
    
}
