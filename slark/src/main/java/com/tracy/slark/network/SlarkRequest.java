package com.tracy.slark.network;

import com.tracy.slark.controller.model.EventConfig;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import tech.guazi.component.network.BaseRequest;
import tech.guazi.component.network.GzipRequestInterceptor;
import tech.guazi.component.network.fastjson.BaseResponse;
import tech.guazi.component.network.fastjson.ResponseCallback;

/**
 * Created by cuishijie on 2018/05/03.
 */

public class SlarkRequest extends BaseRequest {
    private static final String ON_LINE_URL = "http://sizon.com";
    private static final String TEST_URL = "http://sizon.com";
    private SlarkApiService mSlarkApiService;

    public SlarkRequest() {
        mSlarkApiService = createService(SlarkApiService.class);
    }

    public static SlarkRequest getInstance() {
        return SlarkRequest.Default.mInstance;
    }

    private static class Default {
        private static final SlarkRequest mInstance = new SlarkRequest();
    }

    @Override
    protected String getOnlineBaseUrl() {
        return ON_LINE_URL;
    }

    @Override
    protected String getTestBaseUrl() {
        return TEST_URL;
    }

    @Override
    protected List<Interceptor> getInterceptors() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new RequestInterceptor());
        interceptors.add(new GzipRequestInterceptor());
        return interceptors;
    }

    @Override
    protected List<Converter.Factory> getConverterFactory() {
        List<Converter.Factory> factories = new ArrayList<>();
        factories.add(FastJsonConverterFactory.create());
        return factories;
    }


    public void postConfig(String configJson, ResponseCallback<BaseResponse> callback) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), configJson);
        mSlarkApiService.postConfig(body).enqueue(callback);
    }

    public void getConfig(ResponseCallback<BaseResponse> callback) {
        mSlarkApiService.getConfig().enqueue(callback);
    }

    public void postLog(String log, ResponseCallback<BaseResponse> callback) {
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), log);
        mSlarkApiService.postLog(body).enqueue(callback);
    }
}
