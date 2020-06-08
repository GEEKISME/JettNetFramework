package com.biotag.jettnetframework.http;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class JsonHttpRequest implements IHttpRequest {

    private String url;
    private byte[] param;
    private IHttpListener iHttpListener;
    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setParam(byte[] param) {
        this.param = param;
    }


    @Override
    public void setListener(IHttpListener iHttpListener) {
        this.iHttpListener = iHttpListener;
    }

    private HttpURLConnection httpURLConnection;

    /**
     * 在这个方法之中执行真实的网络操作
     * 在这里可以使用适配器，来进行不同方式的切换操作
     */

    @Override
    public void execute() {
        URL url = null;
        try {
            url = new URL(this.url);
            URLConnection urlConnection = url.openConnection();
            httpURLConnection = (HttpURLConnection)urlConnection;
            httpURLConnection.setConnectTimeout(6000);//连接超时时间
            httpURLConnection.setUseCaches(false);//不使用缓存
            httpURLConnection.setInstanceFollowRedirects(true);
            httpURLConnection.setReadTimeout(3000);//响应超时的时间
            httpURLConnection.setDoInput(true);//设置这个连接是否可以写入
            httpURLConnection.setDoOutput(true);//设置这个连接是否可以输出
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type","application/json");
            httpURLConnection.connect();//建立连接
            //-----------------使用字节流发送数据--------------
            OutputStream out = httpURLConnection.getOutputStream();
            //缓冲字节流 包装字节流
            BufferedOutputStream bos = new BufferedOutputStream(out);
            //把请求参数转化的字节流数组写入缓冲区
            bos.write(param);
            //刷新缓冲区，发送数据
            bos.flush();
            out.close();
            bos.close();
            //
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStream inputStream = httpURLConnection.getInputStream();
                iHttpListener.onSuccess(inputStream);
            }else {
                throw new RuntimeException("请求失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("请求失败");
        }finally {
            //关闭httpURLConnection对象
            httpURLConnection.disconnect();
        }
    }

}
