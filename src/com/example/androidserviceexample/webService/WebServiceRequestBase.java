package com.example.androidserviceexample.webService;


/**
 * All JSON requests inherit from this request.
 * Defines the base request and drives all request, session, and security validation.
 * The occurrence of a returned response indicates a successful request to the web service - all error conditions will raise a Fault.
 */
public class WebServiceRequestBase
{
	String mRequestName;
	/**
	 * Default constructor.
	 * @param requestName
	 */
    public WebServiceRequestBase(String requestName)
    {
    	mRequestName = requestName;
    }
	
    public WebServiceResponseBase getEmptyResponse()
    {
    	return new WebServiceResponseBase();
    }
    
    public WebServiceResponseBase parseResponse(String jsonResponse)
    {
    	return new WebServiceResponseParser<WebServiceResponseBase>(WebServiceResponseBase.class).fromJSONResponse(jsonResponse);
    }
    
    /**
     * The name of the request method.
     */
    public String RequestName;
    
    public String getRequestKey()
    {
    	return this.RequestName;
    }
    
    /**
     * Used for web service calls to create the Restful URL
     * @return
     */
    public String GetRequestUrl(){return "";};
    
   
    public String url;
     
}