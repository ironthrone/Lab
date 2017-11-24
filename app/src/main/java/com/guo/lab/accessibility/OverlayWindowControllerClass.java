package com.guo.lab.accessibility;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by ironthrone on 2017/10/24 0024.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface OverlayWindowControllerClass {
    Class<? extends OverlayWindowController> value();
}
