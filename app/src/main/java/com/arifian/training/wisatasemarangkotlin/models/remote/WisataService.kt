package com.arifian.training.wisatasemarangkotlin.models.remote

import com.arifian.training.wisatasemarangkotlin.models.remote.responses.BaseResponse
import com.arifian.training.wisatasemarangkotlin.models.remote.responses.WisataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


/**
 * Created by faqih on 30/10/17.
 */

interface WisataService {
    @get:GET("read_wisata.php")
    val wisata: Call<WisataResponse>

    @Multipart
    @POST("create_wisata.php")
    fun wisataPost(@Part file: MultipartBody.Part,
                   @Part("nama_wisata") nama_wisata: RequestBody,
                   @Part("gambar_wisata") gambar_wisata: RequestBody,
                   @Part("deksripsi_wisata") deksripsi_wisata: RequestBody,
                   @Part("event_wisata") event_wisata: RequestBody,
                   @Part("latitude_wisata") latitude_wisata: RequestBody,
                   @Part("longitude_wisata") longitude_wisata: RequestBody,
                   @Part("alamat_wisata") alamat_wisata: RequestBody): Call<BaseResponse>
}
