package com.wz.xposedalipay;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wz.xposedalipay.common.base.Utils;

/**
 * Created by dell on 2018/4/4.
 */

public class App extends Application {
    private static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();

//        if (Utils.isAppDebug()) {
//            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
//            ARouter.openDebug();
//            ARouter.openLog();
//        }
        ARouter.init(this);


        mApp = this;
    }

    public static Context getAppContext() {
        return baseApplication;
    }


    private static App baseApplication;

    public static void dealAlipayConsultSetAmountResString(Context context, Intent intent) {
        String consultSetAmountResString = intent.getStringExtra("consultSetAmountResString");
        String cookieStr = intent.getStringExtra("cookieStr");
        String toastString = consultSetAmountResString + " " + cookieStr;
        Log.i("liunianprint:", toastString);
        Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show();
        Intent startIntent = new Intent(context, MainActivity.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(startIntent);
    }

    public static void dealAlipayCookieStr(Context context, Intent intent) {
        String cookieStr = intent.getStringExtra("cookieStr");
        String toastString = cookieStr;
        Log.i("liunianprint:", toastString);
        Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show();
        Intent startIntent = new Intent(context, MainActivity.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(startIntent);
    }
}
