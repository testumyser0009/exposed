package com.wz.xposedalipay.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.wz.xposedalipay.R;
import com.wz.xposedalipay.bean.LoginBean;
import com.wz.xposedalipay.network.repository.FdKyAppDataRepository;
import com.wz.xposedalipay.util.LoadingDialog1;

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
                        int a = 5;
                        LoadingDialog1.cancelDialogForLoading();
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
