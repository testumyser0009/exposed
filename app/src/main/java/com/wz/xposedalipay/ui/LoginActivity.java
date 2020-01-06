package com.wz.xposedalipay.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wz.xposedalipay.R;
import com.wz.xposedalipay.base.CommonActivity;
import com.wz.xposedalipay.bean.LoginBean;
import com.wz.xposedalipay.common.base.ARouterPath;
import com.wz.xposedalipay.databinding.LoginLayoutBinding;
import com.wz.xposedalipay.network.repository.FdKyAppDataRepository;
import com.wz.xposedalipay.util.Constant;
import com.wz.xposedalipay.util.LoadingDialog1;
import com.wz.xposedalipay.util.SPUtils;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


@Route(path = ARouterPath.LoginPath)
public class LoginActivity extends CommonActivity<LoginViewModel, LoginLayoutBinding> {

    private ImageView m_obj_changeAccount;
    private boolean m_b_isRememberPwd;
//    private SwitchButton switchButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
//        binding.setLogin(viewModel);
//        subscribeToModel(viewModel);
        m_obj_changeAccount = binding.idLoginLayoutChangeAccount;


        binding.idLoginLayoutLoginname.setSelection(binding.idLoginLayoutLoginname.getText().toString().length());


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
//        switchButton = findViewById(R.id.sb_ios);
//        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//            }
//        });
    }

    public void login() {
        if (binding.idLoginLayoutLoginname.getText().toString().equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (binding.idLoginLayoutPassword.getText().toString().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog1.showDialogForLoading(this);
        FdKyAppDataRepository.Login(binding.idLoginLayoutLoginname.getText().toString(),
                binding.idLoginLayoutPassword.getText().toString(), "3349")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        int a = 5;
                        LoadingDialog1.cancelDialogForLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoadingDialog1.cancelDialogForLoading();
                        int a = 5;
                    }
                });
    }

    /**
     * 订阅数据变化来刷新UI
     * @param model
     */
    private void subscribeToModel(final LoginViewModel model){
        //观察数据变化来刷新UI
        model.getLiveObservableDataLoginBeam().observe(this, new Observer<LoginBean>() {
            @Override
            public void onChanged(@Nullable LoginBean loginBean) {

            }
        });
    }

    /*
    * ArrayList转LinkedList
    * */


    @Override
    public int getLayoutId() {
        return R.layout.login_layout;
    }

    @Override
    public Class<LoginViewModel> getClassObj() {
        return LoginViewModel.class;
    }


    private String[] itemsSingle;


}
