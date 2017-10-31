package com.arifian.training.wisatasemarangkotlin

import android.content.Context
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.arifian.training.wisatasemarangkotlin.Utils.Constants.Companion.KEY_WISATA
import com.arifian.training.wisatasemarangkotlin.Utils.Constants.Companion.PREF_FAVORITE
import com.arifian.training.wisatasemarangkotlin.Utils.GlideApp
import com.arifian.training.wisatasemarangkotlin.database.DBHelper
import com.arifian.training.wisatasemarangkotlin.databinding.ActivityDetailWisataBinding
import com.arifian.training.wisatasemarangkotlin.models.Wisata
import com.arifian.training.wisatasemarangkotlin.models.remote.WisataClient
import org.parceler.Parcels

class DetailWisataActivity : AppCompatActivity() {

    internal lateinit var mBinding: ActivityDetailWisataBinding
    internal lateinit var wisata: Wisata

    internal var favorite: Boolean = false
    internal var pref: SharedPreferences? = null

    internal lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wisata = Parcels.unwrap(intent.getParcelableExtra<Parcelable>(KEY_WISATA))
        pref = getSharedPreferences(PREF_FAVORITE, Context.MODE_PRIVATE)
        db = DBHelper(this)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_wisata)
        setSupportActionBar(mBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle(wisata.namaWisata)

        mBinding.wisata = wisata

        favorite = pref!!.getBoolean(PREF_FAVORITE +wisata.namaWisata, false)

        GlideApp.with(this)
                .load(WisataClient.IMAGE_URL + wisata.gambarWisata!!)
                .centerCrop()
                .into(mBinding.ivDetailGambar)

        checkFavorite()

        mBinding.fab.setOnClickListener { view ->
            val message: String
            if(!favorite) {
                var id = db.insert(wisata)
                if (id <= 0) {
                    message = "Favorite gagal ditambahkan ke database"
                } else {
                    message = "Favorite ditambahkan ke database"

                    updateFavorite()
                }
            }else{
                val count = db.delete(wisata)
                if(count <= 0){
                    message = "Favorite gagal dihapus dr database"
                }else{
                    message = "Favorite dihapus dr database"

                    updateFavorite()
                }
            }
            Snackbar.make(mBinding.fab, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun updateFavorite(){
        favorite = !favorite

        val editor = pref!!.edit()
        editor.putBoolean(PREF_FAVORITE + wisata.namaWisata, favorite)
        editor.apply()

        checkFavorite()
    }

    private fun checkFavorite() {
        if (favorite)
            mBinding.fab.setImageResource(R.drawable.ic_action_favorite_true)
        else
            mBinding.fab.setImageResource(R.drawable.ic_action_favorite_false)
    }
}
