package com.fd.xposed.xposed;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.widget.Button;


import com.fd.xposed.param.SubmitData;
import com.fd.xposed.xposed.boradcast.AlipayBroadcast;
import com.fd.xposed.xposed.boradcast.PluginBroadcast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static com.fd.xposed.xposed.AliParamUtils.ALIPAY_PACKAGE_NAME;
import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;


public class Main1 implements IXposedHookLoadPackage {
    public static Activity launcherActivity = null;
    private static AlipayBroadcast alipayBroadcast = null;
    private static LoadPackageParam m_lpparam = null;
    private static ClassLoader mBillListActivityClassLoader;

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        log("xposed packagename111111111 = " + lpparam.packageName);
        if (lpparam.appInfo == null || (lpparam.appInfo.flags & (ApplicationInfo.FLAG_SYSTEM |
                ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)) != 0) {
            return;
        }
        log("xposed packagename222222 = " + lpparam.packageName);
        final String packageName = lpparam.packageName;

        if (packageName.equals(ALIPAY_PACKAGE_NAME)) {
            m_lpparam = lpparam;
            XposedHelpers.findAndHookMethod(Application.class,
                    "attach",
                    Context.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            super.afterHookedMethod(param);
                            Context context = (Context) param.args[0];
                            ClassLoader appClassLoader = context.getClassLoader();
                            securityCheckHook(appClassLoader);
                        }
                    });

            // hook 支付宝主界面的onCreate方法，获得主界面对象并注册广播
            findAndHookMethod("com.alipay.mobile.quinox.LauncherActivity", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    log("com.alipay.mobile.quinox.LauncherActivity onCreated" + "\n");
                    launcherActivity = (Activity) param.thisObject;

//                    if (AliParamUtils.mBillListActivityIsFromSoLib) {
//                        log("com.alipay.mobile.quinox.LauncherActivity onCreated222222222222222222222222222222222" + "\n");
//                        // 打开账单页面，并加载其对应的库
//                        callStaticMethod(findClass("com.alipay.android.phone.home.manager.LauncherAppUtils", lpparam.classLoader), "a", "20000003", null);
//                    }


                    alipayBroadcast = new AlipayBroadcast();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction(AlipayBroadcast.Alipay_Pay_Account);
                    intentFilter.addAction(AlipayBroadcast.CONSULT_SET_AMOUNT_RES_STRING_INTENT_FILTER_ACTION);
                    intentFilter.addAction(AlipayBroadcast.COOKIE_STR_INTENT_FILTER_ACTION);
                    launcherActivity.registerReceiver(alipayBroadcast, intentFilter);
                }
            });

            // hook 支付宝的主界面的onDestory方法，销毁广播
            findAndHookMethod("com.alipay.mobile.quinox.LauncherActivity", lpparam.classLoader, "onDestroy", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    log("com.alipay.mobile.quinox.LauncherActivity onDestroy" + "\n");
                    if (alipayBroadcast != null) {
                        ((Activity) param.thisObject).unregisterReceiver(alipayBroadcast);
                    }
                    launcherActivity = null;
                }
            });

            // hook设置金额和备注的onCreate方法，自动填写数据并点击
            findAndHookMethod("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    log("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity onCreated" + "\n");
                    Field jinErField = XposedHelpers.findField(param.thisObject.getClass(), "b");
                    final Object jinErView = jinErField.get(param.thisObject);
                    Field beiZhuField = XposedHelpers.findField(param.thisObject.getClass(), "c");
                    final Object beiZhuView = beiZhuField.get(param.thisObject);
                    Intent intent = ((Activity) param.thisObject).getIntent();
                    String jinEr = intent.getStringExtra("qr_money");
                    String beiZu = intent.getStringExtra("beiZhu");
                    log("JinEr:" + jinEr + "\n");
                    log("BeiZu:" + beiZu + "\n");
                    XposedHelpers.callMethod(jinErView, "setText", jinEr);
                    XposedHelpers.callMethod(beiZhuView, "setText", beiZu);

                    Field quRenField = XposedHelpers.findField(param.thisObject.getClass(), "e");
                    final Button quRenButton = (Button) quRenField.get(param.thisObject);
                    quRenButton.performClick();
                }
            });

            // hook获得二维码url的回调方法
            findAndHookMethod("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity", lpparam.classLoader, "a",
                    findClass("com.alipay.transferprod.rpc.result.ConsultSetAmountRes", lpparam.classLoader), new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    log("com.alipay.mobile.payee.ui.PayeeQRSetMoneyActivity a" + "\n");
                    String cookieStr = getCookieStr();
                    Object consultSetAmountRes = param.args[0];
                    String consultSetAmountResString = "";
                    if (consultSetAmountRes != null) {
                        consultSetAmountResString = (String) callMethod(consultSetAmountRes, "toString");
                    }
                    Intent broadCastIntent = new Intent();
                    broadCastIntent.putExtra("consultSetAmountResString", consultSetAmountResString);
                    broadCastIntent.putExtra("cookieStr", cookieStr);
                    broadCastIntent.setAction(PluginBroadcast.CONSULT_SET_AMOUNT_RES_STRING_INTENT_FILTER_ACTION);
                    Activity activity = (Activity) param.thisObject;
                    activity.sendBroadcast(broadCastIntent);
                    log("consultSetAmountResString:" + consultSetAmountResString + "\n");
                    log("cookieStr:" + cookieStr + "\n");
                }
            });

            // hook 支付宝主界面的onCreate方法，获得主界面对象并注册广播
//            findAndHookMethod(AliParamUtils.mLauncherActivityClassfullName, lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    log(AliParamUtils.mLauncherActivityClassfullName + ":3333333onCreated方法");
//                    launcherActivity = (Activity) param.thisObject;
//
//                    if (AliParamUtils.mBillListActivityIsFromSoLib) {
//                        // 打开账单页面，并加载其对应的库
//                        callStaticMethod(findClass("com.alipay.android.phone.home.manager.LauncherAppUtils", lpparam.classLoader), "a", "20000003", null);
//                    }
//                }
//            });

            findAndHookMethod(Activity.class, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    log("ClassLoader000 ============ " + param.thisObject.getClass().getClassLoader().toString());
                    log("ClassLoader222 ============ " + param.thisObject.getClass().getName());
                    if (param.thisObject != null && param.thisObject.getClass().getName().contentEquals("com.alipay.mobile.bill.list.ui.BillListActivity_")) {
                        log("ClassLoader111 ============ " + param.thisObject.getClass().getClassLoader().toString());
                        XposedHelpers.findAndHookMethod("com.alipay.mobile.bill.list.ui.adapter.BillListAdapter", param.thisObject.getClass().getClassLoader(), "a", List.class, new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                log("com.alipay.mobile.bill.list.ui.adapter.BillListAdapter" + "a called" + "\n");
                                log("com.alipay.mobile.bill.list.ui.adapter.BillListAdapter");
                                if (param.args[0] != null) {
                                    Field billListFiled = XposedHelpers.findField(param.thisObject.getClass(), "a"); // 通过反射找到账单数据列表对应的字段
                                    final Object billList = billListFiled.get(param.thisObject); // 获得账单数据列表
                                    List<Object> bList_obj = (List) billList;
                                    if (bList_obj != null) {
//                                        log(bList_obj.toString());
                                        sendBillListBroadCast(bList_obj); // 将账单数据列表通过广播发送回给插件程序
//                                        EventBus.getDefault().post(new PayMentDataEvent());
                                    }
                                }
                            }
                        });
                    }
                }
            });

            // hook 支付宝主界面的onCreate方法，获得主界面对象并注册广播
//            findAndHookMethod(AliParamUtils.mLauncherActivityClassfullName, lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    log("22222222222222222222222222222222222222222222222222222222222222222222222222222222222222");
//
//                    if (AliParamUtils.mBillListActivityIsFromSoLib) {
//                        log("33333333333333333333333333333");
//                        // 打开账单页面，并加载其对应的库
//                        callStaticMethod(findClass("com.alipay.android.phone.home.manager.LauncherAppUtils", lpparam.classLoader), "a", "20000003", null);
//                    }
//                }
//            });

//            XposedHelpers.findAndHookMethod("com.alipay.mobile.bill.list.ui.adapter.BillListAdapter", mBillListActivityClassLoader, "a", List.class, new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    log("com.alipay.mobile.bill.list.ui.adapter.BillListAdapter" + "a called" + "\n");
//                    log("com.alipay.mobile.bill.list.ui.adapter.BillListAdapter");
//                    if (param.args[0] != null) {
//                        Field billListFiled = XposedHelpers.findField(param.thisObject.getClass(), "a"); // 通过反射找到账单数据列表对应的字段
//                        final Object billList = billListFiled.get(param.thisObject); // 获得账单数据列表
//                        List<Object> bList_obj = (List) billList;
//                        if (bList_obj != null) {
//                            log(bList_obj.toString());
////                            sendBillListBroadCast(bList_obj); // 将账单数据列表通过广播发送回给插件程序
//                        }
//                    }
//                }
//            });


            // hook BundleClassLoader构造方法，获得so库对应的classloader并hook来自so库中的类
//            findAndHookConstructor("com.alipay.mobile.quinox.classloader.BundleClassLoader", lpparam.classLoader,
//                    ClassLoader.class,
//                    findClass("com.alipay.mobile.quinox.bundle.Bundle", lpparam.classLoader),
//                    findClass("com.alipay.mobile.quinox.bundle.BundleManager", lpparam.classLoader),
//                    findClass("com.alipay.mobile.quinox.classloader.HostClassLoader", lpparam.classLoader),
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            log("param.args[1]222222222222222222222 =================== " + param.args[1]);
//                            try {
//                                if (param.args[1] != null) {
//                                    log("param.args[1] =================== " + param.args[1]);
//                                    String soLibName = (String) XposedHelpers.callMethod(param.args[1], AliParamUtils.mBundleGetLocationMethodName); // 获得so库名称
//                                    log("soLibName =================== " + soLibName);
//                                    if (soLibName != null) {
//                                        if (soLibName.contains("wallet-billlist")) {
//                                            mBillListActivityClassLoader = (ClassLoader) param.thisObject;
////                                            XposedLogUtils.log("账单页面classloader: " + mBillListActivityClassLoader.toString());
////                                            hookBillListActivityMethod();
//                                        }
//                                    }
//                                }
//                            } catch (Exception e) {
//                            }
//                        }
//                    });
        }
    }

    public static String getCookieStr() {
        String cookieStr = "";
        // 获得cookieStr
        if (m_lpparam != null) {
            callStaticMethod(findClass("com.alipay.mobile.common.transportext.biz.appevent.AmnetUserInfo", Main1.m_lpparam.classLoader), "getSessionid");
            Context context = (Context) callStaticMethod(findClass("com.alipay.mobile.common.transportext.biz.shared.ExtTransportEnv", Main1.m_lpparam.classLoader), "getAppContext");
            if (context != null) {
                Object readSettingServerUrl = callStaticMethod(findClass("com.alipay.mobile.common.helper.ReadSettingServerUrl", Main1.m_lpparam.classLoader), "getInstance");
                if (readSettingServerUrl != null) {
                    String gWFURL = (String) callMethod(readSettingServerUrl, "getGWFURL", context);
                    cookieStr = (String) callStaticMethod(findClass("com.alipay.mobile.common.transport.http.GwCookieCacheHelper", Main1.m_lpparam.classLoader), "getCookie", gWFURL);
                }
            }
        }
        return cookieStr;
    }

    // 解决支付宝的反hook
    private void securityCheckHook(ClassLoader classLoader) {
        try {
            Class securityCheckClazz = XposedHelpers.findClass("com.alipay.mobile.base.security.CI", classLoader);
            XposedHelpers.findAndHookMethod(securityCheckClazz, "a", String.class, String.class, String.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Object object = param.getResult();
                    XposedHelpers.setBooleanField(object, "a", false);
                    param.setResult(object);
                    super.afterHookedMethod(param);
                }
            });

            XposedHelpers.findAndHookMethod(securityCheckClazz, "a", Class.class, String.class, String.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return (byte) 1;
                }
            });
            XposedHelpers.findAndHookMethod(securityCheckClazz, "a", ClassLoader.class, String.class, new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return (byte) 1;
                }
            });
            XposedHelpers.findAndHookMethod(securityCheckClazz, "a", new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                    return false;
                }
            });

        } catch (Error | Exception e) {
            e.printStackTrace();
        }
    }





    /**
     * 发送账单数据广播
     *
     * @param objectList
     */
    private void sendBillListBroadCast(List<Object> objectList) throws IllegalAccessException {
        boolean isFound = false; // 是否查到相应位置
        boolean isLast = false;  // 已是最新数据
        int invalidCount = 0; //
        ArrayList<BillObject> billObjectList = new ArrayList<>();
        List<SubmitData.DataBean> dataBeanList = new ArrayList<>();


        log("objectList size111:" + objectList.size());

        for (int i = 0; i < objectList.size(); i++) {
            Object obj = objectList.get(i);
            BillObject billObject = new BillObject();
            billObject.bizInNo = getStringField(obj, "bizInNo");
            billObject.bizStateDesc = getStringField(obj, "bizStateDesc");
            billObject.bizSubType = getStringField(obj, "bizSubType");
            billObject.canDelete = getStringField(obj, "canDelete");
            billObject.bizType = getStringField(obj, "bizType");
            billObject.consumeFee = getStringField(obj, "consumeFee");
            billObject.consumeStatus = getStringField(obj, "consumeStatus");
            billObject.consumeTitle = getStringField(obj, "consumeTitle");
            billObject.createDesc = getStringField(obj, "createDesc");
            billObject.createTime = getStringField(obj, "createTime");
            billObject.destinationUrl = getStringField(obj, "destinationUrl");
            billObject.gmtCreate = getStringField(obj, "gmtCreate");
            billObject.isAggregatedRec = getStringField(obj, "isAggregatedRec");
            billObject.memo = getStringField(obj, "memo");
            billObject.month = getStringField(obj, "month");
            billObject.oppositeLogo = getStringField(obj, "oppositeLogo");
            billObject.oppositeMemGrade = getStringField(obj, "oppositeMemGrade");
            billObject.sceneId = getStringField(obj, "sceneId");
            billObject.categoryName = getStringField(obj, "categoryName");

            log("bizInNo：" + billObject.bizInNo +
                    ", bizStateDesc:" + billObject.bizStateDesc +
                    ", bizSubType:" + billObject.bizSubType +
                    ", canDelete:" + billObject.canDelete +
                    ", bizType:" + billObject.bizType +
                    ", consumeFee:" + billObject.consumeFee +
                    ", consumeStatus:" + billObject.consumeStatus +
                    ", consumeTitle:" + billObject.consumeTitle +
                    ", createDesc:" + billObject.createDesc +
                    ", createTime:" + billObject.createTime +
                    ", destinationUrl:" + billObject.destinationUrl +
                    ", isAggregatedRec:" + billObject.isAggregatedRec +
                    ", memo:" + billObject.memo +
                    ", month:" + billObject.month +
                    ", oppositeLogo:" + billObject.oppositeLogo +
                    ", oppositeMemGrade:" + billObject.oppositeMemGrade +
                    ", sceneId:" + billObject.sceneId +
                    ", categoryName:" + billObject.categoryName);

//            log("consumeTitle：" + billObject.consumeTitle +
//                    ", consumeFee:" + billObject.consumeFee +
//                    ", createDesc:" + billObject.createDesc +
//                    ", createTime:" + billObject.createTime +
//                    ", categoryName:" + billObject.categoryName);
            billObjectList.add(billObject);

//            SubmitData.DataBean dataBean = new SubmitData.DataBean();
//            dataBean.setTrading_time("1576930080663");
//            dataBean.setOrder_money(billObject.consumeFee);
//            dataBean.setTransfer_type(billObject.categoryName);
//            dataBean.setReciprocal_name(billObject.consumeTitle);
//            dataBeanList.add(dataBean);
        }
//        if (dataBeanList.size() > 0) {
//            EventBus.getDefault().post(new PayMentDataEvent(JSON.toJSONString(dataBeanList)));
//        }
    }

    private String getStringField(final Object obj, final String fieldName) throws IllegalAccessException {
        Field sField = XposedHelpers.findField(obj.getClass(), fieldName);
        if (sField == null) {
            return null;
        }
        return String.valueOf(sField.get(obj));
    }
}
