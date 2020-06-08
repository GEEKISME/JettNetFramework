package com.biotag.jettnetframework.http;

public class JettNetFrameWork {
    public static <T,M> void sendJsonRequest(String url,T requestparam,Class<M> response,IDataListener iDataListener){
        IHttpRequest iHttpRequest = new JsonHttpRequest();
        IHttpListener iHttpListener = new JsonHttpListener<>(response,iDataListener);
        HttpTask tHttpTask = new HttpTask(url, requestparam, iHttpRequest, iHttpListener);
        ThreadManager.getInstance().addTask(tHttpTask);
    }

//    public static <T,M> void sendXmlRequest(String url,T requestparam,Class<M> response,IDataListener iDataListener){
//        IHttpRequest iHttpRequest = new JsonHttpRequest();
//        IHttpListener iHttpListener = new JsonHttpListener<>(response,iDataListener);
//        HttpTask tHttpTask = new HttpTask(url, requestparam, iHttpRequest, iHttpListener);
//        ThreadManager.getInstance().addTask(tHttpTask);
//    }
}
