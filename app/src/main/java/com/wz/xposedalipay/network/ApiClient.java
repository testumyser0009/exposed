package com.wz.xposedalipay.network;


import com.wz.xposedalipay.network.service.FdKyAppDataService;
import com.wz.xposedalipay.util.ApiConstants;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */

public class ApiClient{


    /**
     * 获取指定数据类型
     * @return
     */
    public static FdKyAppDataService getFdKyappDataService(String baseUrl){

        FdKyAppDataService fdKyAppDataService = initService(baseUrl, FdKyAppDataService.class);
        return fdKyAppDataService;
    }

    /**
     * 获得想要的 retrofit service
     * @param baseUrl  数据请求url
     * @param clazz    想要的 retrofit service 接口，Retrofit会代理生成对应的实体类
     * @param <T>      api service
     * @return
     */
    private static <T> T initService(String baseUrl, Class<T> clazz) {
        return getRetrofitInstance(baseUrl).create(clazz);
    }

    /**单例retrofit*/
    private static Retrofit retrofitInstance;
    private static Retrofit getRetrofitInstance(String baseUrl){
        if (retrofitInstance == null) {
            synchronized (ApiClient.class) {
                if (retrofitInstance == null) {
                    retrofitInstance = new Retrofit.Builder()
                            .baseUrl(ApiConstants.GankHost)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(getOkHttpClientInstance())
                            .build();
                }
            }
        }
        // 重新设置baseUrl
        retrofitInstance.baseUrl().parse(baseUrl);
        return retrofitInstance;
    }

    /**单例OkHttpClient*/
    private static OkHttpClient okHttpClientInstance;
    private static OkHttpClient getOkHttpClientInstance(){
        if (okHttpClientInstance == null) {
            synchronized (ApiClient.class) {
                if (okHttpClientInstance == null) {
                    OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

//                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//                    builder.addInterceptor(httpLoggingInterceptor);
                    builder.addInterceptor(new ChangeUrlInterceptor());
                    builder.connectTimeout(30, TimeUnit.SECONDS).
                            readTimeout(30, TimeUnit.SECONDS).
                            writeTimeout(30, TimeUnit.SECONDS).build();

//                    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
//                    httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
//                    LoggingInterceptor httpLoggingInterceptor1 = new LoggingInterceptor.Builder()
//                            .loggable(BuildConfig.DEBUG)
//                            .setLevel(Level.BASIC)
//                            .log(Platform.INFO)
//                            .request("请求")
//                            .response("响应")
//                            .build();
//
//                    builder.addInterceptor(httpLoggingInterceptor1);

                    // 日志显示级别
//                    HttpLoggingInterceptor.Level level= HttpLoggingInterceptor.Level.BASIC;
//                    //新建log拦截器
//                    HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//                        @Override
//                        public void log(String message) {
//                            Log.i("poi_item","OkHttp====Message:"+message);
//                        }
//                    });
//                    loggingInterceptor.setLevel(level);
//                    //OkHttp进行添加拦截器loggingInterceptor
//                    builder.addInterceptor(loggingInterceptor);
//                      builder.addNetworkInterceptor(new StethoInterceptor());
//                      BuildConfig.STETHO.addNetworkInterceptor(builder);
                    okHttpClientInstance = builder.build();
                }
            }
        }
        return okHttpClientInstance;
    }

    private OkHttpClient.Builder getClient(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
        //add log record
            //打印网络请求日志
//            LoggingInterceptor httpLoggingInterceptor = new LoggingInterceptor.Builder()
//                    .log(Platform.INFO)
//                    .request("请求")
//                    .response("响应")
//                    .build();
//            httpClientBuilder.addInterceptor(httpLoggingInterceptor);
        return httpClientBuilder;
    }

}
