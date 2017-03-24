package com.guo.lab;

/**
 * Created by Administrator on 2017/3/23.
 */

public class ResponseModel<T> {
    public String message;
    public int state;
    public T data;

    @Override
    public String toString() {
        return "ResponseModel{" +
                "message='" + message + '\'' +
                ", state=" + state +
                ", data=" + data +
                '}';
    }
}
