package com.example.androidserviceexample.deligate;

import android.content.Context;

import com.example.androidserviceexample.webService.WebServiceException;
import com.example.androidserviceexample.webService.WebServiceResponseBase;

public class PresenterJsonClientDelegate<T extends WebServiceResponseBase>
		extends JsonClientDelegate<T> {
	@SuppressWarnings("unused")
	private static String LOG_TAG = PresenterJsonClientDelegate.class.getSimpleName();
	public PresenterJsonClientDelegate(Context context) {
		super(context);
		
	}

	public void OnRequestSuccessful(T response) {
	
			handleSuccessfulResponse(response);
		
	};

	protected void handleSuccessfulResponse(T response) {
		if (response.getException() != null)
			OnRequestError(response.getException());
	}

	@Override
	public void OnCompleteInBackground(T response) {
		super.OnCompleteInBackground(response);
	};

	@Override
	public void OnRequestError(WebServiceException error) 
	{
		super.OnRequestError(error);
	}

	
}
