package com.example.androidserviceexample.webService;



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