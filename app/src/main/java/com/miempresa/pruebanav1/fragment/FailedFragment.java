package com.miempresa.pruebanav1.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.miempresa.pruebanav1.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FailedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FailedFragment extends BaseFragment {


    private Button mExit;

    @Override
    public View onCreateView(Context context) {
        View root = mInflater.inflate(R.layout.fragment_failed_layout,null,false);
        bindViews(root);
        return root;
    }

    private void bindViews(View root) {
        mExit = (Button) root.findViewById(R.id.exit);
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    public static Fragment newInstance() {
        return new FailedFragment();
    }
}