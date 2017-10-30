package com.arifian.training.wisatasemarang.models.remote;

import android.content.Context;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by faqih on 30/10/17.
 */

public class WisataClient {
    public static final String BASE_URL = "http://52.187.117.60/wisata_semarang/wisata/";
    public static final String IMAGE_URL = "http://52.187.117.60/wisata_semarang/img/wisata/";

    private static Retrofit retrofit = null;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static WisataService getClient(Context context) {
        if (retrofit==null) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }
        return retrofit.create(WisataService.class);
    }
}
