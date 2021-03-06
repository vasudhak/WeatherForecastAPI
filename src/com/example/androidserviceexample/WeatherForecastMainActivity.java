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
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.androidserviceexample.models.Coord;
import com.example.androidserviceexample.models.CurrentConditions;
import com.example.androidserviceexample.models.Main;
import com.example.androidserviceexample.models.Weather;
import com.example.androidserviceexample.models.Wind;
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
	private TextView cityText;
	private TextView condDescr,hum,press,windSpeed,windDeg;
	private View detailsLayout;
	private String LOGTAG=this.getClass().getSimpleName();
	Weather weatherObj=new Weather();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weatherdetails);
		 cityText = (TextView) findViewById(R.id.cityText);
		 condDescr = (TextView) findViewById(R.id.condDescr);
	   detailsLayout=(RelativeLayout) findViewById(R.id.detailsLayout);
	    hum = (TextView) findViewById(R.id.hum);
	    press = (TextView) findViewById(R.id.press);
	    windSpeed = (TextView) findViewById(R.id.windSpeed);
	    windDeg = (TextView) findViewById(R.id.windDeg);
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
				Coord coord=new Coord();
				JSONObject coordinates = jObject.getJSONObject("coord");
				String latitude = coordinates.getString("lat");
				String longitude = coordinates.getString("lon");
				coord.setLat(latitude);
				coord.setLon(longitude);
				weatherObj.setCoord(coord);
								
				Main main=new Main();
				JSONObject mainObj = jObject.getJSONObject("main");
				main.setHumidity((float)mainObj.getDouble("humidity"));
				main.setPressure((float)mainObj.getDouble("pressure"));
				main.setTemp((float) mainObj.getDouble("temp"));
				weatherObj.setMain(main);

				Wind wind=new Wind();
				JSONObject windObj = jObject.getJSONObject("wind");
				wind.setDeg((float) windObj.getDouble("deg"));
				wind.setSpeed((float)windObj.getDouble("speed"));
				weatherObj.setWind(wind);
				
				CurrentConditions currentCondition=new CurrentConditions();
				JSONArray weather = jObject.getJSONArray("weather");
				JSONObject description = weather.getJSONObject(0);
				currentCondition.setId(description.getInt("id"));
				currentCondition.setDescription(description.getString("description"));
				currentCondition.setMain( description.getString("main"));
				weatherObj.setCurrentConditions(currentCondition);
				weatherObj.setName(jObject.getString("name"));
				updateUI(weatherObj);
				//textview.setText(latitude+" , "+longitude+" , "+humidity+" , "+pressure+" , "+temp+" , "+degrees+" , "+speed+" : "+desc+" : "+mai);
			}
			catch(Exception ex){
				if(ex.getMessage().equalsIgnoreCase("No value For Coord"))
					detailsLayout.setVisibility(View.GONE);
					textview.setVisibility(View.VISIBLE);
				textview.setText("Please check the name of the city you enterd !");
				Log.i(LOGTAG,"Exception  :"+ex.getMessage());
			}

		}

	}

void updateUI(Weather weatherObj){
	detailsLayout.setVisibility(View.VISIBLE);
	textview.setVisibility(View.INVISIBLE);
	cityText.setText(weatherObj.getName());
	condDescr.setText(weatherObj.getCurrentConditions().getDescription());
	hum.setText(Float.toString(weatherObj.getMain().getHumidity()));
	press.setText(Float.toString(weatherObj.getMain().getPressure()));
	windSpeed.setText(Float.toString(weatherObj.getWind().getSpeed()));
	windDeg.setText(Float.toString(weatherObj.getWind().getDeg()));
}



}
