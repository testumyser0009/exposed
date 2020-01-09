package com.fd.xposed;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.fd.xposed.bean.PingResponse;
import com.fd.xposed.bean.SubmitResponse;
import com.fd.xposed.broadcast.SubmitDataBroadcast;
import com.fd.xposed.event.PayMentDataEvent;
import com.fd.xposed.network.repository.FdKyAppDataRepository;
import com.fd.xposed.ui.LoginActivity;
import com.fd.xposed.util.Constant;
import com.fd.xposed.util.SPUtils;
import com.fd.xposed.xposed.boradcast.AlipayBroadcast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.fd.xposed.broadcast.SubmitDataBroadcast.submitData;
import static com.fd.xposed.xposed.AliParamUtils.ALIPAY_PACKAGE_NAME;


public class MainActivity extends AppCompatActivity {
    private Button mShouQianButton;
    private Button mGetCookieButton;
    private Button mUploadStatus;


    private Observable<Long> mObservable;

    private Disposable disposable = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("liunianprint", "MainActivity onCreate!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);


        mObservable = Observable.interval(0, 1, TimeUnit.SECONDS);
//        startCountDown();
        interval(15 * 1000);
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

    /** 每隔milliseconds毫秒后执行next操作
     *
     * @param milliseconds
     */
    public void interval(long milliseconds){
        Observable.interval(milliseconds, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {
                        disposable=disposable;
                    }

                    @Override
                    public void onNext(@NonNull Long number) {
                        Toast.makeText(MainActivity.this, "判断是否需要重新抓取", Toast.LENGTH_SHORT).show();
                        Ping();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                        Toast.makeText(MainActivity.this, "开始采集onComplete", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*
    * 心跳
    * */
    private void Ping () {
        String codeId = SPUtils.getSharedStringData(ASXApplication.getAppContext(), Constant.CodeId);
        String bankId = SPUtils.getSharedStringData(ASXApplication.getAppContext(), Constant.bankId);
        String token = SPUtils.getSharedStringData(ASXApplication.getAppContext(), Constant.Token);
        FdKyAppDataRepository.Ping(codeId, bankId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PingResponse>() {
                    @Override
                    public void accept(PingResponse pingResponse) throws Exception {
                        if (pingResponse.getStatus().equals("0")) {
                            Toast.makeText(MainActivity.this, pingResponse.getMessage() , Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (pingResponse.getResult().getNeed_data().equals("1")) {
                            Toast.makeText(MainActivity.this, "开始抓取", Toast.LENGTH_SHORT).show();
                            Intent broadCastIntent = new Intent();
                            broadCastIntent.setAction(AlipayBroadcast.BillPageAppRefreshBrodCast);
                            sendBroadcast(broadCastIntent);
                        } else {
                            Toast.makeText(MainActivity.this, "不需要抓取", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private void startCountDown () {
        //开始倒计时
        final int count = 15;//倒计时3秒id_close_video_audio_tips
        mObservable.take(count + 1)//限制发射次数（因为倒计时要显示 3 2 1 0 四个数字）
                //使用map将数字转换，这里aLong是从0开始增长的,所以减去aLong就会输出3 2 1 0这种样式
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        return count - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Long num) {
                        //接收到消息，这里需要判空，因为3秒倒计时中间如果页面结束了，会造成找不到 tvAdCountDown
                        Toast.makeText(MainActivity.this, "离下次抓取倒计时:" + num, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {


                    }

                    @Override
                    public void onComplete() {
                        Intent broadCastIntent = new Intent();
                        broadCastIntent.setAction(AlipayBroadcast.BillPageAppRefreshBrodCast);
                        sendBroadcast(broadCastIntent);
                        startCountDown();
                    }
                });
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
        if (null != disposable && !disposable.isDisposed()) {
            disposable.dispose();
        }

    }
}
