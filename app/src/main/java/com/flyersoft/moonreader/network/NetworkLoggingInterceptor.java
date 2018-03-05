package com.flyersoft.moonreader.network;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.flyersoft.moonreader.component.log.RsLogger;
import com.flyersoft.moonreader.component.log.RsLoggerManager;

import java.io.IOException;

/**
 * @author kiefer
 *         Created on 16/09/26
 */
public class NetworkLoggingInterceptor implements Interceptor {

    public static final String TAG = "NetworkLoggingInterceptor";
    RsLogger logger = RsLoggerManager.getLogger();

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        return response;
    }
}
