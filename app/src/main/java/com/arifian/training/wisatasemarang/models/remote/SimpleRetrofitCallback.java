package com.arifian.training.wisatasemarang.models.remote;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.arifian.training.wisatasemarang.models.remote.responses.BaseResponse;

import java.net.SocketTimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by faqih on 29/09/17.
 */

public abstract class SimpleRetrofitCallback<T extends BaseResponse> implements Callback<T> {
    Activity activity;

    public SimpleRetrofitCallback(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {

        if (response.code() == 200) {

            if(response.isSuccessful()){
                T baseResponse = response.body();
                if(baseResponse != null) {
                    if (!baseResponse.getSuccess()) {
                        Toast.makeText(activity, baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        onSuccess(baseResponse);
                    }
                }
            }else{
                Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        if(!call.isCanceled()) {
            if (t instanceof SocketTimeoutException) {
                Toast.makeText(activity, "timeout", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public abstract void onSuccess(T response);
}
