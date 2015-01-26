package com.example.androidserviceexample;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * List Activity class which holds the list of all cities for which we can make a weatherforecast request.
 * 
 * <p>
 *   Add city button will enable the option to add another city to view the weather.
 *	 LongPress of the List item will enable the property to delete the list element.
 *   onClicking any of the list items will take you to another activity displaying all the weather results.
 * </p>
 * 
 * */

@SuppressLint("InflateParams")
public class ListActivity extends Activity {
	ListView citiesListView;
	Button addCity;
	final Context context=this;
	ArrayList<String> cityList=new ArrayList<String>();

	
	ArrayAdapter<String> stringAdapter ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		cityList.add("Boston");
		cityList.add("NewYork");
		cityList.add("California");
		stringAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, cityList);
		setContentView(R.layout.activity_list);
		addCity=(Button) findViewById(R.id.addCityButton);
		addCity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.prompt, null);
 
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
 
				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);
 
				final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
 
				// set dialog message
				alertDialogBuilder.setCancelable(false).setPositiveButton("OK",new DialogInterface.OnClickListener() 
				{
					    public void onClick(DialogInterface dialog,int id)
					    {
					    	cityList.add(userInput.getText().toString());
					    	stringAdapter.notifyDataSetChanged();
					    	Intent intent=new Intent(getApplicationContext(),WeatherForecastMainActivity.class);
							Log.i("CityName","name passed in listactivity"+userInput.getText().toString());							
							intent.putExtra("cityName", userInput.getText().toString());
							startActivity(intent);
					    }
					  })
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });
 
				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();
				alertDialog.show();
 
			}
		});

		citiesListView = (ListView) findViewById(R.id.listview);		
		citiesListView.setAdapter(stringAdapter);
		citiesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				Intent intent=new Intent(getApplicationContext(),WeatherForecastMainActivity.class);
				Log.i("CityName","name passed in listactivity"+arg0.getItemAtPosition(position).toString());
				
				intent.putExtra("cityName", arg0.getItemAtPosition(position).toString());
				startActivity(intent);
			}
		});
		citiesListView.setLongClickable(true);
		//set the long click listener to delete a city from list.
		citiesListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				final int itemPosition=position;
				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				builder.setMessage("Are you sure you want to delete "+parent.getItemAtPosition(position).toString()+"?")
				   .setCancelable(false)
				   .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				       public void onClick(DialogInterface dialog, int id) {
				          cityList.remove(itemPosition);
				          stringAdapter.notifyDataSetChanged();
				          
				       }
				   })
				   .setNegativeButton("No", new DialogInterface.OnClickListener() {
				       public void onClick(DialogInterface dialog, int id) {
				            dialog.cancel();
				       }
				   });
				AlertDialog alert = builder.create();
				alert.show();
				return true;
			
			
			}
		});
	}	
		
	
		  

}
