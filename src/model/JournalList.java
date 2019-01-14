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
public class JournalList {
    int index;
    String journalname;
    String tablename;
    String tabletype;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getJournalname() {
        return journalname;
    }

    public void setJournalname(String journalname) {
        this.journalname = journalname;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public String getTabletype() {
        return tabletype;
    }

    public void setTabletype(String tabletype) {
        this.tabletype = tabletype;
    }
}
