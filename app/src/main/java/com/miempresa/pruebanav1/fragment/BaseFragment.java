package com.miempresa.pruebanav1.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.miempresa.pruebanav1.MainActivity;
import com.miempresa.pruebanav1.R;
import com.miempresa.pruebanav1.utils.LogTools;
import com.miempresa.pruebanav1.view.BackView;
import com.miempresa.pruebanav1.view.ResultView;


public abstract class BaseFragment extends Fragment {

    private BackView mBv_back;
    private ResultView mRv_result;
    private RelativeLayout mRl_content;

    protected MainActivity mActivity;
    protected LayoutInflater mInflater;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mActivity = (MainActivity) context;
        }
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_base, container, false);
        bindViews(root);
        return root;
    }

    private void bindViews(View root) {
        mBv_back = (BackView) root.findViewById(R.id.bv_back);
        mRl_content = (RelativeLayout) root.findViewById(R.id.rl_content);
        mRv_result = (ResultView) root.findViewById(R.id.rv_result);
        mRl_content.addView(onCreateView(mActivity));
    }

    protected void showBackView() {
        mBv_back.setVisibility(View.VISIBLE);
    }

    protected void hideBackView() {
        mBv_back.setVisibility(View.GONE);
    }

    protected void showResultView() {
        mRv_result.setVisibility(View.VISIBLE);
    }

    protected void hideResultView() {
        mRv_result.setVisibility(View.GONE);
    }

    public void switchFragment(Fragment fragment){
        mActivity.switchFragment(fragment);
    }

    public abstract View onCreateView(Context context);


    @Override
    public void onStop() {
        super.onStop();
        LogTools.clearHistory();
    }
}