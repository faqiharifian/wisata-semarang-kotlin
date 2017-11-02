package com.arifian.training.wisatasemarangkotlin.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.content.CursorLoader
import java.io.File


/**
 * Created by faqih on 02/11/17.
 */
class RealPathUtils(val context: Context){
    fun getFile(uri: Uri): File {
        var path: String? = null
        if(Build.VERSION.SDK_INT < 11){
            path = getRealPathFromURI_BelowAPI11(uri)
        }else if(Build.VERSION.SDK_INT < 19){
            path = getRealPathFromURI_API11to18(uri)
        }else if(Build.VERSION.SDK_INT < 23){
            path = getRealPathFromURI_API19(uri)
        }else{
            path = getRealPathFromURI_API23(uri)
        }
        return File(path)
    }

    @SuppressLint("NewApi")
    fun getRealPathFromURI_API19(uri: Uri): String {
        var filePath = ""
        val wholeID = DocumentsContract.getDocumentId(uri)
        // Split at colon, use second item in the array
        val id = wholeID.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]
        val column = arrayOf(MediaStore.Images.Media.DATA)
        // where id is equal to
        val sel = MediaStore.Images.Media._ID + "=?"
        val cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, arrayOf(id), null)
        val columnIndex = cursor.getColumnIndex(column[0])
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex)
        }
        cursor.close()
        return filePath
    }

    @SuppressLint("NewApi")
    fun getRealPathFromURI_API11to18(contentUri: Uri): String? {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        var result: String? = null
        val cursorLoader = CursorLoader(
                context,
                contentUri, proj, null, null, null)
        val cursor = cursorLoader.loadInBackground()
        if (cursor != null) {
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            result = cursor!!.getString(column_index)
        }
        return result
    }

    fun getRealPathFromURI_BelowAPI11(contentUri: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.getContentResolver().query(contentUri, proj, null, null, null)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(column_index)
    }

    fun getRealPathFromURI_API23(contentUri: Uri): String {
        val cursor = context.getContentResolver().query(contentUri, null, null, null, null)
        val idx: Int
        if (cursor == null) {
            return contentUri.getPath()
        } else {
            cursor!!.moveToFirst()
            idx = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
        }
    }
}
