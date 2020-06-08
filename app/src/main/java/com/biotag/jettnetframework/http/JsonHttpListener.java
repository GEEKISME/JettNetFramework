package com.biotag.jettnetframework.http;

import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonHttpListener<T> implements IHttpListener{
    //定义出用户使用当前框架输入的对象
    private Class<T> response;
    private IDataListener iDataListener;
    private Handler handler = new Handler(Looper.getMainLooper());
    public JsonHttpListener(Class<T> response, IDataListener iDataListener) {
        this.response = response;
        this.iDataListener = iDataListener;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
        //inputStream就已经得到网络回来的相应结果了
        //第一步先把inputstream 变成string
        String content = getContent(inputStream);
        //第二步 把string 变成object
        final T responseObject = JSON.parseObject(content, response);

        //第三步 把 object交给用户
        handler.post(new Runnable() {
            @Override
            public void run() {
                iDataListener.onSuccess(responseObject);
            }
        });

    }



    @Override
    public void onFailure() {

    }


    /**inputStream 转为 String
     *
     * @param inputStream
     * @return
     */
    private String getContent(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = bufferedReader.readLine())!=null){
                sb.append(line+"\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().replace("\n","");
    }
}
