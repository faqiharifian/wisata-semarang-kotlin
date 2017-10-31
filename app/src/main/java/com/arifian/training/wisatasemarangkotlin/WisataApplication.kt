package com.arifian.training.wisatasemarangkotlin

import android.app.Activity
import android.app.Application

import com.arifian.training.wisatasemarangkotlin.models.remote.WisataClient
import com.arifian.training.wisatasemarangkotlin.models.remote.WisataService

/**
 * Created by faqih on 30/10/17.
 */

class WisataApplication : Application() {
    internal var wisataService: WisataService? = null

    fun getService(context: Activity): WisataService {
        if (wisataService == null) {
            wisataService = WisataClient.getClient(context)
        }
        return wisataService!!
    }

    companion object {

        operator fun get(context: Activity): WisataApplication {
            return context.application as WisataApplication
        }
    }
}
