/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Nabila
 */
public class Koneksi {
    private static Connection mysqlconfig;
    public static Connection connect(){
        try {
            String url = "jdbc:mysql://localhost:3306/simperkas";
            String username = "root";
            String pass = "";
            mysqlconfig = DriverManager.getConnection(url, username, pass);
        } catch (SQLException sQLException) {
            System.err.println(sQLException.getMessage());
        }
        return mysqlconfig;
    }
    
}
