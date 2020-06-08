package com.biotag.jettnetframework.http;

/**
 * 给用户使用
 */
public  interface IDataListener<T> {
    void success(T t);
    void onFailure();
}
