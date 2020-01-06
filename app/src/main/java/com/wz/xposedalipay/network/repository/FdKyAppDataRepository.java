package com.wz.xposedalipay.network.repository;

import com.wz.xposedalipay.bean.LoginBean;
import com.wz.xposedalipay.network.ApiClient;
import com.wz.xposedalipay.util.ApiConstants;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

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

}
