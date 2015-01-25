package com.example.androidserviceexample.webService;


public class WebServiceException {


	/**
     * The Enum ExceptionType.
     */
    public static enum WebServiceExceptionType
    {
    	GeneralException,
        NetworkNotAvailableException,
        MaintenanceException, 
        ResourceNotFound,
        JSONException,
        TechnicalDifficultiesException
    }
    
    public WebServiceExceptionType exceptionType;
 
    public String message;
   
 
    public WebServiceException(Exception newOriginalException) {
		exceptionType = WebServiceExceptionType.GeneralException;
		
	}
    
    public WebServiceException(String message) {
    	this(message, WebServiceExceptionType.GeneralException);
	}
    
    public WebServiceException(String message, WebServiceExceptionType type) {
		this.message = message;
		exceptionType = type;
		
	}

	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString() + "\n");
        builder.append("exceptionType: " + exceptionType.name() + "\n");
        return builder.toString();
    }
}
