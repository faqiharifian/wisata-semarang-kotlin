package com.arifian.training.wisatasemarangkotlin


import android.graphics.Point
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.arifian.training.wisatasemarangkotlin.drawroutemap.DrawRouteMaps
import com.arifian.training.wisatasemarangkotlin.drawroutemap.models.Response
import com.arifian.training.wisatasemarangkotlin.models.Wisata
import com.arifian.training.wisatasemarangkotlin.models.remote.SimpleRetrofitCallback
import com.arifian.training.wisatasemarangkotlin.models.remote.responses.WisataResponse
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions




/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment(), OnMapReadyCallback {
    var mMap: GoogleMap? = null
    var wisataArrayList: List<Wisata>? = null

//    lateinit var markerA: Marker
//    lateinit var markerB: Marker

    companion object {

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
//        getWisata()
        return view
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!

        updateMarker()

        val origin = LatLng(-7.788969, 110.338382)
        val destination = LatLng(-7.781200, 110.349709)
        DrawRouteMaps.getInstance(activity)
                .draw(origin, destination, mMap, object: DrawRouteMaps.OnLoadFinishedListener{
                    override fun onLoadFinish(response: Response?) {
                        try {
                            activity.runOnUiThread{
                                var duration = response!!.routes!![0]!!.legs!![0]!!.duration!!.text
                                var distance = response!!.routes!![0]!!.legs!![0]!!.distance!!.text
                                mMap!!.addMarker(MarkerOptions().position(origin).title("origin"))
                                mMap!!.addMarker(MarkerOptions().position(destination).title("destination").snippet(distance+" "+duration))
                            }

                        }catch (e: Exception){
                            e.printStackTrace()
                        }
                    }
                })

        val bounds = LatLngBounds.Builder()
                .include(origin)
                .include(destination).build()
        val displaySize = Point()
        activity.windowManager.defaultDisplay.getSize(displaySize)
        mMap!!.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, displaySize.y, 25))
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
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 13))
        }
    }

}
