package com.biotag.jettnetframework.http;

import java.io.InputStream;

//响应顶层接口
public  interface IHttpListener {
    void onSuccess(InputStream inputStream);

    void onFailure();
}
