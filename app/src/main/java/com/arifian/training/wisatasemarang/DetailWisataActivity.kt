package com.arifian.training.wisatasemarang

import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import com.arifian.training.wisatasemarang.Utils.GlideApp
import com.arifian.training.wisatasemarang.databinding.ActivityDetailWisataBinding
import com.arifian.training.wisatasemarang.models.Wisata
import com.arifian.training.wisatasemarang.models.remote.WisataClient

import org.parceler.Parcels

class DetailWisataActivity : AppCompatActivity() {

    internal lateinit var mBinding: ActivityDetailWisataBinding
    internal lateinit var wisata: Wisata

    internal var favorite: Boolean = false
    internal var pref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wisata = Parcels.unwrap(intent.getParcelableExtra<Parcelable>(KEY_WISATA))

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_wisata)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(wisata.namaWisata)

        mBinding.wisata = wisata

        GlideApp.with(this)
                .load(WisataClient.IMAGE_URL + wisata.gambarWisata!!)
                .centerCrop()
                .into(mBinding.ivDetailGambar)

        checkFavorite()

        mBinding.fab.setOnClickListener { view ->
            favorite = !favorite
            checkFavorite()

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun checkFavorite() {
        if (favorite)
            mBinding.fab.setImageResource(R.drawable.ic_action_favorite_true)
        else
            mBinding.fab.setImageResource(R.drawable.ic_action_favorite_false)
    }

    companion object {
        val KEY_WISATA = "wisata"
    }
}
