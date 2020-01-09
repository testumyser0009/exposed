package com.fd.xposed.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fd.xposed.ASXApplication;
import com.fd.xposed.bean.SubmitResponse;
import com.fd.xposed.event.PayMentDataEvent;
import com.fd.xposed.network.repository.FdKyAppDataRepository;
import com.fd.xposed.util.Constant;
import com.fd.xposed.util.SPUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by dell on 2018/4/4.
 */

public class SubmitDataBroadcast extends BroadcastReceiver{
    public static String submitData = "com.submit.data";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().contentEquals(submitData)) {


            String codeId = SPUtils.getSharedStringData(ASXApplication.getAppContext(), Constant.CodeId);
            String bankId = SPUtils.getSharedStringData(ASXApplication.getAppContext(), Constant.bankId);
            String token = SPUtils.getSharedStringData(ASXApplication.getAppContext(), Constant.Token);
            String data = intent.getStringExtra("sumitData");
            EventBus.getDefault().post(new PayMentDataEvent("数据上传中。。。"));
            FdKyAppDataRepository.submitData(codeId, bankId, token, data)
                    .subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<SubmitResponse>() {
                        @Override
                        public void accept(SubmitResponse submitResponse) throws Exception {
//                            mUploadStatus.setText("上传成功");
                            EventBus.getDefault().post(new PayMentDataEvent("上传成功"));
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
//                            mUploadStatus.setText("上传失败");
                            EventBus.getDefault().post(new PayMentDataEvent("上传失败"));
                        }
                    });
        }
    }
}
