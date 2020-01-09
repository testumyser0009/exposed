package com.fd.xposed;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;


public class ASXApplication extends Application {

//    private Typeface iconTypeFace;
    @Override
    public void onCreate() {
        super.onCreate();

//        iconTypeFace = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        baseApplication = this;
//        Fresco.initialize(this);
////        if (Utils.isAppDebug()) {
////            //开启InstantRun之后，一定要在ARouter.init之前调用openDebug
////            ARouter.openDebug();
////            ARouter.openLog();
////        }
//        ARouter.init(this);
//
////        JPushInterface.setDebugMode(true);
////        JPushInterface.init(this);
//
//
//        CrashReport.initCrashReport(getApplicationContext(), "47bfe9ba73", true);
    }

    private static ASXApplication baseApplication;


    public static Context getAppContext() {
        return baseApplication;
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
    }





    public static void dealAlipayConsultSetAmountResString(Context context, Intent intent) {
//        String consultSetAmountResString = intent.getStringExtra("consultSetAmountResString");
//        String cookieStr = intent.getStringExtra("cookieStr");
//        String toastString = consultSetAmountResString + " " + cookieStr;
//        Log.i("liunianprint:", toastString);
//        Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show();
//        Intent startIntent = new Intent(context, MainActivity.class);
//        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(startIntent);
    }

    public static void dealAlipayCookieStr(Context context, Intent intent) {
//        String cookieStr = intent.getStringExtra("cookieStr");
//        String toastString = cookieStr;
//        Log.i("liunianprint:", toastString);
//        Toast.makeText(context, toastString, Toast.LENGTH_SHORT).show();
//        Intent startIntent = new Intent(context, MainActivity.class);
//        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(startIntent);
    }
}
