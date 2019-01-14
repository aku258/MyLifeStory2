/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylifestory;

import java.util.*;
import model.JournalList;
import model.JournalTemplateDAO;
import model.TimelineList;
/**
 *
 * @author akash
 */
public abstract class JournalTemplate {
     Scanner sc;
     JournalList journal;
     List<TimelineList> timelinelist;
     List<String> bloglist;
     JournalTemplateDAO jtd;
     String currenttimeline;
     String currenttimelinefilename;
     TimelineList curtimelinelist;
    JournalTemplate(JournalList journal){
        sc = new Scanner(System.in);
        this.journal = journal;
        jtd = new JournalTemplateDAO();
        timelinelist = new ArrayList<>();
        displayalltimelines();
    }
    
   void displayalltimelines(){
        timelinelist = jtd.gettimelines(journal.getTablename());
        System.out.println("Enter the index of journal to open the journal");
        for(int i=0; i<timelinelist.size();i++){
            System.out.println((i+1) + ". " +timelinelist.get(i).getTimelinename());
        }
        System.out.println("0. New Timeline");
        System.out.println("-1. Delete a Timeline");
        
        int op = sc.nextInt();
        
        if(op == 0)
        {
            createnewtimeline();
        }
        else if(op == -1){
            deletetimeline();
        }
        else{
           currenttimeline = timelinelist.get(op-1).getTimelinename();
           currenttimelinefilename = timelinelist.get(op-1).getTablename();
           showtimeline(timelinelist.get(op-1));
        }
    }

    void createnewtimeline(){
        System.out.println("enter name for timeline");
        String name = sc.nextLine();
        name = sc.nextLine();
        
        if(jtd.createtimeline(name, journal.getTablename()))
            System.out.println(name + " timeline successfully created");
        else
            System.out.println("failed");
        
        displayalltimelines();
    }

    

    void deletetimeline(){ 
        System.out.println("Select Index of timeline to be deleted");
        int in = sc.nextInt();
        String name = timelinelist.get( in -1).getTimelinename();
        if(jtd.deletetimeline(name,journal.getTablename()))
            System.out.println(name + " timeline successfully deleted");
        else
            System.out.println("failed to delete "+name);
        
        displayalltimelines();
    }

    
    
    void deleteblog(List bloglist){
        jtd.deleteblog(bloglist,currenttimelinefilename);
        showtimeline(curtimelinelist);
    }
    
    void showtimeline(TimelineList tll){
       bloglist = jtd.getbloglist(tll);
        System.out.println("Enter index of blog to perform action");
        for(String blogs: bloglist){
            String[] blogdata = blogs.split(",");
            int i=1;
            if(blogdata[0].equals("V"))
            {
                System.out.println((i++) +". " + blogdata[1]);
            }
        }
            System.out.println("0. add blog");
            System.out.println("-1. delete blog");
            
            int bop = sc.nextInt();
            
            if(bop == 0){
                addblog();
            }
            else if(bop==-1){
                System.out.println("enter the index of blog to be deleted");
                int delblog = sc.nextInt();
                bloglist.remove(delblog -1);
                deleteblog(bloglist);
            }
            else{
                String blogdetails = bloglist.get(bop - 1);
                showblog(blogdetails);
            }
        
        
    }; 
    String blogfile;
    void addblog(){
        System.out.println("enter blog name");
        String blogname = sc.nextLine();
        blogname = sc.nextLine();
        blogfile = jtd.createblog(currenttimeline,journal.getTablename(),blogname);
        //create entry in file 
        //V/I,timestamp,blogname,blogfile
    }
    abstract void showblog(String blogdetails);
    abstract void editblog();
}
