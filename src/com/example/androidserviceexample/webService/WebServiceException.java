package com.example.androidserviceexample.webService;


public class WebServiceException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
    public Integer severityCode;
    public String faultType;
    public Number faultCode;
    public String message;
   
 
    public WebServiceException(Exception newOriginalException) {
		exceptionType = WebServiceExceptionType.GeneralException;
		faultType = "None";
	}
    
    public WebServiceException(String message) {
    	this(message, WebServiceExceptionType.GeneralException);
	}
    
    public WebServiceException(String message, WebServiceExceptionType type) {
		this.message = message;
		exceptionType = type;
		faultType = "None";
	}

	@Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString() + "\n");
        builder.append("exceptionType: " + exceptionType.name() + "\n");
        if(severityCode != null){
        	builder.append("severityCode: "+severityCode.toString());
        }else{
        	builder.append("severityCode: null\n");
        }
        builder.append("faultType: " + faultType + "\n");
        if(faultCode != null){
        	builder.append("faultCode: "+faultCode.toString());
        }else{
        	builder.append("faultCode: null\n");
        }
        return builder.toString();
    }
}
