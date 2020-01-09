package com.fd.xposed;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.fd.xposed.bean.SubmitResponse;
import com.fd.xposed.broadcast.SubmitDataBroadcast;
import com.fd.xposed.event.PayMentDataEvent;
import com.fd.xposed.network.repository.FdKyAppDataRepository;
import com.fd.xposed.util.Constant;
import com.fd.xposed.util.SPUtils;
import com.fd.xposed.xposed.boradcast.AlipayBroadcast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.fd.xposed.broadcast.SubmitDataBroadcast.submitData;
import static com.fd.xposed.xposed.AliParamUtils.ALIPAY_PACKAGE_NAME;


public class MainActivity extends AppCompatActivity {
    private Button mShouQianButton;
    private Button mGetCookieButton;
    private Button mUploadStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("liunianprint", "MainActivity onCreate!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        initBroadCast();

//        mShouQianButton = (Button) findViewById(R.id.shouqian);
//        mShouQianButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = getPackageManager().getLaunchIntentForPackage(ALIPAY_PACKAGE_NAME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//                Intent broadCastIntent = new Intent();
//                Random random = new Random();
//                broadCastIntent.putExtra("qr_money", String.valueOf(random.nextInt(100) + 1));
//                broadCastIntent.putExtra("beiZhu", "测试");
//                broadCastIntent.setAction(AlipayBroadcast.CONSULT_SET_AMOUNT_RES_STRING_INTENT_FILTER_ACTION);
//                sendBroadcast(broadCastIntent);
//            }
//        });
//
//        mGetCookieButton = (Button) findViewById(R.id.get_cookie);
//        mGetCookieButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = getPackageManager().getLaunchIntentForPackage(ALIPAY_PACKAGE_NAME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//                Intent broadCastIntent = new Intent();
//                broadCastIntent.setAction(AlipayBroadcast.COOKIE_STR_INTENT_FILTER_ACTION);
//                sendBroadcast(broadCastIntent);
//            }
//        });

        mUploadStatus = findViewById(R.id.upload_status);

    }

    private SubmitDataBroadcast submitDataBroadcast;
    private void initBroadCast() {
        submitDataBroadcast = new SubmitDataBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(submitData);
        this.registerReceiver(submitDataBroadcast, intentFilter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onEventJumpToLogin(PayMentDataEvent event) {
//        datas.addAll(clueHomeResponse.getData().getList());
        Toast.makeText(MainActivity.this, event.getData(), Toast.LENGTH_SHORT).show();
//        String codeId = SPUtils.getSharedStringData(MainActivity.this, Constant.CodeId);
//        String bankId = SPUtils.getSharedStringData(MainActivity.this, Constant.bankId);
//        String token = SPUtils.getSharedStringData(MainActivity.this, Constant.Token);
        mUploadStatus.setText(event.getData());
//        FdKyAppDataRepository.submitData(codeId, bankId, token, event.getData())
//                .subscribeOn(Schedulers.io())
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<SubmitResponse>() {
//                    @Override
//                    public void accept(SubmitResponse submitResponse) throws Exception {
//                        mUploadStatus.setText("上传成功");
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        mUploadStatus.setText("上传失败");
//                    }
//                });
    }


    public void launcherShouQianActivity() {
//        Random random = new Random();
//        PreferencesUtils.setBeiZu(this, "测试");
//        PreferencesUtils.setJinEr(this, String.valueOf(random.nextInt(100) + 1));
//        startActivity(this.getPackageManager().getLaunchIntentForPackage(ALIPAY_PACKAGE_NAME));
//        Intent intent = getPackageManager().getLaunchIntentForPackage(ALIPAY_PACKAGE_NAME);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
//        Log.i("liunianprint:", PreferencesUtils.getBeiZu());
//        Log.i("liunianprint:", PreferencesUtils.getJinEr());
    }

    public void jumpAlipay(){
//        RootShell rootShell = RootShell.open();
//        rootShell.execute("am start -n com.eg.android.AlipayGphone/com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity");
//        rootShell.close();
//        ShellUtils.CommandResult commandResult = ShellUtils.execCmd("am start -n com.eg.android.AlipayGphone/com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", true);
//        Log.i("liunianprint:", commandResult.result + "-------" + commandResult.errorMsg + "-------" + commandResult.successMsg);
//        try{
//            Intent in=new Intent();
////            in.addCategory(Intent.CATEGORY_LAUNCHER);
//            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//            ComponentName cn =new ComponentName(ALIPAY_PACKAGE_NAME,"com.eg.android.AlipayGphone.AlipayLogin");
//            in.setComponent(cn);
//            this.startActivity(in);
//        } catch (Exception e){
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        this.unregisterReceiver(submitDataBroadcast);
    }
}
