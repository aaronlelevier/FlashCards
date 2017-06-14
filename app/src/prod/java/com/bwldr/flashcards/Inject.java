package com.bwldr.flashcards;

import com.bwldr.flashcards.api.ApiClient;
import com.bwldr.flashcards.api.ServiceGenerator;


public class Inject {

    private static ApiClient mApiClient = null;

    private Inject() {
    }

    public synchronized static ApiClient getApiClient() {
        if (mApiClient == null) {
            mApiClient = ServiceGenerator.createService(ApiClient.class);
        }
        return mApiClient;
    }
}
