/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylifestory;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.JournalDAO;
import model.JournalList;

/**
 *
 * @author akash
 */
public class Homepage {
    
    Scanner sc;
    List<JournalList> journallist;
    JournalDAO jd;
    
    Homepage(){
        sc = new Scanner(System.in);
        jd = new JournalDAO();
        journallist = new ArrayList<>();
        displayhome();
    }
    void displayhome(){
        journallist = jd.getList();
        System.out.println("Enter the index of journal to open the journal");
        for(int i=0; i<journallist.size();i++){
            System.out.println(journallist.get(i).getIndex() + ". " +journallist.get(i).getJournalname());
        }
        System.out.println("0. New Journal");
        System.out.println("-1. Delete a journal");
        
        int op = sc.nextInt();
        
        if(op == 0)
        {
            createnewjournal();
        }
        else if(op == -1){
            deletejournal();
        }
        else{
            openjournal(op);
        }
    }
    
    void createnewjournal(){
        System.out.println("enter type of journal");
        System.out.println(" 1. diary \n 2. travel \n 3. food");
        int type = sc.nextInt();
        System.out.println("enter name for journal");
        String name = sc.nextLine();
        name = sc.nextLine();
        String jtype;
        if(type == 1)
        {
            jtype = "diary";
        }
        else if(type == 2)
            jtype = "travel";
        else
            jtype = "food";
        if(jd.createjournal(name, jtype))
            System.out.println(name + " journal successfully created");
        else
            System.out.println("failed");
        
        displayhome();
    }
    
    void deletejournal(){
        System.out.println("Select Index of journal to be deleted");
        int in = sc.nextInt();
        String name = journallist.get( in -1).getJournalname();
        if(jd.deletejournal(name))
            System.out.println(name + " journal successfully deleted");
        else
            System.out.println("failed to delete "+name);
        
        displayhome();
    }
    
    void openjournal(int op){
        
        String type = journallist.get( op -1).getTabletype();
        jd.closeconn();
        
        if(type.equals("diary")){
            Diary d = new Diary(journallist.get(op-1));
        }
        else if(type.equals("travel")){
            TravelJ tj = new TravelJ(journallist.get(op-1));
        }
        else
        {    FoodJ fj = new FoodJ(journallist.get(op-1));
        }
        System.out.println("success");
    }
}
