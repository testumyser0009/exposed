package com.wz.xposedalipay.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import io.reactivex.disposables.CompositeDisposable;

/*
* B,M
* B:代表Binding
* M: 代表实体类
* */
public class BaseViewModel<B> extends AndroidViewModel {
    protected Context context;
    protected Activity activity;
    protected B binding;
    private static final MutableLiveData ABSENT = new MutableLiveData();
    {
        //noinspection unchecked
        ABSENT.setValue(null);
    }


    protected final CompositeDisposable mDisposable = new CompositeDisposable();


    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    public void init(B binding, Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        this.binding = binding;
        initData();
    }

    protected void initData() {

    }


    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
    }
}
