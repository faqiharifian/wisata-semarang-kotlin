package com.arifian.training.wisatasemarangkotlin


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.arifian.training.wisatasemarangkotlin.Utils.Constants.Companion.KEY_WISATA
import com.arifian.training.wisatasemarangkotlin.adapters.WisataAdapter
import com.arifian.training.wisatasemarangkotlin.databinding.FragmentHomeBinding
import com.arifian.training.wisatasemarangkotlin.models.Wisata
import com.arifian.training.wisatasemarangkotlin.models.remote.SimpleRetrofitCallback
import com.arifian.training.wisatasemarangkotlin.models.remote.responses.WisataResponse
import org.parceler.Parcels
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    internal lateinit var mBinding: FragmentHomeBinding

    internal var wisataArrayList: MutableList<Wisata> = ArrayList()
    internal lateinit var adapter: WisataAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater!!, container, false)

        adapter = WisataAdapter(wisataArrayList, object: WisataAdapter.OnWisataClickListener{
            override fun onItemClick(wisata: Wisata) {
                val intent = Intent(activity, DetailWisataActivity::class.java)
                intent.putExtra(KEY_WISATA, Parcels.wrap(wisata))
                startActivity(intent)
            }
        })

        mBinding.recyclerView.adapter = adapter

        getWisata()

        return mBinding.root
    }

    private fun getWisata() {
        val progressBar = ProgressBar(activity, null, android.R.attr.progressBarStyleSmall)

        progressBar.visibility = View.VISIBLE
        WisataApplication.get(activity)
                .getService(activity)
                .wisata
                .enqueue(object : SimpleRetrofitCallback<WisataResponse>(activity) {
                    override fun onSuccess(response: WisataResponse) {
                        wisataArrayList.addAll(response.wisata!!)
                        adapter.swapData(wisataArrayList)
                    }
                })
    }

    companion object {

        fun newInstance(): HomeFragment {

            val args = Bundle()

            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
