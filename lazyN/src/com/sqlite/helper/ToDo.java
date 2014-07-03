package com.sqlite.helper;



public class ToDo {
	
	int id;
	int arrayId;
    String note;
    int status;
    String created_at;
 
    // constructors
    public ToDo() {
    }
    
    public ToDo(String note) {
        this.note = note;
    }
    

    public ToDo(String note, int position) {
        this.note = note;
        this.arrayId = position;
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
    
    public void setArrayId(int id) {
        this.arrayId = id;
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
    public int getArrayId() {
        return this.arrayId;
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