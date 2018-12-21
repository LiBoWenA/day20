package com.example.lianxishop.model;



import com.example.lianxishop.okhttp.MyCallBack;

import java.util.Map;

public interface IModel {
    void requestData(String path, Map<String, String> params, Class clazz, MyCallBack myCallBack);
}
