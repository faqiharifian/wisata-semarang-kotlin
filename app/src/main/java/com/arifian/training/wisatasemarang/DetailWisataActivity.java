package com.arifian.training.wisatasemarang;

import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.arifian.training.wisatasemarang.Utils.GlideApp;
import com.arifian.training.wisatasemarang.databinding.ActivityDetailWisataBinding;
import com.arifian.training.wisatasemarang.models.Wisata;
import com.arifian.training.wisatasemarang.models.remote.WisataClient;

import org.parceler.Parcels;

public class DetailWisataActivity extends AppCompatActivity {
    public static final String KEY_WISATA = "wisata";

    ActivityDetailWisataBinding mBinding;
    Wisata wisata;

    boolean favorite;
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wisata = Parcels.unwrap(getIntent().getParcelableExtra(KEY_WISATA));

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail_wisata);
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(wisata.getNamaWisata());

        mBinding.setWisata(wisata);

        GlideApp.with(this)
                .load(WisataClient.IMAGE_URL+wisata.getGambarWisata())
                .centerCrop()
                .into(mBinding.ivDetailGambar);

        checkFavorite();

        mBinding.fab.setOnClickListener((view) -> {
            favorite = !favorite;
            checkFavorite();

            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        });
    }

    private void checkFavorite() {
        if(favorite)
            mBinding.fab.setImageResource(R.drawable.ic_action_favorite_true);
        else
            mBinding.fab.setImageResource(R.drawable.ic_action_favorite_false);
    }
}
