package com.arifian.training.wisatasemarangkotlin.drawroutemap;

import android.content.Context;

import com.arifian.training.wisatasemarangkotlin.drawroutemap.models.Response;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

/**
 * Created by ocittwo on 11/14/16.
 *
 * @Author Ahmad Rosid
 * @Email ocittwo@gmail.com
 * @Github https://github.com/ar-android
 * @Web http://ahmadrosid.com
 */

public class DrawRouteMaps {

    private static DrawRouteMaps instance;
    private Context context;
    private Response response;
    OnLoadFinishedListener listener;

    public static DrawRouteMaps getInstance(Context context) {
        instance = new DrawRouteMaps();
        instance.context = context;
        return instance;
    }

    public DrawRouteMaps draw(LatLng origin, LatLng destination, GoogleMap googleMap, OnLoadFinishedListener listener){
        String url_route = FetchUrl.getUrl(origin, destination);
        DrawRoute drawRoute = new DrawRoute(googleMap, json -> {
            Gson gson = new Gson();
            response = gson.fromJson(json, Response.class);
            listener.onLoadFinish(response);
        });
        drawRoute.execute(url_route);
        return instance;
    }

    public static Context getContext() {
        return instance.context;
    }

    public interface OnLoadFinishedListener{
        void onLoadFinish(Response response);
    }
}
