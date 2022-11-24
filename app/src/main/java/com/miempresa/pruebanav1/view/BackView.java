package com.miempresa.pruebanav1.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;


import com.miempresa.pruebanav1.MainActivity;
import com.miempresa.pruebanav1.R;
import com.miempresa.pruebanav1.fragment.MainFragment;

public class BackView extends LinearLayout {

    private Button mBack_btn;
    private Context mContext;
    public BackView(Context context) {
        super(context);
        init(context);
    }

    public BackView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_back_view,this,true);
        mContext  = context;
        bindViews();
    }

    private void bindViews() {
        mBack_btn = (Button) findViewById(R.id.back_btn);
        mBack_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().switchFragment(MainFragment.newInstance());
            }
        });
    }
}
