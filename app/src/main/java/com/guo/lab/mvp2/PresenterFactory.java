package com.guo.lab.mvp2;

/**
 * Created by Administrator on 2017/1/18.
 */

public class PresenterFactory {
    public static <P> P newPresenter(Class<?> viewClass) {
        PresenterClass annotation = viewClass.getAnnotation(PresenterClass.class);
        Class<P> presenterClass = annotation == null ? null : (Class<P>) annotation.clazz();

        try {
            return presenterClass == null ? null: presenterClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
