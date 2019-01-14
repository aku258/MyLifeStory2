/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author akash
 */
public class JournalDAO extends BaseDAO{
    
    Connection connection;
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet rs;

    public JournalDAO() {
        try {
            connection = getConnection();
        } catch (ClassNotFoundException ex) {
            System.out.println("not connected");
            Logger.getLogger(JournalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("not connected 2");
            Logger.getLogger(JournalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("connected");
    }
    
    public List getList(){
        String sql = "select * from MyLifeStory.journallist";
        int count = 1;
        List<JournalList> journallist = new ArrayList<JournalList>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                JournalList jl = new JournalList();
                jl.setIndex(count++);
                jl.setJournalname(rs.getString(1));
                jl.setTablename(rs.getString(2));
                jl.setTabletype(rs.getString(3));
                journallist.add(jl);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(JournalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //closeConnection(preparedStatement, rs);
        }

        return journallist;
    }
    
    public boolean createjournal(String name,String type){
        String sql = "Insert into MyLifeStory.journallist values (?,?,?)";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            String tablename  = name.replaceAll("\\s", "_");
            preparedStatement.setString(2, tablename);
            preparedStatement.setString(3,type);
            
            int s = preparedStatement.executeUpdate();
            if (s != 0) {
                String sql2 = "Create table MyLifeStory." +tablename+ "( timelinename varchar(100), timelinetable varchar(100), PRIMARY KEY(timelinename) )";
                preparedStatement = connection.prepareStatement(sql2);
                int s2 = preparedStatement.executeUpdate();
                if(s2 == 0){
                    return true;
                }
                else{
                    return false;
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(JournalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           // closeConnection(preparedStatement, rs);
        };
        return false;
    }
    public boolean deletejournal(String name){
        String sql = "delete from MyLifeStory.journallist where journalname = ?";
         try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            int s = preparedStatement.executeUpdate();
            if (s != 0) {
                String tablename  = name.replaceAll("\\s", "_");
                String sql2 = "Drop table MyLifeStory." +tablename;
                preparedStatement = connection.prepareStatement(sql2);
                int s2 = preparedStatement.executeUpdate();
                if(s2!=0)
                    return true;
                else{
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JournalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           // closeConnection(preparedStatement, rs);
        };
        return false;
    }
    
    public void closeconn(){
        closeConnection(preparedStatement, rs);
    }
}
