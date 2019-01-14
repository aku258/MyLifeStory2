/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author akash
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
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

public class JournalTemplateDAO extends BaseDAO{
     Connection connection;
    PreparedStatement preparedStatement;
    Statement statement;
    ResultSet rs;

    public JournalTemplateDAO() {
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
    
    public List gettimelines(String tablename){
    String sql = "select * from MyLifeStory." + tablename +"";
        List<TimelineList> timelinelist = new ArrayList<TimelineList>();
        try {
            preparedStatement = connection.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                TimelineList tl = new TimelineList();
                tl.setTimelinename(rs.getString(1));
                tl.setTablename(rs.getString(2));
                timelinelist.add(tl);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(JournalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //closeConnection(preparedStatement, rs);
        }

        return timelinelist;
    }
    
    public boolean createtimeline(String name, String table){
        String sql = "Insert into "+table+" values (?,?)";
        
        

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            String tablename  = name.replaceAll("\\s", "_");
            tablename = createtimelinefile(tablename,table);
            preparedStatement.setString(2, tablename);
            
            int s = preparedStatement.executeUpdate();
            if (s != 0) 
                return true; 
        } catch (SQLException ex) {
            Logger.getLogger(JournalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           // closeConnection(preparedStatement, rs);
        };
        return false;
    }
    
    public boolean deletetimeline(String name, String table){
                String sql = "delete from MyLifeStory."+table+" where timelinename = ?";
                String tablename  = name.replaceAll("\\s", "_");
            
         try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            
            if(deletetimelinefile(tablename,table)){
                int s = preparedStatement.executeUpdate();
                if (s != 0) {
                    return true;
                }
            }                
            else
                System.out.println("failed to delete file");
            
        } catch (SQLException ex) {
            Logger.getLogger(JournalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
    }
    
    String createtimelinefile(String filename,String table){
        
        filename = "./"+table+"/"+filename+".txt";
        File f = new File(filename);  
        f.getParentFile().mkdirs(); 
         try {
             f.createNewFile();
         } catch (IOException ex) {
             Logger.getLogger(JournalTemplateDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        System.out.println(filename);
        return filename;
    }
    
    boolean deletetimelinefile(String filename,String table){
        filename = "/"+table+"/"+filename+".txt";
         try { 
             Files.deleteIfExists(Paths.get(filename));
             return true;
         } catch(NoSuchFileException e) 
           { 
            System.out.println("No such file/directory exists"); 
            return false;
        } catch (IOException ex) { 
             Logger.getLogger(JournalTemplateDAO.class.getName()).log(Level.SEVERE, null, ex);
         } 
       return false;  
    }
    
    public String createblog(String timelinename,String table,String blogname ){
        String sql = "Select timelinetable from MyLifeStory."+table+" where timelinename = '"+timelinename+"'";
        String blogfile = "";
        try {System.out.println(sql);
             preparedStatement = connection.prepareStatement(sql);
//             preparedStatement.setString(1, timelinename);
             rs = preparedStatement.executeQuery();
             while(rs.next()){
                 String filename = rs.getString(1);
                 
                 FileWriter fr = new FileWriter(filename,true);
                 filename = filename.replaceAll(".txt", "1");
                 filename = filename.replaceAll("/","");
                 filename = filename.replaceFirst(".","./");
                 String blogshort = blogname.replaceAll("\\s", "_");
                 blogfile = filename+"/"+blogshort+".txt";
                 fr.write("V,"+blogname+","+blogfile+"\n");                 
                 fr.flush();
                 fr.close();
                 System.out.println(blogfile);
                 File f = new File(blogfile);  
                 f.getParentFile().mkdirs(); 
                 f.createNewFile();
                 
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(JournalTemplateDAO.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(JournalTemplateDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        return blogfile;
    }
    
    public List getbloglist(TimelineList tll){
        List<String> bloglist = new ArrayList<>();
        
        String filename = tll.getTablename();
         try {
             bloglist = Files.readAllLines(Paths.get(filename));
         } catch (IOException ex) {
             Logger.getLogger(JournalTemplateDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        
        return bloglist;
    }
    
    public List displayblog(String blogfile){
       List<String> blogcontent = new ArrayList<>(); 
       
         try {
             blogcontent = Files.readAllLines(Paths.get(blogfile));
         } catch (IOException ex) {
             Logger.getLogger(JournalTemplateDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
         return blogcontent;
    }
    
    public void deleteblog(List<String> bloglist,String filename){
         try {
             FileWriter fr = new FileWriter(filename,true);
             for(String blogdata: bloglist){
                 fr.write(blogdata);
                 fr.write("\n");
             }
             fr.flush();
             fr.close();
         } catch (IOException ex) {
             Logger.getLogger(JournalTemplateDAO.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
}
