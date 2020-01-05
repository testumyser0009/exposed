package com.wz.xposedalipay;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import java.lang.reflect.Field;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;
import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

public class HookLogic implements IXposedHookLoadPackage {

    private Activity mLauncherActivity;

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        log("HookLogic >> current package:" + lpparam.packageName);
        log("HookLogic 000000000000000000:");

        if (AliParamUtils.ALIPAY_PACKAGE_NAME.equals(lpparam.packageName)) {


            XposedHelpers.findAndHookMethod(Application.class,
                    "attach",
                    Context.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                            log("HookLogic 111111111111111111111111:");
                            super.afterHookedMethod(param);
                            Context context = (Context) param.args[0];
                            ClassLoader appClassLoader = context.getClassLoader();
                            securityCheckHook(appClassLoader);
                        }
                    });


            // hook 支付宝主界面的onCreate方法，获得主界面对象并注册广播
            XposedHelpers.findAndHookMethod(AliParamUtils.mLauncherActivityClassfullName,  lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    XposedLogUtils.log(AliParamUtils.mLauncherActivityClassfullName + ":onCreated方法");
                    log("AliParamUtils.mLauncherActivityClassfullName");
                    mLauncherActivity = (Activity) param.thisObject;

                    if (AliParamUtils.mBillListActivityIsFromSoLib) {
                        // 打开账单页面，并加载其对应的库
                        callStaticMethod(findClass("com.alipay.android.phone.home.manager.LauncherAppUtils",  lpparam.classLoader), "a", "20000003", null);
                    }
                }
            });


            // hook BundleClassLoader构造方法，获得so库对应的classloader并hook来自so库中的类
//            XposedHelpers.findAndHookConstructor("com.alipay.mobile.quinox.classloader.BundleClassLoader",  lpparam.classLoader,
//                    ClassLoader.class,
//                    findClass("com.alipay.mobile.quinox.bundle.Bundle",  lpparam.classLoader),
//                    findClass("com.alipay.mobile.quinox.bundle.BundleManager",  lpparam.classLoader),
//                    findClass("com.alipay.mobile.quinox.classloader.HostClassLoader",  lpparam.classLoader),
//                    new XC_MethodHook() {
//                        @Override
//                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                            try {
//                                if (param.args[1] != null) {
//                                    String soLibName = (String) XposedHelpers.callMethod(param.args[1], AliParamUtils.mBundleGetLocationMethodName); // 获得so库名称
//                                    if (soLibName != null) {
//                                        if (soLibName.contains("wallet-billlist")) { // 账单页面
//                                            mBillListActivityClassLoader = (ClassLoader) param.thisObject;
//                                            hookBillListActivityMethod();
//                                        }
//                                    }
//                                }
//                            } catch (Exception e) {
//                            }
//                        }
//                    });

            XposedHelpers.findAndHookMethod("com.alipay.mobile.bill.list.ui.adapter.BillListAdapter", lpparam.classLoader, "a", List.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    log("com.alipay.mobile.bill.list.ui.adapter.BillListAdapter" + "a called" + "\n");
                    log("com.alipay.mobile.bill.list.ui.adapter.BillListAdapter");
                    if (param.args[0] != null) {
                        Field billListFiled = XposedHelpers.findField(param.thisObject.getClass(), "a"); // 通过反射找到账单数据列表对应的字段
                        final Object billList = billListFiled.get(param.thisObject); // 获得账单数据列表
                        List<Object> bList_obj = (List) billList;
                        if (bList_obj != null) {
                            log(bList_obj.toString());
//                            sendBillListBroadCast(bList_obj); // 将账单数据列表通过广播发送回给插件程序
                        }
                    }
                }
            });

            /*找到支付宝账单对应的activity*/
//            XposedHelpers.findAndHookMethod("com.alipay.mobile.bill.list.ui.BillListActivity_",  lpparam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//
//                }
//            });
//
//            XposedHelpers.findAndHookMethod(Activity.class, "onCreate", Bundle.class, new XC_MethodHook() {
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    if (param.thisObject != null && param.thisObject.getClass().getName().contentEquals("com.alipay.mobile.bill.list.ui.BillListActivity_")) {
//                    }
//                }
//            });

        }

    }

    // 解决支付宝的反hook
    private void securityCheckHook(ClassLoader classLoader) {
        try {
            Class securityCheckClazz = findClass("com.alipay.mobile.base.security.CI", classLoader);
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
                protected Object replaceHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
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

}