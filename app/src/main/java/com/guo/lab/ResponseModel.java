package com.guo.lab;

/**
 * Created by Administrator on 2017/3/23.
 */

public class ResponseModel<T> {
    public String err;
    public int state;
    public T datas;

    @Override
    public String toString() {
        return "ResponseModel{" +
                "err='" + err + '\'' +
                ", state=" + state +
                ", datas=" + datas +
                '}';
    }
}
