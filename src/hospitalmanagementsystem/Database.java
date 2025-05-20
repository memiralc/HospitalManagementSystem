/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author memir
 */
public class Database {
    public static Connection connectDB(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect =DriverManager.getConnection("jdbc:mysql://localhost/hospital","root","1qazw76.pm1");
            return connect;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
