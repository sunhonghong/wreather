package com.coolweather.util;


import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by SHH on 2017/7/12.
 */

public class OkHttpManager {//单例模式

    //1.声明私有的静态的实例
    private static OkHttpManager instance;
    private OkHttpClient mOkHttpClient;
    private Handler okHandler;

    //2.私有的构造方法，外部不可以通过new方式使用
    private OkHttpManager(){
        //Looper.getMainLooper()//获取主线程loop
       okHandler = new Handler(Looper.getMainLooper());//获取主线程loop

        //创建okhttpclient
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS);//超时时间
        mOkHttpClient = builder.build();

    }

    //3.声明共有的静态获取实例
    public static OkHttpManager getInstance(){
        if (instance == null){
            synchronized (OkHttpManager.class){
                if (instance == null){
                    instance = new OkHttpManager();
                }
            }
        }
        return instance;
    }

    public void postNet(String url, ResultCallBack resultCallback, Param... param){

        if (param == null){
            param = new Param[0];
        }

        //创建表单数据
        FormBody.Builder formBody = new FormBody.Builder();
        for (Param p: param){
            formBody.add(p.key, p.value );
        }

        RequestBody requestBody = formBody.build();
        //创建request请求
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        dealNet(request, resultCallback);

    }

    private void dealNet(final Request request, final ResultCallBack resultCallback){
        mOkHttpClient.newCall(request).enqueue(new Callback() {//异步，在子线程中
            @Override
            public void onFailure(Call call, final IOException e) {
                okHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        resultCallback.OnFailed(request,e);

                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = "";
                try {
                    str = response.body().string();
                }catch (IOException e){
                    e.printStackTrace();
                }
                final String finalStr = str;
                okHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        resultCallback.onSuccess(finalStr);
                    }
                });

            }
        });
    }

    public static abstract class ResultCallBack{//抽象类
        //两个抽象方法
        public abstract void OnFailed(Request request, IOException e);
        public abstract void onSuccess(String respose);

    }

    public static class Param{

        String key;
        String value;
        public Param(){}

        public Param(String key, String value){
            this.key = key;
            this.value = value;
        }
    }
}
