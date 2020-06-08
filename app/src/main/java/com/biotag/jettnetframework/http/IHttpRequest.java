package com.biotag.jettnetframework.http;

//请求的顶层接口
public interface IHttpRequest {
    void setUrl(String url);
    void setParam(byte[] param);
    void execute();
    void setListener(IHttpListener iHttpListener);
}
