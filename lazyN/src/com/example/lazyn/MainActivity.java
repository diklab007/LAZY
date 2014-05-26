package com.example.lazyn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.Inflater;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

import com.sqlite.helper.*;
import com.example.lazyn.R;

public class MainActivity extends ActionBarActivity {
	StableArrayAdapter adapter;
	//List<String> list = new LinkedList<String>();
	//database connection
	DatabaseHelper db= new DatabaseHelper(this);
	/******************************stableArrayAdapterclass*****************///
/*	 private class StableArrayAdapter extends ArrayAdapter<String> {

		    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

		    public StableArrayAdapter(Context context, int textViewResourceId,
		        List<String> objects) {
		      super(context, textViewResourceId, objects);
		      for (int i = 0; i < objects.size(); ++i) {
		        mIdMap.put(objects.get(i), i);
		      }
		    }
		    
		    public StableArrayAdapter( int textViewResourceId,Context context,
			        List<ToDo> objects) {
			      super(context, textViewResourceId, objects);
			      for (int i = 0; i < objects.size(); ++i) {
			        mIdMap.put(objects.get(i), i);
			      }
			    }		    
		    @Override
		    public long getItemId(int position) {
		      String item = getItem(position);
		      return mIdMap.get(item);
		    }

		    @Override
		    public boolean hasStableIds() {
		      return true;
		    }

		  }*/
	 private class StableArrayAdapter extends BaseAdapter {

		 ArrayList<ToDo> toDoArray = new ArrayList<ToDo>();

		 /*   public StableArrayAdapter(Context context, int textViewResourceId,
		        List<String> objects) {
		      super(context, textViewResourceId, objects);
		      for (int i = 0; i < objects.size(); ++i) {
		        mIdMap.put(objects.get(i), i);
		      }
		    }*/
		    
		 public void addItem(ToDo item){
		    	 toDoArray.add(item);
		    }
		    @Override
		    public int getCount(){
		    	return toDoArray.size();
		    }
		    @Override
		    public ToDo getItem(int position) {
		      return toDoArray.get(position);
		    }
		    
		    @Override
		    public long getItemId(int position) {
		     // int item = getItem(position);
		      return position;
		    }

		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		    	View row;
		    	String title;
		    	LayoutInflater inflater=getLayoutInflater();
		    	TextView titleView;
		    	ToDo item=getItem(position);
	            row = inflater.inflate(R.layout.todorow, parent, false);
	            title=item.getNote();
	            titleView = (TextView) row.findViewById(R.id.title_row);
	            titleView.setText(title);
	            return row;

		    }

		  }

	 /**************************end of stabelarray class***************/
	
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
        List<ToDo> todoList = new ArrayList<ToDo>();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//get all to do
		todoList=db.getAllToDos();
		if (!todoList.isEmpty()){
			showToDos(todoList);
		}
	   	 /** Reference to the button of the layout main.xml */
       //Button btn = (Button) findViewById(R.id.Add);
       /** Defining a click event listener for the button "Add" */
       /*btn.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
							}
			
       });*/
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}
	}
	/*********on click btn*************/
	public void addToDo(View v) 
	{
		ToDo newToDO;
    	EditText edit = (EditText) findViewById(R.id.editText);
		String itemString=edit.getText().toString();

    	newToDO=new ToDo(itemString);
       ListView listOfItems = (ListView)findViewById(R.id.toDoList); 
	
	//	list.add(itemString);
		//adapter = new StableArrayAdapter(this, R.layout.todorow,list);
	//	adapter= new StableArrayAdapter();
		adapter.addItem(newToDO);
		//adapter.getView(position, convertView, parent)
		listOfItems.setAdapter(adapter);
		
		//add to database
		newToDO= new ToDo(itemString);
		db.createToDo(newToDO, 1);
		edit.setText("");				

	}	
	/***********************************/
	
	/***********help functions*************/
	
	public void showToDos(List<ToDo> todoList){
	    
		adapter = new StableArrayAdapter();
		ListView listOfItems = (ListView)findViewById(R.id.toDoList); 
		String title;
		for (ToDo td : todoList){
			adapter.addItem(td);
		}
		listOfItems.setAdapter(adapter);
	}
	  public void onFinish() {
	        // set the new Content of your activity
	        MainActivity.this.setContentView(R.layout.activity_main);
	
	  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		 getMenuInflater().inflate(R.menu.main, menu);
		 

			
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_main, container,
					false);
			return rootView;
		}
		
	}
	
}
		

	
	//add listenres
	

