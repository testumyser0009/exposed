package com.wz.xposedalipay.network.repository;

import com.wz.xposedalipay.bean.LoginBean;
import com.wz.xposedalipay.network.ApiClient;
import com.wz.xposedalipay.util.ApiConstants;

import io.reactivex.Observable;

/**
 * Created by dxx on 2017/11/8.
 */

public class FdKyAppDataRepository {

    /*登陆*/
    public static Observable<LoginBean> Login(String loginName, String password, String codeId){


        // 2表示是App端注册
        Observable<LoginBean> observableForGetAndroidDataFromNetWork = ApiClient.getFdKyappDataService(ApiConstants.GankHost).login(codeId, loginName, password);

        //可以操作Observable来筛选网络或者是本地数据

        return observableForGetAndroidDataFromNetWork;
    }

//    /*心跳*/
//    public static Observable<PingResponse> Ping(String codeId, String bankId, String token){
//
//
//        // 2表示是App端注册
//        Observable<PingResponse> observableForGetAndroidDataFromNetWork = ApiClient.getFdKyappDataService(ApiConstants.GankHost).ping(codeId, bankId, token);
//
//        //可以操作Observable来筛选网络或者是本地数据
//
//        return observableForGetAndroidDataFromNetWork;
//    }
//
//    /*心跳*/
//    public static Observable<SubmitResponse> submitData(String codeId, String bankId, String token, String data){
//
//
//        // 2表示是App端注册
//        Observable<SubmitResponse> observableForGetAndroidDataFromNetWork = ApiClient.getFdKyappDataService(ApiConstants.GankHost).submitData(codeId, bankId, token,"1", data);
//
//        //可以操作Observable来筛选网络或者是本地数据
//
//        return observableForGetAndroidDataFromNetWork;
//    }
}
