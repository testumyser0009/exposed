package com.wz.xposedalipay.base;

import android.widget.Toast;

import androidx.databinding.ViewDataBinding;

import com.wz.xposedalipay.common.base.ViewManager;


/**
 * Created by weizhi on 2017/2/22.
 */
public abstract class CommonActivity<VM extends BaseViewModel,DB extends ViewDataBinding> extends BaseActivity<VM,DB> {

    @Override
    public void onBackPressed() {
        boolean isReturn = showConfirmToast();
        if (isReturn)
            return;


        ViewManager.getInstance().finishAllActivity();
        // SharePreferenceUtils.getInstance(getApplicationContext()).clearUser();
        super.onBackPressed();
    }

    private long mExitTime;

    public boolean showConfirmToast() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            mExitTime = secondTime;

            return true;

        }
        return false;
    }
}
