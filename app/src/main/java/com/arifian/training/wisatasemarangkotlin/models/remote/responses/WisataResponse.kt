package com.arifian.training.wisatasemarangkotlin.models.remote.responses

import com.arifian.training.wisatasemarangkotlin.models.Wisata
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by faqih on 30/10/17.
 */

class WisataResponse : BaseResponse() {
    @SerializedName("wisata")
    @Expose
    var wisata: List<Wisata>? = null

}
