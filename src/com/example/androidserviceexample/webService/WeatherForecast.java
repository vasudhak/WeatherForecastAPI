package com.example.androidserviceexample.webService;

import com.example.androidserviceexample.deligate.PresenterJsonClientDelegate;



public class WeatherForecast {
	private static String SERVICE_NAME = "WeatherForecast";
	
	public static WeatherForecast.Request createEmptyRequest()
	{
		return new WeatherForecast().new Request();
	}
	
	public class Request extends WebServiceRequestBase
	{
		public Request()
		{
			super(SERVICE_NAME);
		}
		
		public Response getEmptyResponse()
		{
			return new Response();
		}
		
	    @Override 
	    public WebServiceResponseBase parseResponse(String jsonResponse)
	    {
	    	return new WebServiceResponseParser<WeatherForecast.Response>(WeatherForecast.Response.class).fromJSONResponse(jsonResponse);
	    }
	    
	    public String cityName;
	}
	
	
	@SuppressWarnings({ "unused" })	
	private void getWeatherForecast(WeatherForecast.Request request, PresenterJsonClientDelegate<WeatherForecast.Response> delegate)
	{
	 // JsonClient client= new JsonClient<WeatherForecast.Request, WeatherForecast.Response>(request);
	 //  client.sendRequestAsync(delegate);
	}
	
	public class Response extends WebServiceResponseBase
	{
        /**
         * Default constructor.
         */
        public Response()
        {
            ServiceName = SERVICE_NAME;
        }
	}
}