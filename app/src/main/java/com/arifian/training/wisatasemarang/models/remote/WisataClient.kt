package com.arifian.training.wisatasemarang.models.remote

import android.content.Context

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by faqih on 30/10/17.
 */

object WisataClient {
    val BASE_URL = "http://52.187.117.60/wisata_semarang/wisata/"
    val IMAGE_URL = "http://52.187.117.60/wisata_semarang/img/wisata/"

    private var retrofit: Retrofit? = null

    private val logging = HttpLoggingInterceptor()
    private val httpClient = OkHttpClient.Builder()

    fun getClient(context: Context): WisataService {
        if (retrofit == null) {
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build()
        }
        return retrofit!!.create(WisataService::class.java!!)
    }
}
