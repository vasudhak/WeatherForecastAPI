package com.example.androidserviceexample;

import org.json.JSONArray;
import org.json.JSONObject;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.androidserviceexample.service.WeatherForecastIntentService;
/**
 * WeatherForecastMainActivity is used to create and start the service for weatherForecastIntentService.
 * 
 * A broadCast Reciever is created to listen to the IntentService and display all the results recieved.
 * 
 * UI can be enhanced as i am focusing more on implementation structure rather than the UI of edit texts.
 * 
 * */

public class WeatherForecastMainActivity extends Activity {

	private MyReceiver mReceiver;
	private TextView textview;
	private Intent intent;
	private String LOGTAG=this.getClass().getSimpleName();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent i=getIntent();
		String city=i.getStringExtra("cityName");
		textview =  (TextView) findViewById(R.id.textview);
		mReceiver = new MyReceiver();
		intent = new Intent(WeatherForecastMainActivity.this,WeatherForecastIntentService.class);
		intent.putExtra("URL", getServiceURl(getResources().getString(R.string.webServiceURL),city));
		intent.putExtra("Action", "MainActivity");
		startService(intent);
		
	}

	public String getServiceURl(String url,String cityName){
		String serviceDomain = url;
        return serviceDomain + cityName;
	}


	@Override
	protected void onResume() {
		registerReceiver(mReceiver, new IntentFilter("MainActivity"));
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}

	class MyReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {


			String response = intent.getStringExtra("Key");
			

			try{

				JSONObject jObject = new JSONObject(response);
				JSONObject coordinates = jObject.getJSONObject("coord");
				String latitude = coordinates.getString("lat");
				String longitude = coordinates.getString("lon");

				JSONObject main = jObject.getJSONObject("main");

				String humidity = main.getString("humidity");
				String pressure = main.getString("pressure");
				String temp = main.getString("temp");

				JSONObject wind = jObject.getJSONObject("wind");

				String degrees = wind.getString("deg");
				String speed = wind.getString("speed");

				JSONArray weather = jObject.getJSONArray("weather");
				JSONObject description = weather.getJSONObject(0);
				String  desc = description.getString("description");
				String mai = description.getString("main");

				textview.setText(latitude+" , "+longitude+" , "+humidity+" , "+pressure+" , "+temp+" , "+degrees+" , "+speed+" : "+desc+" : "+mai);
			}
			catch(Exception ex){
				if(ex.getMessage().equalsIgnoreCase("No value For Coord"))
				textview.setText("Please check the name of the city you enterd !");
				Log.i(LOGTAG,"Exception  :"+ex.getMessage());
			}

		}

	}





}
