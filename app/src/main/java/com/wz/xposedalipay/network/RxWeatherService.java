package com.wz.xposedalipay.network;

import com.wz.xposedalipay.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RxWeatherService {
    /*登录接口*/
//    @Headers({"url_name:base_host","Content-Type:application/json; charset=utf-8"})
    @Headers({"url_name:base_host"})
    @POST("api/device/userLoginMobile")
    Observable<LoginBean> login(@Query("code_id") String code_id,
                                @Query("account") String account,
                                @Query("password") String password);
}