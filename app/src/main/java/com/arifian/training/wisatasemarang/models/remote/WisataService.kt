package com.arifian.training.wisatasemarang.models.remote

import com.arifian.training.wisatasemarang.models.remote.responses.WisataResponse

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by faqih on 30/10/17.
 */

interface WisataService {
    @get:GET("read_wisata.php")
    val wisata: Call<WisataResponse>
}
