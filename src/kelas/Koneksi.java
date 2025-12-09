/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kelas;

import java.sql.Connection;
import java.sql.DriverAction;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Koneksi {
    
    private static Connection mysqlconfig;
    
    public static Connection connect(){
    
        try {
            String url = "jdbc:mysql://localhost:3306/simperkas_test" ;
            String userName = "root";
            String password = "";
            mysqlconfig = DriverManager.getConnection(url, userName, password);
            
        } catch (SQLException sQLException) {
            System.err.println(sQLException.getMessage());
        }
        return mysqlconfig;
    }
}
