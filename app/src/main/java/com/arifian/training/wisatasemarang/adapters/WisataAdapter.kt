package com.arifian.training.wisatasemarang.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.arifian.training.wisatasemarang.R
import com.arifian.training.wisatasemarang.Utils.GlideApp
import com.arifian.training.wisatasemarang.databinding.ItemWisataBinding
import com.arifian.training.wisatasemarang.models.Wisata
import com.arifian.training.wisatasemarang.models.remote.WisataClient
import java.util.*

/**
 * Created by faqih on 30/10/17.
 */

class WisataAdapter(wisataArrayList: List<Wisata>, internal var listener: OnWisataClickListener) : RecyclerView.Adapter<WisataAdapter.ViewHolder>() {
    private val wisataArrayList = ArrayList<Wisata>()

    init {
        this.wisataArrayList.addAll(wisataArrayList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWisataBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WisataAdapter.ViewHolder, position: Int) {
        val wisata = wisataArrayList[position]
        holder.bind(wisata, listener)
    }

    override fun getItemCount(): Int {
        return wisataArrayList.size
    }

    interface OnWisataClickListener {
        fun onItemClick(wisata: Wisata)
    }

    inner class ViewHolder(private val binding: ItemWisataBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(wisata: Wisata, listener: OnWisataClickListener){
            binding.wisata = wisata

            GlideApp.with(binding.ivItemGambar)
                    .load(WisataClient.IMAGE_URL + wisata.gambarWisata!!)
                    .error(R.drawable.no_image_found)
                    .centerCrop()
                    .into(binding.ivItemGambar)

            itemView.setOnClickListener { v -> listener.onItemClick(wisata) }
        }
    }
}
