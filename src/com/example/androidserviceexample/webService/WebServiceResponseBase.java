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
		
    }
	
	/**
	 * The name of the service method.
	 */
    public String ServiceName;
    public WebServiceException webServiceException;
    //public Integer mFault;
    
    /*
     * If the request resulted in an error, this field will be populated with the details of the error.
	 * If the request was successful, this field will be null.
     */
   
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