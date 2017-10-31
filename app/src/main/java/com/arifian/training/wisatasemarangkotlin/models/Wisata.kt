package com.arifian.training.wisatasemarangkotlin.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import org.parceler.Parcel

/**
 * Created by faqih on 30/10/17.
 */

@Parcel
class Wisata {
    @SerializedName("id_wisata")
    @Expose
    var idWisata: String? = null
    @SerializedName("nama_wisata")
    @Expose
    var namaWisata: String? = null
    @SerializedName("gambar_wisata")
    @Expose
    var gambarWisata: String? = null
    @SerializedName("deksripsi_wisata")
    @Expose
    var deksripsiWisata: String? = null
    @SerializedName("alamat_wisata")
    @Expose
    var alamatWisata: String? = null
    @SerializedName("event_wisata")
    @Expose
    var eventWisata: String? = null
    @SerializedName("latitude_wisata")
    @Expose
    var latitudeWisata: String? = null
    @SerializedName("longitude_wisata")
    @Expose
    var longitudeWisata: String? = null
}
