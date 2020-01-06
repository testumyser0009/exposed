package com.wz.xposedalipay.ui;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.wz.xposedalipay.base.BaseViewModel;
import com.wz.xposedalipay.bean.LoginBean;
import com.wz.xposedalipay.databinding.LoginLayoutBinding;
import com.wz.xposedalipay.network.repository.FdKyAppDataRepository;
import com.wz.xposedalipay.util.LoadingDialog1;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends BaseViewModel<LoginLayoutBinding> {
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    //生命周期观察的数据
    private MutableLiveData<LoginBean> mLiveObservableDataLoginBean = new MutableLiveData<>();
//    public void login() {
//        if (binding.idLoginLayoutLoginname.getText().toString().equals("")) {
//            Toast.makeText(context, "用户名不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (binding.idLoginLayoutPassword.getText().toString().equals("")) {
//            Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        LoadingDialog1.showDialogForLoading(activity);
//        FdKyAppDataRepository.Login(binding.idLoginLayoutLoginname.getText().toString(),
//                binding.idLoginLayoutPassword.getText().toString())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<LoginBean>() {
//                    @Override
//                    public void accept(LoginBean loginBean) throws Exception {
////                        LoadingDialog.getInstance().dismiss();
//                        mLiveObservableDataLoginBean.setValue(loginBean);
//                        LoadingDialog1.cancelDialogForLoading();
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        LoadingDialog1.cancelDialogForLoading();
//                        mLiveObservableDataLoginBean.setValue(null);
////                        LoadingDialog.getInstance().dismiss();
//                    }
//                });
//
//
//
//    }

    public MutableLiveData<LoginBean> getLiveObservableDataLoginBeam() {
        return mLiveObservableDataLoginBean;
    }

}
