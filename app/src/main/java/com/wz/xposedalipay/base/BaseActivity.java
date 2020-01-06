package com.wz.xposedalipay.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProviders;

import com.wz.xposedalipay.R;
import com.wz.xposedalipay.common.base.ViewManager;
import com.wz.xposedalipay.util.BaseFragment;

/**
 * <p>Activity基类 </p>
 * VM:ViewModel
 * DB: databinding
 * @name BaseActivity
 */
public abstract class BaseActivity<VM extends BaseViewModel,DB extends ViewDataBinding> extends AppCompatActivity {


    protected DB binding;
    protected VM viewModel;
    // 获取VM的class对象
    protected Class<VM> clazz;
    /**
     * 封装的findViewByID方法
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T $(@IdRes int id) {
        return (T) super.findViewById(id);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
//        clazz = clazz.getSuperclass();
        viewModel =
                ViewModelProviders.of(this).get(getClassObj());
        viewModel.init(binding, this, this);
        ViewManager.getInstance().addActivity(this);
    }

    /*获取布局文件*/
    public abstract int getLayoutId();
    /*获取class对象*/
    public abstract Class<VM> getClassObj();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ViewManager.getInstance().finishActivity(this);
//        LoadingDialog.getInstance(this).destroy();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(R.anim.activity_down_in, R.anim.activity_down_out);
    }
}
