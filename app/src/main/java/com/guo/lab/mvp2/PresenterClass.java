package com.guo.lab.mvp2;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2017/1/18.
 */
@Retention(RUNTIME) @Target(TYPE)
public @interface PresenterClass {
     Class<? extends Presenter> clazz();
}
