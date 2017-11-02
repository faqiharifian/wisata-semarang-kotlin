package com.arifian.training.wisatasemarangkotlin


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.arifian.training.wisatasemarangkotlin.Utils.FileUtils
import com.arifian.training.wisatasemarangkotlin.Utils.GlideApp
import com.arifian.training.wisatasemarangkotlin.databinding.FragmentCreateBinding
import com.arifian.training.wisatasemarangkotlin.models.remote.SimpleRetrofitCallback
import com.arifian.training.wisatasemarangkotlin.models.remote.responses.BaseResponse
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlacePicker
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class CreateFragment : Fragment() {

    companion object {
        const val REQUEST_IMAGE = 1
        const val REQUEST_PERMISSION = 2
        const val REQUEST_PLACE = 3

        fun newInstance(): CreateFragment {

            val args = Bundle()

            val fragment = CreateFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var mBinding: FragmentCreateBinding
    var uri: Uri? = null
    lateinit var place: Place

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = FragmentCreateBinding.inflate(inflater!!, container!!, false)

        mBinding.imageView.setOnClickListener {
            openGallery()
        }

        mBinding.btnMaps.setOnClickListener {
            val intent = PlacePicker.IntentBuilder()
            startActivityForResult(intent.build(activity), REQUEST_PLACE)
        }

        mBinding.btnSubmit.setOnClickListener{
            submit()
        }

        requestPermission()

        GlideApp.with(this)
                .load(R.drawable.add_photo)
                .into(mBinding.imageView)

        return mBinding.root
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.e("result", requestCode.toString()+" - "+resultCode.toString())
        if(resultCode == RESULT_OK && data != null && requestCode == REQUEST_IMAGE){
            uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, data.data)
            mBinding.imageView.setImageBitmap(bitmap)
        }else if(resultCode == RESULT_OK && data != null && requestCode == REQUEST_PLACE){
            place = PlacePicker.getPlace(activity, data)
            val alamat = place.address
            mBinding.edtAlamat.setText(alamat!!)
            mBinding.statusMaps.text = alamat!!
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(REQUEST_PERMISSION)
    private fun requestPermission() {
        val perms = Manifest.permission.READ_EXTERNAL_STORAGE
        if (EasyPermissions.hasPermissions(context, perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(activity, "Butuh lokasi",
                    REQUEST_PERMISSION, perms)
        }
    }

    private fun check(et: EditText): Boolean{
        if(TextUtils.isEmpty(et.text)){
            et.error = "Harus diisi"
            return false
        }
        return true
    }

    private fun submit() {
        if(check(mBinding.edtNama)
                && check(mBinding.edtDeskripsi)
                && check(mBinding.edtAlamat)
                && check(mBinding.edtEvent)){
            if(uri == null){
                Toast.makeText(activity, "Gambar harus ada", LENGTH_SHORT).show()
            }else{
//                val file = RealPathUtils(activity).getFile(uri!!)
                val file = File(FileUtils.getPath(activity, uri))

                var namaGambar = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
                namaGambar += file.extension
                val sFile = RequestBody.create(MediaType.parse("image/*"), file)

                val fileToUpload = MultipartBody.Part.createFormData("file", namaGambar, sFile)

                WisataApplication.get(activity)
                        .getService(activity)
                        .wisataPost(
                                fileToUpload,
                                RequestBody.create(MediaType.parse("text/plain"), mBinding.edtNama.text.toString()),
                                RequestBody.create(MediaType.parse("text/plain"), namaGambar),
                                RequestBody.create(MediaType.parse("text/plain"), mBinding.edtDeskripsi.text.toString()),
                                RequestBody.create(MediaType.parse("text/plain"), mBinding.edtEvent.text.toString()),
                                RequestBody.create(MediaType.parse("text/plain"), place.latLng.latitude.toString()),
                                RequestBody.create(MediaType.parse("text/plain"), place.latLng.longitude.toString()),
                                RequestBody.create(MediaType.parse("text/plain"), mBinding.edtAlamat.text.toString())

                        )
                        .enqueue(object : SimpleRetrofitCallback<BaseResponse>(activity) {
                            override fun onSuccess(response: BaseResponse) {
                                Toast.makeText(activity, response.message, LENGTH_SHORT).show()
                                (activity as AppCompatActivity).supportFragmentManager.popBackStack()
                            }
                        })
            }
        }
    }
}
