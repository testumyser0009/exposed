package com.fd.xposed.xposed.boradcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import com.fd.xposed.ASXApplication;
import com.fd.xposed.MainActivity;
import com.fd.xposed.bean.SubmitResponse;
import com.fd.xposed.event.PayMentDataEvent;
import com.fd.xposed.network.repository.FdKyAppDataRepository;
import com.fd.xposed.util.Constant;
import com.fd.xposed.util.SPUtils;
import com.fd.xposed.xposed.Main1;

import org.greenrobot.eventbus.EventBus;

import de.robv.android.xposed.XposedHelpers;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.fd.xposed.broadcast.SubmitDataBroadcast.submitData;
import static de.robv.android.xposed.XposedBridge.log;

/**
 * Created by dell on 2018/4/4.
 */

public class AlipayBroadcast extends BroadcastReceiver{

    public static String Alipay_Pay_Account = "com.hhly.pay.alipay.info.payAccount";

    public static String CONSULT_SET_AMOUNT_RES_STRING_INTENT_FILTER_ACTION = "com.hhly.pay.alipay.info.consultSetAmountResString";

    public static String COOKIE_STR_INTENT_FILTER_ACTION = "com.hhly.pay.alipay.info.cookieStr";
    @Override
    public void onReceive(Context context, Intent intent) {
        log("777777777777777777777777777777777777");
        if (intent.getAction().contentEquals(CONSULT_SET_AMOUNT_RES_STRING_INTENT_FILTER_ACTION)) {
            log("8888888888888888888888");
            String qr_money = intent.getStringExtra("qr_money");
            String beiZhu = intent.getStringExtra("beiZhu");
            log("AlipayBroadcast onReceive " + qr_money + " " + beiZhu + "\n");
            if (!qr_money.contentEquals("")) {
                Intent launcherIntent = new Intent(context, XposedHelpers.findClass("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", Main1.launcherActivity.getApplicationContext().getClassLoader()));
                launcherIntent.putExtra("qr_money", qr_money);
                launcherIntent.putExtra("beiZhu", beiZhu);
                Main1.launcherActivity.startActivity(launcherIntent);
            }
        } else if (intent.getAction().contentEquals(COOKIE_STR_INTENT_FILTER_ACTION)) {
            log("999999999999999999999999999999");
            String cookieStr = Main1.getCookieStr();
            log("AlipayBroadcast onReceive getCookieStr " + cookieStr + "\n");
            Intent broadCastIntent = new Intent();
            broadCastIntent.putExtra("cookieStr", cookieStr);
            broadCastIntent.setAction(PluginBroadcast.COOKIE_STR_INTENT_FILTER_ACTION);
            context.sendBroadcast(broadCastIntent);
        } else if (intent.getAction().contentEquals(Alipay_Pay_Account)) {
            log("aaaaaaaaaaaaaaaaaaaaaaaaaa");

            Intent broadCastIntent = new Intent();
            log("bbbbbbbbbbbbbbbbbbb");
            log("sumitData ================ " + intent.getStringExtra("sumitData"));
            broadCastIntent.putExtra("sumitData", intent.getStringExtra("sumitData"));
            log("cccccccccccccccccccccccccc");
            broadCastIntent.setAction(submitData);
            log("ddddddddddddddddddddddddddd");
//            Activity activity = (Activity) param.thisObject;
            log("eeeeeeeeeeeeeeeeeeeeeeeeeee");
            context.sendBroadcast(broadCastIntent);
            log("ffffffffffffffffffffffffffffffffff");



        }
    }
}
