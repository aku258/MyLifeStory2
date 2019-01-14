/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author akash
 */
public class BaseDAO {
     private Connection con;

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        con = DriverManager.getConnection("jdbc:derby://localhost:1527/MyLifeStory", "MyLifeStory", "MyLifeStory");
        return con;
    }

    public void closeConnection(PreparedStatement ps, ResultSet rs) {
        try {
            con.close();
            ps.close();
            rs.close();
        } catch (SQLException ex) {
            //Logger.getLogger(CustomerMasterDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
