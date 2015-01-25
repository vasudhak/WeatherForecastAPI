package com.example.androidserviceexample.webService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WebServiceResponseParser<S extends WebServiceResponseBase>
{
	private Class<S> mResponseClass;
	
	public WebServiceResponseParser(Class<S> responseClass) {
		mResponseClass = responseClass;
	}
	
    public S fromJSONResponse(String json)
    {
    
        Gson gson = new GsonBuilder().create();
        
        S response = (S) gson.fromJson(json, mResponseClass);
        
        return response;
    }
    
    protected WebServiceResponseParser<S> clone()
    {
    	return new WebServiceResponseParser<S>(mResponseClass);
    }
}