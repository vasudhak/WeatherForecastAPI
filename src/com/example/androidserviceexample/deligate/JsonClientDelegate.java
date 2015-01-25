package com.example.androidserviceexample.deligate;

import com.example.androidserviceexample.webService.WebServiceException;
import com.example.androidserviceexample.webService.WebServiceResponseBase;

import android.content.Context;
import android.util.Log;

/**
 * The JsonClientDelegate class is used as the base callback class for all CDWebServiceClient methods.
 * This delegate will handle the deserialization of the response and error checking.  The lifecycle of the
 * methods are as follows:
 * <p>
 * <ul>
 * <li> OnRequestCompleted - Use this to access the raw json response string.
 * <li> OnRequestSucessful - Called after deserializing the json response string, and checking that no Fault is returned.
 * <li> OnRequestError - Called after deserializing the json response string, and finding a fault.
 * </ul>
 * <p>
 */
public class JsonClientDelegate<T extends WebServiceResponseBase> implements IJsonClientDelegate<T>
{
    private static final String LOG_TAG = "JsonClientDelegate";

    @SuppressWarnings("unused")
	private Context  _context;
    
    public JsonClientDelegate(Context context)
    {
        _context = context;
    }
    
    public void setContext(Context context)
    {
    	_context = context;
    }
    
    /**
     * Use this to access the raw json response string.
     */
    @Override
    public void OnRequestCompleted(T response)
    {
        if(response.webServiceException != null)
        {
        	OnRequestError(response.getException());
        }
    
            try
            {
            	OnRequestSuccessful(response);
            }
            catch(IllegalStateException exception)
            {
            	// This happens when the class that overrides the delegate method gets 
            	// destroyed and that method no longer exists.  TODO: Need to find a better way to handle this.
            	Log.w(LOG_TAG, "Illegal State Exception - OnRequestCompleted failed: "  + exception.getMessage() + " : " + exception.toString());
            }
        
    }

    /**
     * Called after deserializing the json response string, and checking that no Fault is returned.
     */
    @Override
    public void OnRequestSuccessful(T response)
    {
    }

    /**
     * Called after deserializing the json response string, and finding a fault.
     */
    @Override
    public void OnRequestError(WebServiceException error)
    {
    	
    }
    
    /**
	 * Run Logic in Background thread on calls completed. User is responsible for success and fault in this code.
	 */
	public void OnCompleteInBackground(T response)
	{
	}
	
}