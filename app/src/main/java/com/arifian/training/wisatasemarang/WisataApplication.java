package com.arifian.training.wisatasemarang;

import android.app.Activity;
import android.app.Application;

import com.arifian.training.wisatasemarang.models.remote.WisataClient;
import com.arifian.training.wisatasemarang.models.remote.WisataService;

/**
 * Created by faqih on 30/10/17.
 */

public class WisataApplication extends Application {
    WisataService wisataService;

    public static WisataApplication get(Activity context){
        return (WisataApplication) context.getApplication();
    }

    public WisataService getService(Activity context){
        if(wisataService == null){
            wisataService = WisataClient.getClient(context);
        }
        return wisataService;
    }
}
