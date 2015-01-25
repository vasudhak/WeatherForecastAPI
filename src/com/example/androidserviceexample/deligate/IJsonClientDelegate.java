package com.example.androidserviceexample.deligate;

import com.example.androidserviceexample.webService.WebServiceException;
import com.example.androidserviceexample.webService.WebServiceResponseBase;

public interface IJsonClientDelegate<S extends WebServiceResponseBase>
{
	void OnRequestCompleted(S reponse);
    void OnRequestSuccessful(S response);
    void OnRequestError(WebServiceException error);
}