package com.util.pattern.adapter;

/**
 * Created by wn on 2018/2/11.
 */
public class RequestAdapter extends RequestAdaptee implements RequestInterface {
    @Override
    public void request() {
        super.adapteeRequest();
    }

    public static void main(String[] args) {
        RequestInterface request = new RequestInterImpl();
        request.request();
        RequestInterface r1 = new RequestAdapter();
        r1.request();
    }
}
