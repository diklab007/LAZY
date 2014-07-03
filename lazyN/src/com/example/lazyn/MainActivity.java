package com.example.lazyn;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.Inflater;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
 

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

import com.sqlite.helper.*;
import com.example.lazyn.R;


public class MainActivity extends ActionBarActivity {
	StableArrayAdapter adapter;
	DatabaseHelper db= new DatabaseHelper(this);
	/******************************stableArrayAdapterclass*****************///
	 private class StableArrayAdapter extends BaseAdapter {

		 ArrayList<ToDo> toDoArray = new ArrayList<ToDo>();

		 public void addItem(ToDo item){
		    	 toDoArray.add(item);
		    }
		    @Override
		    public int getCount(){
		    	if(toDoArray.isEmpty()){
		    		return 0;
		    	}

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
		    	MyTag tag;
		    	ToDo td;
		    	View row;
		    	String title;
		    	LayoutInflater inflater=getLayoutInflater();
		    	TextView titleView;
		    	ImageView deleteButton;
		    	ToDo item=getItem(position);
		    	item.setArrayId(position);
	            row = inflater.inflate(R.layout.todorow, parent, false);
	            title=item.getNote();
	            titleView = (TextView) row.findViewById(R.id.title_row);
	            titleView.setText(title);
	            td=getItem(position);
	            tag= new MyTag(position, (td.getId())); 
	            row.setTag(tag);
	        
	            deleteButton= (ImageView) row.findViewById(R.id.remove);
	            deleteButton.setOnClickListener(
	                    new Button.OnClickListener() {
	                        @Override
	                        public void onClick(View v) {
	                        	View parent;
	                        	parent=(View) v.getParent();
	                            MyTag mTag =  (MyTag) parent.getTag();
	                            toDoArray.remove(mTag.getPosition());
	                            db.deleteToDo(mTag.getId());
	                            notifyDataSetChanged();	                            
	                        }
	                    }
	                );
	            
	            titleView.setOnClickListener(
	            		new Button.OnClickListener() {
	            			@Override
	            			public void onClick(View v){
	            				
	            		        startActivity(new Intent(getApplicationContext(), ToDoDetails.class));

	            			}
	            });
	            return row;

		    }

		  }

	 /**************************end of stabelarray class***************/
	
	 @Override
	protected void onCreate(Bundle savedInstanceState) {

        List<ToDo> todoList = new ArrayList<ToDo>();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		adapter = new StableArrayAdapter();
		//adding fragment
		addTmFrag();
		//get all to do
		todoList=db.getAllToDos();
		if (!todoList.isEmpty()){
			showToDos(todoList);
		}
		if (savedInstanceState == null) {
	        Log.v("bundle", "null");
		}
		
	 }

	//adding tamaguchi fragment
 public void addTmFrag(){
				          Fragment fr;
				           fr = new tamaguchiFragment();
				          FragmentManager fm = getFragmentManager();
				          android.app.FragmentTransaction fragmentTransaction = fm.beginTransaction();
				          fragmentTransaction.replace(R.id.tamaguchi_fragment, fr);
				          happyTurtule();
				          fragmentTransaction.commit();
}
 
 //play happy turtule animation
public void happyTurtule(){
	 // Load the ImageView that will host the animation and
	 // set its background to our AnimationDrawable XML resource.
	 ImageView img = (ImageView)findViewById(R.id.tmImage);
	 img.setAdjustViewBounds(true);
	 img.setScaleType(ScaleType.FIT_CENTER);

	// img.setBackgroundResource(R.drawable.happy);
	 img.setImageDrawable(getResources().getDrawable(R.drawable.happy)); 

	 // Get the background, which has been compiled to an AnimationDrawable object.
	 AnimationDrawable frameAnimation = (AnimationDrawable) img.getDrawable();

	 // Start the animation (looped playback by default).
	 frameAnimation.start();
}
	/*********on click btn*************/
	public void addToDo(View v) 
	{
		int toDoId;
		ToDo newToDO;
    	EditText edit = (EditText) findViewById(R.id.editText);
    	String itemString=edit.getText().toString();
		int positionInArray=adapter.getCount();
    	newToDO=new ToDo(itemString, positionInArray);
    	ListView listOfItems = (ListView)findViewById(R.id.toDoList); 
		adapter.addItem(newToDO);
		//adapter.getView(position, convertView, parent)
		listOfItems.setAdapter(adapter);
		//add to database
		toDoId=(int) db.createToDo(newToDO, 1);
		newToDO.setId(toDoId);
		edit.setText("");			
		
		//moveToAnotherActivity();
	}	

//	private void moveToAnotherActivity() {
	//	Intent goToNextActivity = new Intent(getApplicationContext(), FragmentActivity.class);
		//startActivity(goToNextActivity);		
	//}
	/***********************************/
	
	/***********help functions*************/
	
	public void showToDos(List<ToDo> todoList){
	    
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
	

