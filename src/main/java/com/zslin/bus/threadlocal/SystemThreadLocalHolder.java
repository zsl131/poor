package com.zslin.bus.threadlocal;

/**
 * Created by zsl on 2019/4/24.
 */
public class SystemThreadLocalHolder {

    private final static ThreadLocal<RequestDto> threadLocal = new ThreadLocal<>();

    public static void initRequestDto(RequestDto requestDto) {
        threadLocal.set(requestDto);
    }

    public static RequestDto getRequestDto() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }
}
