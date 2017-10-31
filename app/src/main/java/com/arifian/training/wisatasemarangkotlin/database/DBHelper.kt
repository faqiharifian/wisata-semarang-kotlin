package com.arifian.training.wisatasemarangkotlin.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.arifian.training.wisatasemarangkotlin.models.Wisata

/**
 * Created by faqih on 31/10/17.
 */
class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    companion object {
        private val DATABASE_NAME = "dbwisata"
        private val DATABASE_TABLE = "table_wisata"
        private val WISATA_ID = "_id"
        private val NAMA_WISATA = "nama_wisata"
        private val GAMBAR_WISATA = "gambar_wisata"
        private val ALAMAT_WISATA = "alamat_wisata"
        private val DESKRIPSI_WISATA = "deskripsi_wisata"
        private val LATITUDE_WISATA = "latitude_wisata"
        private val LONGITUDE_WISATA = "longitude_wisata"
        private val DATABASE_VERSION = 1

        private val CREATE_TABLE = ("CREATE TABLE " + DATABASE_TABLE
                + " (" + WISATA_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, "
                + NAMA_WISATA + " VARCHAR(200), "
                + GAMBAR_WISATA + " VARCHAR(200), "
                + ALAMAT_WISATA + " TEXT, "
                + DESKRIPSI_WISATA + " TEXT, "
                + LATITUDE_WISATA + " VARCHAR(20), "
                + LONGITUDE_WISATA + " VARCHAR(20));")

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS "+ DATABASE_NAME)
        onCreate(db)
    }

    fun insert(wisata: Wisata): Long{
        val cv = ContentValues()
        cv.put(NAMA_WISATA, wisata.namaWisata)
        cv.put(GAMBAR_WISATA, wisata.gambarWisata)
        cv.put(ALAMAT_WISATA, wisata.alamatWisata)
        cv.put(DESKRIPSI_WISATA, wisata.deksripsiWisata)
        cv.put(LATITUDE_WISATA, wisata.latitudeWisata)
        cv.put(LONGITUDE_WISATA, wisata.longitudeWisata)

        val db = writableDatabase
        val result = db.insert(DATABASE_TABLE, null, cv)
        db.close()
        return result
    }

    fun delete(wisata: Wisata): Int{
        val clause = NAMA_WISATA+" = ?"
        val args = Array(1, {wisata.namaWisata})

        val db = this.writableDatabase
        val count = db.delete(DATABASE_TABLE, clause, args)
        db.close()
        return count
    }
}