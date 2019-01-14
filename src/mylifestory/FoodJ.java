/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylifestory;

import model.JournalList;
import model.JournalTemplateDAO;

/**
 *
 * @author akash
 */
public class FoodJ extends JournalTemplate{
    
    FoodJ(JournalList journal){
        super(journal);
    }
    
    void showtimeline(){};    
     void addblog(){};
     void showblog(String details){};
     void editblog(){};
}
