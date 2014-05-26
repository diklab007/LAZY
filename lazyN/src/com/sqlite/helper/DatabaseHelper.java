package com.sqlite.helper;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
	
	 // Logcat tag
    private static final String LOG = "DatabaseHelper";
 
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "LazyNotesDB";
 
    // Table Names
    private static final String TABLE_TODO = "todos";
    private static final String TABLE_TAG = "tags";
    private static final String TABLE_TODO_TAG = "todo_tags";
 
    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";
 
    // NOTES Table - column names
    private static final String KEY_TODO = "todo";
    private static final String KEY_STATUS = "status";
 
    // TAGS Table - column names
    private static final String KEY_TAG_NAME = "tag_name";
 
    // NOTE_TAGS Table - column names
    private static final String KEY_TODO_ID = "todo_id";
    private static final String KEY_TAG_ID = "tag_id";
 
    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_TODO = "CREATE TABLE "
            + TABLE_TODO + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TODO
            + " TEXT," + KEY_STATUS + " INTEGER," + KEY_CREATED_AT
            + " DATETIME" + ")";
 
    // Tag table create statement
    private static final String CREATE_TABLE_TAG = "CREATE TABLE " + TABLE_TAG
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TAG_NAME + " TEXT,"
            + KEY_CREATED_AT + " DATETIME" + ")";
 
    // todo_tag table create statement
    private static final String CREATE_TABLE_TODO_TAG = "CREATE TABLE "
            + TABLE_TODO_TAG + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_TODO_ID + " INTEGER," + KEY_TAG_ID + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";
 
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_TODO);
        db.execSQL(CREATE_TABLE_TAG);
        db.execSQL(CREATE_TABLE_TODO_TAG);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO_TAG);
 
        // create new tables
        onCreate(db);
    }
    
    /*******************************methods***************************************/
    // Creating a todo
    public long createToDo(ToDo todo, int tag_ids) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_TODO, todo.getNote());
        values.put(KEY_STATUS, todo.getStatus());
        values.put(KEY_CREATED_AT, todo.getDateTime());
     
        // insert row
        long todo_id = db.insert(TABLE_TODO, null, values);
     
        // assigning tags to todo
      //  for (long tag_id : tag_ids) {
        //    createTodoTag(todo_id, tag_id);
        //}
     
        return todo_id;
    }

/*    public long createToDo(ToDo todo) {

        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_TODO, todo.getNote());
        values.put(KEY_STATUS, todo.getStatus());
        values.put(KEY_CREATED_AT, todo.getDateTime());
     
        // insert row
        long todo_id = db.insert(TABLE_TODO, null, values);
     
        // assigning tags to todo
        for (long tag_id : tag_ids) {
            createTodoTag(todo_id, tag_id);
        }
     
        return todo_id;
    }*/
    
    //get single todo SELECT * FROM todos WHERE id = 1;
    public ToDo getTodo(long todo_id) {
        SQLiteDatabase db = this.getReadableDatabase();
     
        String selectQuery = "SELECT  * FROM " + TABLE_TODO + " WHERE "
                + KEY_ID + " = " + todo_id;
     
        Log.e(LOG, selectQuery);
     
        Cursor c = db.rawQuery(selectQuery, null);
     
        if (c != null)
            c.moveToFirst();
     
        ToDo td = new ToDo();
        td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
        td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
     
        return td;
    }
    
    // getting all todos
    public List<ToDo> getAllToDos() {
        List<ToDo> todos = new ArrayList<ToDo>();
        String selectQuery = "SELECT  * FROM " + TABLE_TODO;
     
        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ToDo td = new ToDo();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
     
                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }
     
        return todos;
    }



// Updating a Todo
      public int updateToDo(ToDo todo) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_TODO, todo.getNote());
        values.put(KEY_STATUS, todo.getStatus());
     
        // updating row
        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(todo.getId()) });
    }


   //Deleting a Todo
      public void deleteToDo(long tado_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TODO, KEY_ID + " = ?",
                new String[] { String.valueOf(tado_id) });
    }


// Creating Tag
    public long createTag(Tag tag) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_TAG_NAME, tag.getTagName());
        values.put(KEY_CREATED_AT, getDateTime());
     
        // insert row
        long tag_id = db.insert(TABLE_TAG, null, values);
        tag_id = tag_id;
        return tag_id;
    }


 // Fetching all Tag names    SELECT * FROM tags;
    
    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<Tag>();
        String selectQuery = "SELECT  * FROM " + TABLE_TAG;
     //dikla
        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Tag t = new Tag();
                t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                t.setTagName(c.getString(c.getColumnIndex(KEY_TAG_NAME)));
     
                // adding to tags list
                tags.add(t);
            } while (c.moveToNext());
        }
        return tags;
    }


    //  Updating a tag
    public int updateTag(Tag tag) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_TAG_NAME, tag.getTagName());
     
        // updating row
        return db.update(TABLE_TAG, values, KEY_ID + " = ?",
                new String[] { String.valueOf(tag.getId()) });
    }

    /*
     * getting all todos under single tag
     * */
    public List<ToDo> getAllToDosByTag(String tag_name) {
        List<ToDo> todos = new ArrayList<ToDo>();
     
        String selectQuery = "SELECT  * FROM " + TABLE_TODO + " td, "
                + TABLE_TAG + " tg, " + TABLE_TODO_TAG + " tt WHERE tg."
                + KEY_TAG_NAME + " = '" + tag_name + "'" + " AND tg." + KEY_ID
                + " = " + "tt." + KEY_TAG_ID + " AND td." + KEY_ID + " = "
                + "tt." + KEY_TODO_ID;
     
        Log.e(LOG, selectQuery);
     
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
     
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                ToDo td = new ToDo();
                td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                td.setNote((c.getString(c.getColumnIndex(KEY_TODO))));
                td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));
     
                // adding to todo list
                todos.add(td);
            } while (c.moveToNext());
        }
     
        return todos;
    }

    /*
     * Deleting a tag
     */
    public void deleteTag(Tag tag, boolean should_delete_all_tag_todos) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        // before deleting tag
        // check if todos under this tag should also be deleted
        if (should_delete_all_tag_todos) {
            // get all todos under this tag
        	List<ToDo> allTagToDos = getAllToDosByTag(tag.getTagName());
            // delete all todos
            for (ToDo todo : allTagToDos) {
                // delete todo
                deleteToDo(todo.getId());
            }
        }
     
        // now delete the tag
        db.delete(TABLE_TAG, KEY_ID + " = ?",
                new String[] { String.valueOf(tag.getId()) });
    }


 // Assigning a Tag to Todo
           public long createTodoTag(long todo_id,int tag_id) {
            SQLiteDatabase db = this.getWritableDatabase();
     
            ContentValues values = new ContentValues();
            values.put(KEY_TODO_ID, todo_id);
            values.put(KEY_TAG_ID, tag_id);
            values.put(KEY_CREATED_AT, getDateTime());
     
            long id = db.insert(TABLE_TODO_TAG, null, values);
     
            return id;
        }


// Removing Tag of Todo
      public int updateNoteTagRemove(long id, long tag_id) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_TAG_ID, tag_id);
     
        // updating row
        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

// Updating a todo tag-changeTag
    
    public int updateNoteTagChange(long id, long tag_id) {
        SQLiteDatabase db = this.getWritableDatabase();
     
        ContentValues values = new ContentValues();
        values.put(KEY_TAG_ID, tag_id);
     
        // updating row
        return db.update(TABLE_TODO, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }



    // closing database
        public void closeDB() {
            SQLiteDatabase db = this.getReadableDatabase();
            if (db != null && db.isOpen())
                db.close();
        }

        private String getDateTime() {
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            return dateFormat.format(date);
        }

}

