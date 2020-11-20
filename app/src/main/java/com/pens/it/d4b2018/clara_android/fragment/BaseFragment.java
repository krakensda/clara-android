package com.pens.it.d4b2018.clara_android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.pens.it.d4b2018.clara_android.R;
import com.pens.it.d4b2018.clara_android.presenter.BasePresenter;

public abstract class BaseFragment<T extends FragmentActivity, U extends BasePresenter> extends Fragment {

    protected String title;
    protected T activity;
    protected View fragmentView;
    protected U mPresenter;
    protected FragmentListener fragmentListener;

    public void setTitle(String title){
        this.title = title;
        fragmentListener.setTitle(title);
    }

    public String getTitle() {
        return title;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        title = getResources().getString(R.string.app_name);
        setTitle(title);

        return view;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (T) context;
        this.fragmentListener = (FragmentListener) context;
    }

}
