package com.arifian.training.wisatasemarang;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.arifian.training.wisatasemarang.adapters.WisataAdapter;
import com.arifian.training.wisatasemarang.databinding.FragmentHomeBinding;
import com.arifian.training.wisatasemarang.models.Wisata;
import com.arifian.training.wisatasemarang.models.remote.SimpleRetrofitCallback;
import com.arifian.training.wisatasemarang.models.remote.responses.WisataResponse;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import static com.arifian.training.wisatasemarang.DetailWisataActivity.KEY_WISATA;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    FragmentHomeBinding mBinding;

    List<Wisata> wisataArrayList = new ArrayList<>();
    WisataAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);

        adapter = new WisataAdapter(wisataArrayList, wisata -> {
            Intent intent = new Intent(getActivity(), DetailWisataActivity.class);
            intent.putExtra(KEY_WISATA, Parcels.wrap(wisata));
            startActivity(intent);
        });
        mBinding.recyclerView.setAdapter(adapter);

        getWisata();

        return mBinding.getRoot();
    }

    private void getWisata() {
        ProgressBar progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleSmall);

        progressBar.setVisibility(View.VISIBLE);
        WisataApplication.get(getActivity())
                .getService(getActivity())
                .getWisata()
                .enqueue(new SimpleRetrofitCallback<WisataResponse>(getActivity()) {
                    @Override
                    public void onSuccess(WisataResponse response) {
                        wisataArrayList.addAll(response.getWisata());
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}
