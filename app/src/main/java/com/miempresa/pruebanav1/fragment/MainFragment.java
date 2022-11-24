package com.miempresa.pruebanav1.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.miempresa.pruebanav1.R;

public class MainFragment extends BaseFragment {

//    private Button mLead_scene;
//    private Button mSport_scene;
//    private Button mSpeech_scene;
//    private Button mVision_scene;
//    private Button mCharge_scene;
//    private Button mLocation_scene;
    private Button mNavigation_scene;
    private Button mExit;

    @Override
    public View onCreateView(Context context) {
        View root = mInflater.inflate(R.layout.fragment_main_layout,null,false);
        bindViews(root);
        hideBackView();
        hideResultView();
        return root;
    }

    private void bindViews(View root) {
//        mLead_scene = (Button) root.findViewById(R.id.lead_scene);
//        mSport_scene = (Button) root.findViewById(R.id.sport_scene);
//        mSpeech_scene = (Button) root.findViewById(R.id.speech_scene);
//        mVision_scene = (Button) root.findViewById(R.id.vision_scene);
//        mCharge_scene = (Button) root.findViewById(R.id.charge_scene);
//        mLocation_scene = (Button) root.findViewById(R.id.location_scene);
        mNavigation_scene = (Button) root.findViewById(R.id.navigation_scene);
        mExit = (Button) root.findViewById(R.id.exit);
        mExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
//        mLead_scene.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchFragment(LeadFragment.newInstance());
//            }
//        });
//
//        mSpeech_scene.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchFragment(SpeechFragment.newInstance());
//            }
//        });
//
//        mSport_scene.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchFragment(SportFragment.newInstance());
//            }
//        });
//
//        mVision_scene.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchFragment(VisionFragment.newInstance());
//            }
//        });
//
//        mLocation_scene.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchFragment(LocationFragment.newInstance());
//            }
//        });

        mNavigation_scene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFragment(NavigationFragment.newInstance());
//                switchFragment(NavFragment.newInstance());
            }
        });

//        mCharge_scene.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switchFragment(ChargeFragment.newInstance());
//            }
//        });
    }

    public static Fragment newInstance() {
        return new MainFragment();
    }
}