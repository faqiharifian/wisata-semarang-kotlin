package com.arifian.training.wisatasemarang.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.arifian.training.wisatasemarang.R;
import com.arifian.training.wisatasemarang.Utils.GlideApp;
import com.arifian.training.wisatasemarang.databinding.ItemWisataBinding;
import com.arifian.training.wisatasemarang.models.Wisata;
import com.arifian.training.wisatasemarang.models.remote.WisataClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by faqih on 30/10/17.
 */

public class WisataAdapter extends RecyclerView.Adapter<WisataAdapter.ViewHolder>{
    private List<Wisata> wisataArrayList = new ArrayList<>();

    public WisataAdapter(List<Wisata> wisataArrayList) {
        this.wisataArrayList = wisataArrayList;
    }

    @Override
    public WisataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemWisataBinding binding = ItemWisataBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(WisataAdapter.ViewHolder holder, int position) {
        Wisata wisata = wisataArrayList.get(position);
        holder.binding.setWisata(wisata);

        GlideApp.with(holder.binding.ivItemGambar)
                .load(WisataClient.IMAGE_URL+wisata.getGambarWisata())
                .error(R.drawable.no_image_found)
                .centerCrop()
                .into(holder.binding.ivItemGambar);
    }

    @Override
    public int getItemCount() {
        return wisataArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemWisataBinding binding;
        public ViewHolder(ItemWisataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
