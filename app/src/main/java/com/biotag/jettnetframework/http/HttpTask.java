package com.biotag.jettnetframework.http;

import com.alibaba.fastjson.JSON;

public class HttpTask<T> implements Runnable {
    private IHttpRequest iHttpRequest;
    private IHttpListener iHttpListener;

    public HttpTask(String url, T requestData, IHttpRequest iHttpRequest, IHttpListener iHttpListener) {
        this.iHttpRequest = iHttpRequest;
        this.iHttpListener = iHttpListener;
        this.iHttpRequest.setUrl(url);
        this.iHttpRequest.setListener(iHttpListener);
        if(requestData!=null){
            //将请求数据转化为json字符串
            String datastr = JSON.toJSONString(requestData);
            try {
                this.iHttpRequest.setParam(datastr.getBytes("utf-8"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    @Override
    public void run() {
        this.iHttpRequest.execute();
    }
}