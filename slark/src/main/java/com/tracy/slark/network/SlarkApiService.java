package com.tracy.slark.network;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import tech.guazi.component.network.fastjson.BaseResponse;

/**
 * Created by cuishijie on 2018/05/03.
 */

public interface SlarkApiService {

    @GET("config")
    Call<BaseResponse> getConfig();

    @POST("postConfig")
    Call<BaseResponse> postConfig(@Body RequestBody config);

    @POST("postLog")
    Call<BaseResponse> postLog(@Body RequestBody log);
}
