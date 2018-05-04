package com.tracy.slark.network;

import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import tech.guazi.component.network.PhoneInfoHelper;
import tech.guazi.component.network.SignHelper;

/**
 * Created by cuishijie on 2018/05/03.
 */

final class RequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = handleParams(chain.request());
        return chain.proceed(addHeaders(request));
    }

    /**
     * 添加post公共参数
     */
    private Request handleParams(Request original) {
        Map<String, String> map = new HashMap<>();
        // put params
        map.putAll(addPublicParamsToMap());
        map.put("sign", getSign(original));
        //rebuild request
        HttpUrl.Builder builder = original.url().newBuilder();
        Iterator iterator = map.keySet().iterator();
        String key;
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            builder.addQueryParameter(key, map.get(key));
        }
        return original.newBuilder().url(builder.build()).build();
    }

    /**
     * 获取sign
     */
    private String getSign(Request original) {
        //get parasm
        Map<String, String> paramsGet = new HashMap<>();
        HttpUrl url = original.url();
        for (int i = 0; i < url.querySize(); i++) {
            paramsGet.put(url.queryParameterName(i), url.queryParameterValue(i));
        }
        paramsGet.putAll(addPublicParamsToMap());

        //postparams
        Map<String, String> postParams = new HashMap<>();
        if (original.body() instanceof FormBody) {
            FormBody formBody = (FormBody) original.body();
            for (int i = 0; i < formBody.size(); i++) {
                postParams.put(formBody.name(i), formBody.value(i));
            }
        }
        return SignHelper.getTokens(paramsGet, postParams);
    }


    /**
     * 添加headers
     */
    private Request addHeaders(Request original) {
        Map<String, String> map = addHeadersToMap();
        Iterator iterator = map.keySet().iterator();
        String key;
        Request.Builder builder = original.newBuilder();
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            if (checkNameAndValue(map.get(key))) {
                builder.addHeader(key, map.get(key));
            }
        }
        return builder.build();
    }

    private boolean checkNameAndValue(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        for (int i = 0, length = value.length(); i < length; i++) {
            char c = value.charAt(i);
            if ((c <= '\u001f' && c != '\t') || c >= '\u007f') {
                return false;
            }
        }
        return true;
    }

    private Map<String, String> addHeadersToMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        putNotNullGetParams(map, "versionId", PhoneInfoHelper.versionName);
        putNotNullGetParams(map, "model", PhoneInfoHelper.model);
        return map;
    }

    private Map<String, String> addPublicParamsToMap() {
        Map<String, String> map = new HashMap();
        putNotNullGetParams(map, "customerId", PhoneInfoHelper.customerId);
        putNotNullGetParams(map, "deviceId", PhoneInfoHelper.IMEI);
        putNotNullGetParams(map, "dpi", PhoneInfoHelper.density + "");
        putNotNullGetParams(map, "screenWH", PhoneInfoHelper.screenWidth + "X" + PhoneInfoHelper.screenHeight);
        putNotNullGetParams(map, "osv", PhoneInfoHelper.osv + "");
        putNotNullGetParams(map, "model", PhoneInfoHelper.model);
        putNotNullGetParams(map, "platform", PhoneInfoHelper.platform);
        putNotNullGetParams(map, "versionId", PhoneInfoHelper.versionName);
        putNotNullGetParams(map, "net", PhoneInfoHelper.netType);
        return map;
    }


    public void putNotNullGetParams(Map<String, String> map, String key, String value) {
        if (!TextUtils.isEmpty(value)) {
            map.put(key, value);
        }
    }

}

