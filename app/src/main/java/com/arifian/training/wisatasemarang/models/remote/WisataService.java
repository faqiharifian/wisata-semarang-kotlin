package com.arifian.training.wisatasemarang.models.remote;

import com.arifian.training.wisatasemarang.models.remote.responses.WisataResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by faqih on 30/10/17.
 */

public interface WisataService {
    @GET("read_wisata.php")
    Call<WisataResponse> getWisata();
}
