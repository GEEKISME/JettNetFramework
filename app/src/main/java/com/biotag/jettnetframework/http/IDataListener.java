package com.biotag.jettnetframework.http;

/**
 * 给用户使用
 */
public  interface IDataListener<T> {
    void onSuccess(T t);
    void onFailure();
}
