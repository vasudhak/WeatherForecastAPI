package com.example.androidserviceexample.webService;


/**
 * The Response data that can be optionally returned by all made request.
 */
public class WebServiceResponseBase 
{
	/**
	 * Default constructor.
	 */
	public WebServiceResponseBase()
    {
		//create a default response base model object if any required.
    }
	
	/**
	 * The name of the service method.
	 */
    public String ServiceName;
    public WebServiceException webServiceException;
   
    public WebServiceException getException()
    {
        return webServiceException;
    }
    
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("ServiceName: " + ServiceName + "\n");
        if(getException() != null)
        {
            builder.append(getException().toString());
        }

        return builder.toString();
    }
}