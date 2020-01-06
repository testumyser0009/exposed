package com.wz.xposedalipay.network.service;

import com.wz.xposedalipay.bean.LoginBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
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



}
