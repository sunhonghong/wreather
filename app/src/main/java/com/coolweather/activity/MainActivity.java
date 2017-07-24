package com.coolweather.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.coolweather.R;
import com.coolweather.util.OkHttpManager;
import com.coolweather.util.UrlUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    TextView tvTitle;
    ListView lvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindView();

        OkHttpManager.getInstance().postNet(UrlUtil.province, new OkHttpManager.ResultCallBack() {
            @Override
            public void OnFailed(Request request, IOException e) {

            }

            @Override
            public void onSuccess(String respose) {

                System.out.println("!!!!!!!respose!!!!"+respose);

            }
        });

    }

    private void bindView() {
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        lvMain = (ListView) findViewById(R.id.lvMain);

    }
}
