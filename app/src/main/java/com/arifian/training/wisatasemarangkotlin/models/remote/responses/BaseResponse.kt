package com.arifian.training.wisatasemarangkotlin.models.remote.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by faqih on 30/10/17.
 */

open class BaseResponse {
    @SerializedName("message")
    @Expose
    var message: String = ""
    @SerializedName("success")
    @Expose
    var success: Boolean? = false
}
