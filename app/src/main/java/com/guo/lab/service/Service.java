package com.guo.lab.service;

import com.guo.lab.MemberModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/3/23.
 */

public interface Service {



    @POST("MemberIndex/member_info")
    @FormUrlEncoded
    Call<ResponseModel<MemberModel>> memberInfo(@Field("key") String key);

    @POST("MemberBbsClass/province_list")
    @FormUrlEncoded
    Call<ResponseModel<String>> provinceList(@Field("key") String key);

}
