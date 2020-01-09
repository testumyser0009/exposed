package com.fd.xposed.xposed.boradcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fd.xposed.ASXApplication;


/**
 * Created by dell on 2018/4/4.
 */

public class PluginBroadcast extends BroadcastReceiver{
    public static String CONSULT_SET_AMOUNT_RES_STRING_INTENT_FILTER_ACTION = "com.eg.android.AlipayGphone.info.consultSetAmountResString";
    public static String COOKIE_STR_INTENT_FILTER_ACTION = "com.eg.android.AlipayGphone.info.cookieStr";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().contentEquals(CONSULT_SET_AMOUNT_RES_STRING_INTENT_FILTER_ACTION)) {
            ASXApplication.dealAlipayConsultSetAmountResString(context, intent);
        } else if (intent.getAction().contentEquals(COOKIE_STR_INTENT_FILTER_ACTION)) {
            ASXApplication.dealAlipayCookieStr(context, intent);
        }
    }
}
