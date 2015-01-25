package com.example.androidserviceexample.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
/**
 * WeatherForecastIntentService class is a IntentService used to make the Service calls to the Weather Url provided
 * it will make the webservice call and Broadcast the data recieved to the WeatherForecastMainActivity Broadcaster.
 * 
 * */
public class WeatherForecastIntentService  extends IntentService{
	
	private final String LOG_TAG= this.getClass().getSimpleName();
	
	public WeatherForecastIntentService() {
		super("");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
		try {
			URL url = new URL(intent.getStringExtra("URL"));
			Log.e(LOG_TAG,"Response code is :"+url);
			
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			Log.i(LOG_TAG,"Response code is :"+urlConnection.getResponseCode());
			
			String response = convert_To_String(urlConnection.getInputStream());
			
			Log.i(LOG_TAG,"Response is :"+response);
			
			Intent i = new Intent(intent.getStringExtra("Action"));
			i.putExtra("Key", response);
			sendBroadcast(i);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			Log.i(LOG_TAG,"iam in exception");
		}
		
	}

	private   String convert_To_String(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {

				sb.append(line + "\n");
				Log.i(LOG_TAG, "Line is  :" + line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {                 
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}     



	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i(LOG_TAG,"onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onDestroy() {
		Log.i(LOG_TAG,"onDestroy");
		super.onDestroy();
	}

}
