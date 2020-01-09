package com.fd.xposed.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.fd.xposed.MainActivity;
import com.fd.xposed.R;
import com.fd.xposed.bean.LoginBean;
import com.fd.xposed.network.repository.FdKyAppDataRepository;
import com.fd.xposed.util.Constant;
import com.fd.xposed.util.SPUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends Activity {

    private ImageView m_obj_changeAccount;
    private boolean m_b_isRememberPwd;
    private EditText m_account_et;
    private EditText m_password_et;
    private Button m_btn_login;
//    private SwitchButton switchButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        m_account_et = this.findViewById(R.id.id_login_layout_loginname);
        m_password_et = this.findViewById(R.id.id_login_layout_password);
        m_btn_login = findViewById(R.id.login_btn);
        m_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        if (m_account_et.getText().toString().equals("")) {
            Toast.makeText(this, "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (m_password_et.getText().toString().equals("")) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        LoadingDialog1.showDialogForLoading(this);

        FdKyAppDataRepository.Login(m_account_et.getText().toString(),
                m_password_et.getText().toString(), "3349")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginBean>() {
                    @Override
                    public void accept(LoginBean loginBean) throws Exception {
                        if (loginBean.getStatus().equals("0")) {
                            Toast.makeText(LoginActivity.this, loginBean.getMessage() , Toast.LENGTH_SHORT).show();
                            return;
                        }
                        int a = 5;
                        SPUtils.setSharedStringData(LoginActivity.this, Constant.CodeId, loginBean.getResult().getData().getCode_id());
                        SPUtils.setSharedStringData(LoginActivity.this, Constant.bankId, loginBean.getResult().getData().getBank_id());
                        SPUtils.setSharedStringData(LoginActivity.this, Constant.Token, loginBean.getResult().getToken());
                        LoadingDialog1.cancelDialogForLoading();
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);

//                        mLiveObservableDataLoginBean.setValue(loginBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LoadingDialog1.cancelDialogForLoading();
                        int a = 5;
                    }
                });

    }


}
