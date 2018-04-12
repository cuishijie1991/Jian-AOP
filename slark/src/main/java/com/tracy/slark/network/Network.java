package com.tracy.slark.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by zhaohaiyang on 2018/4/2.
 */

public class Network {

    public static final int GET = 1;
    public static final int POST = 2;

    private static Network network;

    private Network() {
    }

    public static Network getInstance() {
        if (network == null) {
            network = new Network();
        }
        return network;
    }

    public void sendRequestWithHttpURLConnection(Context context, int action, String body) {
        //开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    if (action == GET) {
                        URL url = new URL("http://sizon.com/config");
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                    } else {
                        URL url = new URL("http://sizon.com/postConfig");
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.writeBytes(body);
                    }
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse(context, response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    private void showResponse(Context context, final String response) {
        Log.e("HttpResult", response.toString());
        SharedPreferences mSharedPreferences = context.getSharedPreferences("config", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString("pointConfig", response.toString());
        editor.commit();
    }
}
