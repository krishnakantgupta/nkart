package com.kk.nkart.data.api;

import com.kk.nkart.utils.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SimpleLoggingInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Logger.INSTANCE.v("Intercepted headers: {} from URL: {}", request.headers() +"\n"+ request.url());

        return chain.proceed(request);
    }
}