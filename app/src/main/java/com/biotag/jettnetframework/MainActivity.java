package com.biotag.jettnetframework;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.biotag.jettnetframework.http.IDataListener;
import com.biotag.jettnetframework.http.JettNetFrameWork;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String url = "https://v.juhe.cn/historyWeather/citys";
    RequestBean param = new RequestBean();
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        param.setProvide_id("2");
        param.setKey("bb52107206585ab074f5e59a8c7387");
    }

    private void initView() {
        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:
                JettNetFrameWork.sendJsonRequest(url, param, ResponseBean.class, new IDataListener<ResponseBean>() {

                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        Toast.makeText(MainActivity.this, responseBean.getResultcode()+"", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                break;
        }
    }
}