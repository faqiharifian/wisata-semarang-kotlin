package com.arifian.training.wisatasemarang.models.remote.responses;

import com.arifian.training.wisatasemarang.models.Wisata;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by faqih on 30/10/17.
 */

public class WisataResponse extends BaseResponse{
    @SerializedName("wisata")
    @Expose
    private List<Wisata> wisata = null;

    public List<Wisata> getWisata() {
        return wisata;
    }

    public void setWisata(List<Wisata> wisata) {
        this.wisata = wisata;
    }

}
