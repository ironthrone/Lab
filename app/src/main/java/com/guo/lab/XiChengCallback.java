package com.guo.lab;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/3/24.
 */

public abstract class XiChengCallback<T> implements Callback<ResponseModel<T>> {
    @Override
    public void onResponse(Call<ResponseModel<T>> call, Response<ResponseModel<T>> response) {
        if (response.isSuccessful() && response.body().state == 1) {
            onSuccess(response.body().data);
        } else {
            onFail(response.body().message);
        }
    }

    @Override
    public void onFailure(Call<ResponseModel<T>> call, Throwable t) {
        onFail(t.getMessage());
    }

    protected abstract void onSuccess(T data);

    protected abstract void onFail(String message);
}
