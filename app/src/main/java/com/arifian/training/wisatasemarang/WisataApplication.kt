package com.arifian.training.wisatasemarang

import android.app.Activity
import android.app.Application

import com.arifian.training.wisatasemarang.models.remote.WisataClient
import com.arifian.training.wisatasemarang.models.remote.WisataService

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
