package com.fd.xposed.network.service;

import com.fd.xposed.bean.LoginBean;
import com.fd.xposed.bean.SubmitResponse;

import io.reactivex.Observable;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by dxx on 2017/11/8.
 */

public interface FdKyAppDataService {

    /*登录接口*/
//    @Headers({"url_name:base_host","Content-Type:application/json; charset=utf-8"})
    @Headers({"url_name:base_host"})
    @POST("api/device/userLoginMobile")
    Observable<LoginBean> login(@Query("code_id") String code_id,
                                @Query("account") String account,
                                @Query("password") String password);

//    /*心跳接口*/
////    @Headers({"url_name:base_host","Content-Type:application/json; charset=utf-8"})
//    @Headers({"url_name:base_host"})
//    @POST("api/device/ping")
//    Observable<PingResponse> ping(@Query("code_id") String code_id,
//                                  @Query("bank_id") String bank_id,
//                                  @Query("token") String token);
//
//
    /*账单数据提交*/
//    @Headers({"url_name:base_host","Content-Type:application/json; charset=utf-8"})
    @Headers({"url_name:base_host"})
    @POST("/api/device/postDataMobile")
    Observable<SubmitResponse> submitData(@Query("code_id") String code_id,
                                          @Query("bank_id") String bank_id,
                                          @Query("token") String token,
                                          @Query("page") String page,
                                          @Query("data") String data);

}
