package com.fd.xposed.ui;

import android.app.Dialog;
import android.content.Context;

import com.wang.avi.AVLoadingIndicatorView;

/**
 * 加载中Dialog
 *
 */
public class LoadingDialog extends Dialog {

    private volatile static LoadingDialog loadingDialog = null;
    private static AVLoadingIndicatorView avi;

    public static LoadingDialog getInstance(Context context) {
//        if (null == loadingDialog) {
//            synchronized (LoadingDialog.class) {
//                if (null == loadingDialog) {
//                    loadingDialog = new LoadingDialog(ASXApplication.getAppContext(), R.style.TransparentDialog); //设置AlertDialog背景透明
//                    loadingDialog.setCancelable(false);
//                    loadingDialog.setCanceledOnTouchOutside(false);
//                    View view = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
//
//                    avi = (AVLoadingIndicatorView)view.findViewById(R.id.avi);
//                }
//            }
//        }
//        View view = getLayoutInflater().inflate(R.layout.login_layout,null);

        return loadingDialog;
    }

    public void destroy() {
        loadingDialog = null;
    }
    public LoadingDialog(Context context, int themeResId) {
        super(context,themeResId);
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.setContentView(R.layout.loading_dialog);
//        avi = (AVLoadingIndicatorView)this.findViewById(R.id.avi);
//        int a = 5;
//    }

//    @Override
//    public void show() {
//        super.show();
//        avi = (AVLoadingIndicatorView)this.findViewById(R.id.avi);
//        avi.show();
//    }

    public void showLoading() {
//        if (null != avi) {
//            avi.show();
//        } else {
//            avi = (AVLoadingIndicatorView)this.findViewById(R.id.avi);
//            avi.show();
//        }
        if (null != avi) {
            avi.show();
        }
    }
    @Override
    public void dismiss() {
        super.dismiss();
        if (null != avi) {
            avi.hide();
        }
    }
}