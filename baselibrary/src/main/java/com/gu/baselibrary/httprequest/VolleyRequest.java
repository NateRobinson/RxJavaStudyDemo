package com.gu.baselibrary.httprequest;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.gu.baselibrary.baseui.BaseApplication;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by Nate on 2015/10/9.
 */
public class VolleyRequest<T> extends BaseRequest {


    public VolleyRequest(NetDataBackListener listener, int code) {
        this.mListener = listener;
        this.code = code;
    }

    /**
     * 发起GET请求
     */
    @Override
    public void askGetRequest(String url, String tag, Type type) {
        GsonGetRequest<T> request = new GsonGetRequest<>(url, type,
                new Response.Listener<T>() {
                    @Override
                    public void onResponse(T t) {
                        //获取到值
                        if (null != mListener) {
                            mListener.successData(t, code);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //请求失败的情况
                        if (null != mListener) {
                            mListener.errorData(error, code);
                        }
                    }
                });
        BaseApplication.addRequest(request, tag);
    }

    /**
     * 发起POST请求
     */
    @Override
    public void askPostRequest(String url, String tag, Type type, Map map) {
        GsonPostRequest<T> request = new GsonPostRequest<>(url, map, type, new Response.Listener<T>() {
            @Override
            public void onResponse(T t) {
                //获取到值
                if (null != mListener) {
                    mListener.successData(t, code);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //请求失败的情况
                if (null != mListener) {
                    mListener.errorData(error, code);
                }
            }
        });
        BaseApplication.addRequest(request, tag);
    }
}
