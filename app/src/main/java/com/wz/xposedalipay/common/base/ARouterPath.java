package com.wz.xposedalipay.common.base;

import android.app.Activity;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wz.xposedalipay.R;


/**
 * Created by danxx on 2017/11/27.
 * 路由path
 *
 * Aty : Activity
 * Fgt : Fragment
 *
 */

public class ARouterPath {

    /**登陆路径*/
    public static final String MainPath = "/main/path";
    /**登陆路径*/
    public static final String LoginPath = "/login/path";
    /**线索任务编辑页面Activity*/
    public static final String ClueTaskEdit = "/clue/task/edit";
    /*线索任务申请界面*/
    public static final String ClueTaskApply = "/clue/task/apply";
    /*任务详情有数据*/
    public static final String ClueTaskDetail = "/clue/task/detail";
    /*任务详情无数据*/
    public static final String ClueTaskDetailNoData = "/clue/task/detail/nodata";
    /*勘验点数据编辑*/
    public static final String ClueTaskky = "/clue/task/ky";
    /*勘验点指标*/
    public static final String KanYanDianTag = "/kyd/tag";
    /*勘验线索详情*/
    public static final String KanYanClueDetail = "/kyd/clue/detail";
    /*设备管理列表页*/
    public static final String DeviceManagerList = "/device/manager/list";
    /*添加设备界面*/
    public static final String AddDevice = "/add/device";
    /*图片预览界面*/
    public static final String previewImageAct = "/preview/image/act";
    /*线索详情界面*/
    public static final String clueDetailAct = "/clue/detail/act";
    /*关于我们*/
    public static final String aboutus = "/about/us/act";
    /*
    * 音视频接收画面
    * */
    public static final String videoaccept = "/video/accept/act";
    public static final String videoaccept1 = "/video/accept/act1";

    /*
    * 页面跳转
    * path 跳转路径
    * activity 上下文
    * requestCode 请求码
    * */
    public static void JumpCallBack(String path, final Activity activity, int requestCode, final Boolean isCloseCurrentPage) {
        ARouter.getInstance()
                .build(path)
                .withTransition(R.anim.activity_up_in, R.anim.activity_up_out)
                .navigation(activity, requestCode, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        Log.i("danxx", "ARouter onFound 找到跳转匹配路径");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        Log.i("danxx", "ARouter onLost 没有匹配到跳转路径");
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        Log.i("danxx", "ARouter onArrival 成功跳转");
                        if (isCloseCurrentPage) {
                            activity.finish();
                        }
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        Log.i("danxx", "ARouter onInterrupt 跳转被中断");
                    }
                });
    }

    /*
    * 页面跳转传参
    * */
    public static void JumpCallBackParam(String path, final Activity activity, int requestCode, String paramKey, String paramValue, final Boolean isCloseCurrentPage) {
        ARouter.getInstance()
                .build(path)
                .withString(paramKey,paramValue)
                .withTransition(R.anim.activity_up_in, R.anim.activity_up_out)
                .navigation(activity, requestCode, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        Log.i("danxx", "ARouter onFound 找到跳转匹配路径");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        Log.i("danxx", "ARouter onLost 没有匹配到跳转路径");
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        if (isCloseCurrentPage) {
                            activity.finish();
                        }
                        Log.i("danxx", "ARouter onArrival 成功跳转");
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        Log.i("danxx", "ARouter onInterrupt 跳转被中断");
                    }
                });
    }

    /*
     * 页面跳转传参
     * */
    public static void JumpCallBackParamObject(String path, final Activity activity, int requestCode, String paramKey, Object paramValue, final Boolean isCloseCurrentPage) {
        ARouter.getInstance()
                .build(path)
                .withObject(paramKey,paramValue)
                .withTransition(R.anim.activity_up_in, R.anim.activity_up_out)
                .navigation(activity, requestCode, new NavigationCallback() {
                    @Override
                    public void onFound(Postcard postcard) {
                        Log.i("danxx", "ARouter onFound 找到跳转匹配路径");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        Log.i("danxx", "ARouter onLost 没有匹配到跳转路径");
                    }

                    @Override
                    public void onArrival(Postcard postcard) {
                        if (isCloseCurrentPage) {
                            activity.finish();
                        }
                        Log.i("danxx", "ARouter onArrival 成功跳转");
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        Log.i("danxx", "ARouter onInterrupt 跳转被中断");
                    }
                });
    }
}
