package com.guo.lab;

/**
 * Created by Administrator on 2017/3/23.
 */

public class MemberModel {
    public String key;
    public String member_name;
    public int member_id;

    @Override
    public String toString() {
        return "MemberModel{" +
                "key='" + key + '\'' +
                ", member_name='" + member_name + '\'' +
                ", member_id=" + member_id +
                '}';
    }
}
