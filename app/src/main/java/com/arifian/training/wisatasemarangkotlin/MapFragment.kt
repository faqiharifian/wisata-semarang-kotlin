package com.arifian.training.wisatasemarangkotlin


import android.Manifest
import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.arifian.training.wisatasemarangkotlin.Utils.GPSTracker
import com.arifian.training.wisatasemarangkotlin.drawroutemap.DrawRouteMaps
import com.arifian.training.wisatasemarangkotlin.drawroutemap.models.Response
import com.arifian.training.wisatasemarangkotlin.models.Wisata
import com.arifian.training.wisatasemarangkotlin.models.remote.SimpleRetrofitCallback
import com.arifian.training.wisatasemarangkotlin.models.remote.responses.WisataResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment(), OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {
    var mMap: GoogleMap? = null
    var wisataArrayList: List<Wisata>? = null

    var gpsTracker: GPSTracker? = null

    var current: Marker? = null

    companion object {
        const val REQUEST_LOCATION = 100

        fun newInstance(): MapFragment {

            val args = Bundle()

            val fragment = MapFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_map, container, false)
        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        gpsTracker = GPSTracker(activity)

        getWisata()
        return view
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!

        updateMarker()

        mMap!!.setOnMarkerClickListener { marker: Marker? ->
            val origin = LatLng(gpsTracker!!.latitude, gpsTracker!!.longitude)
            val destination = marker!!.position
            mMap!!.clear()
            updateMarker()

            DrawRouteMaps.getInstance(activity)
                    .draw(origin, destination, mMap, object: DrawRouteMaps.OnLoadFinishedListener{
                        override fun onLoadFinish(response: Response?) {
                            try {
                                activity.runOnUiThread{
                                    var duration = response!!.routes!![0]!!.legs!![0]!!.duration!!.text
                                    var distance = response!!.routes!![0]!!.legs!![0]!!.distance!!.text
                                    current = mMap!!.addMarker(MarkerOptions()
                                            .position(origin)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_a))
                                            .title("origin"))
                                    marker.snippet = distance+" "+duration
                                    val bounds = LatLngBounds.Builder()
                                            .include(origin)
                                            .include(destination).build()
                                    val displaySize = Point()
                                    activity.windowManager.defaultDisplay.getSize(displaySize)
                                    mMap!!.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, displaySize.y, 100))
                                }

                            }catch (e: Exception){
                                e.printStackTrace()
                            }
                        }
                    })
            true
        }
    }

    private fun getWisata() {
        val progressBar = ProgressBar(activity, null, android.R.attr.progressBarStyleSmall)

        progressBar.visibility = View.VISIBLE
        WisataApplication.get(activity)
                .getService(activity)
                .wisata
                .enqueue(object : SimpleRetrofitCallback<WisataResponse>(activity) {
                    override fun onSuccess(response: WisataResponse) {
                        wisataArrayList = response.wisata!!
                        updateMarker()
                    }
                })
    }

    private fun updateMarker(){
        if(mMap != null && wisataArrayList != null){
            val bounds = LatLngBounds.Builder()
            for(wisata: Wisata in wisataArrayList!!){
                val marker = LatLng(wisata.latitudeWisata!!.toDouble(), wisata.longitudeWisata!!.toDouble())
                mMap!!.addMarker(MarkerOptions().position(marker))
                bounds.include(marker)
            }
            if(current == null)
                mMap!!.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 13))

            methodRequiresTwoPermission()
        }
    }

    override
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    @AfterPermissionGranted(REQUEST_LOCATION)
    private fun methodRequiresTwoPermission() {
        val perms = arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION)
        if (EasyPermissions.hasPermissions(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(activity, "Butuh lokasi",
                    REQUEST_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }


}
