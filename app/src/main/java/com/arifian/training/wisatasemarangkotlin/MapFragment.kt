package com.arifian.training.wisatasemarangkotlin


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


/**
 * A simple [Fragment] subclass.
 */
class MapFragment : Fragment(), OnMapReadyCallback {
    lateinit var mMap: GoogleMap

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
        return view
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!

        // Add a marker in Sydney and move the camera
        val dcs = LatLng(-7.063282, 110.446007)
        mMap.addMarker(MarkerOptions().position(dcs).title("Digit Creative Studio"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dcs, 15f))

        mMap.setOnInfoWindowClickListener { marker ->
            val gmmIntentUri = Uri.parse("google.navigation:q="+dcs.latitude+","+dcs.longitude)
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.`package` = "com.google.android.apps.maps"
            if (mapIntent.resolveActivity(activity.packageManager) != null) {
                startActivity(mapIntent)
            }
        }
    }

}// Required empty public constructor
