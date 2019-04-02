package com.zslin.bus.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Created by zsl on 2018/7/5.
 */
@Target(ElementType.METHOD)
public @interface ApiCodeMethod {

    /** 接口编码 */
    String value() default "";

    /** 备注 */
    String remark() default "";
}
