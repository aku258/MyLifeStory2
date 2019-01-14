/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylifestory;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.JournalList;
import model.JournalTemplateDAO;

/**
 *
 * @author akash
 */
public class Diary extends JournalTemplate{
    
    Diary(JournalList journal){
        super(journal);
    }
    
       
     
     void addblog(){
         super.addblog();
        try {
            FileWriter fr  = new FileWriter(blogfile);
            List<String> stmts = new ArrayList<String>();
            System.out.println("Start writing your in your personal blog");
            System.out.println("to stop writing type end");
            String tempdata;
            do{
                tempdata = sc.nextLine();
                if(!tempdata.equals("end")){
                fr.write(tempdata);
                fr.write("\n");
                }
            }while(!tempdata.equals("end"));
            fr.flush();
            fr.close();
        } catch (IOException ex) {
            Logger.getLogger(Diary.class.getName()).log(Level.SEVERE, null, ex);
        }
     };
     
     void showblog(String blogdetails){
         String[] blogdata = blogdetails.split(",");
         List<String>blogcontents = super.jtd.displayblog(blogdata[2]);
         for(String contentline: blogcontents){
             System.out.println(contentline);
         }
         super.displayalltimelines();
         
     };
     
     void editblog(){};
}
