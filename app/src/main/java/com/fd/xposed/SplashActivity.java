package com.fd.xposed;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.fd.xposed.ui.LoginActivity;
import com.fd.xposed.util.Constant;
import com.fd.xposed.util.SPUtils;


public class SplashActivity extends AppCompatActivity {


    private Handler handler=new Handler();
    private Runnable runnable;
    private int countdown = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //不显示程序的标题栏
        requestWindowFeature( Window.FEATURE_NO_TITLE );

        //不显示系统的标题栏
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView(R.layout.act_splash);
        initRunnable();
    }

    private void initRunnable () {
        runnable = new Runnable() {
            @Override
            public void run() {
                countdown--;
                Log.i("test1","countdown = " + countdown);
                if(countdown==0){
                    handler.removeCallbacks(runnable);
                    String token = SPUtils.getSharedStringData(SplashActivity.this, Constant.Token);
                    Intent intent = new Intent();
                    if (null == token || token.equals("")) {
                        intent.setClass(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
//                        ARouterPath.JumpCallBack(ARouterPath.LoginPath, SplashActivity.this, 0, false);
                    } else {
                        intent.setClass(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
//                        ARouterPath.JumpCallBack(ARouterPath.MainPath, SplashActivity.this, 0, false);
                    }
                    finish();
                }else{
                    handler.postDelayed(runnable,1 * 1000);
                }

            }
        };
        handler.postDelayed(runnable, 2 * 1000);
    }
}
