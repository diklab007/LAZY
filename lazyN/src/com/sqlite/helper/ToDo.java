package com.sqlite.helper;



public class ToDo {
	
	int id;
    String note;
    int status;
    String created_at;
 
    // constructors
    public void Todo() {
    }
    
    public void Todo(String note) {
        this.note = note;
    }
    
    public void Todo(String note, int status) {
        this.note = note;
        this.status = status;
    }
 
    public void Todo(int id, String note, int status) {
        this.id = id;
        this.note = note;
        this.status = status;
    }
 
    // setters
    public void setId(int id) {
        this.id = id;
    }
 
    public void setNote(String note) {
        this.note = note;
    }
 
    public void setStatus(int status) {
        this.status = status;
    }
     
    public void setCreatedAt(String created_at){
        this.created_at = created_at;
    }
 
    // getters
    public long getId() {
        return this.id;
    }
 
    public String getNote() {
        return this.note;
    }
 
    public int getStatus() {
        return this.status;

    }
    
   public String getDateTime(){
	   return this.created_at;
   }
   
}