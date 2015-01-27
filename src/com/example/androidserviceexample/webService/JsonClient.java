package com.example.androidserviceexample.webService;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import android.os.AsyncTask;
import android.util.Log;

import com.example.androidserviceexample.deligate.JsonClientDelegate;
/**
 * Json CLient generic class which is used to make the async service calls and update the cache if any.
 * 
 * I takes the request base class and response base class generic types as parameters and executes the 
 * provided request object and stores the result to the response object .
 * 
 * By doing this i can have a delegate which will make use of the JsonClient and update the Response/UI after succesfull call.
 * 
 * */


public abstract class JsonClient<R extends WebServiceRequestBase, S extends WebServiceResponseBase>
{
  
   
    protected HttpPost mHttpPost;
    protected HttpGet mHttpGet;
    private JsonClientDelegate<S> mDelegate;
  
    private R mRequest;
    protected String mHeaderString = "";
    public JsonClient(final R request)
    {
        mRequest = request;
      
    }

    public abstract String getServiceUrl( R request );
    protected abstract void getHeaders(String url, R request);
    protected abstract void getContent( R request ) throws UnsupportedEncodingException;
    protected abstract HttpResponse execute() throws ClientProtocolException, IOException;
    
    public void sendRequestAsync(JsonClientDelegate<S> delegate)
    {
        mDelegate = delegate;
        SendRequestTask task = new SendRequestTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Object());
    }

    public S sendRequest(Class<S> clazz) 
    {
    	 S response = runCall(mRequest);
         if(response.webServiceException == null)
         {
            
           //response is good to parse, if i have to update any response which is previously cached i would do it here.
        }    
   
		 else
         {
        	//i have to throw an exception .
         }
         return response;
    }
    
    @SuppressWarnings("unchecked")
	private S runCall(R request) 
    {
    	String jsonResponse = null;
        HttpResponse httpResponse = null;
        S response = null;
        try
        {
            //Make Request Call
        	String postUrl = getServiceUrl(request);
            getHeaders(postUrl, request);
            getContent( request );
            httpResponse = execute();  
            
           
            //Create Response String
            BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for(String line = null; (line = reader.readLine()) != null;)
            {
                builder.append(line).append("\n");
            }
           
            //Create JSON String
            jsonResponse = builder.toString();
  
           
            if(jsonResponse == null || jsonResponse.isEmpty())
            {
                throw new Exception("jsonResponse is null!");
            }
            
            //Parse JSON 
            if( jsonResponse.startsWith("{}") == false )
            {
            	response = (S) request.parseResponse(jsonResponse);
            }
            else//correct empty JSON Responses
            {
            	response = (S) request.getEmptyResponse();
            }            
        }
        catch(IOException io)
        {        	
        	
            @SuppressWarnings("unused")
			WebServiceException error = new WebServiceException("Network connection not available.", WebServiceException.WebServiceExceptionType.NetworkNotAvailableException);
            
            //throw error;
        }
//        catch(WebServiceException we)
//        {
//        	throw we;
//        }
//        catch(JsonParseException e)
//        {
//            WebServiceException	error = new WebServiceException("GSON JSON parsing exception", CDWebServiceExceptionType.JSONException);
//         
//            throw error;
//        }
        catch(Exception e)
        {
           @SuppressWarnings("unused")
		WebServiceException	error = new WebServiceException("Unknown error occured in network stack.", WebServiceException.WebServiceExceptionType.GeneralException);
          
           Log.e("Exception", "Error making call: " + e.getMessage() + " : " + e.toString(), e);
          
        }
        
        return response;
    }
    
   
  
    private class SendRequestTask extends AsyncTask<Object, WebServiceException, S>
    {
        @Override
        protected S doInBackground(Object... arg0)
        {
            S response = null;
            Thread.currentThread().setName(mRequest.RequestName);
            try
            {
                response = runCall(mRequest);
            }
            catch(Exception we)
            {
            	this.cancel(false);
                WebServiceException error = new WebServiceException(we.getLocalizedMessage());                
                publishProgress(error);
                this.cancel(false);
            }

            return response;
        }

        @Override
        protected void onProgressUpdate(WebServiceException... errorArr)
        {
        	if(mDelegate != null)
        	{
        		mDelegate.OnRequestError(errorArr[0]);
        	}
        }

        @Override
        protected void onPostExecute(S response)
        {          
            if(mDelegate != null)
            {
            	mDelegate.OnRequestCompleted(response);
            }
        }
    }

}