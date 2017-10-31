package com.arifian.training.wisatasemarangkotlin.models.remote

import com.arifian.training.wisatasemarangkotlin.models.remote.responses.WisataResponse

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by faqih on 30/10/17.
 */

interface WisataService {
    @get:GET("read_wisata.php")
    val wisata: Call<WisataResponse>
}
