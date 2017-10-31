package com.arifian.training.wisatasemarangkotlin


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arifian.training.wisatasemarangkotlin.Utils.Constants
import com.arifian.training.wisatasemarangkotlin.adapters.WisataAdapter
import com.arifian.training.wisatasemarangkotlin.database.DBHelper
import com.arifian.training.wisatasemarangkotlin.databinding.FragmentFavoriteBinding
import com.arifian.training.wisatasemarangkotlin.models.Wisata
import org.parceler.Parcels
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {
    internal lateinit var mBinding: FragmentFavoriteBinding

    internal var wisataArrayList: MutableList<Wisata> = ArrayList()
    internal lateinit var adapter: WisataAdapter

    companion object {

        fun newInstance(): FavoriteFragment {

            val args = Bundle()

            val fragment = FavoriteFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = FragmentFavoriteBinding.inflate(inflater!!, container, false)

        adapter = WisataAdapter(wisataArrayList, object : WisataAdapter.OnWisataClickListener {
            override fun onItemClick(wisata: Wisata) {
                val intent = Intent(activity, DetailWisataActivity::class.java)
                intent.putExtra(Constants.KEY_WISATA, Parcels.wrap(wisata))
                startActivity(intent)
            }
        })

        mBinding.recyclerView.adapter = adapter

        getWisata()

        return mBinding.root
    }

    private fun getWisata() {
        val db = DBHelper(activity)
        wisataArrayList.addAll(db.query())
        adapter.swapData(wisataArrayList)
    }
}
