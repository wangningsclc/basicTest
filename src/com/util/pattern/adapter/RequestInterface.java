package com.util.pattern.adapter;

/**
 * Created by wn on 2018/2/11.
 */
public interface RequestInterface {
    public void request();
}

class RequestInterImpl implements RequestInterface{
    @Override
    public void request() {
        System.out.println("old request....");
    }
}


