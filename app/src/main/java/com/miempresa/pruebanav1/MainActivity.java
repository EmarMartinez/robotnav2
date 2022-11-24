package com.miempresa.pruebanav1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.ainirobot.coreservice.client.RobotApi;
import com.miempresa.pruebanav1.fragment.FailedFragment;
import com.miempresa.pruebanav1.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout mContent;

    private static MainActivity mInstance;
    private int checkTimes;

    public static MainActivity getInstance(){
        return mInstance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkTimes = 0;
        initViews();
        mInstance = this;
    }

    private void initViews() {
        mContent = findViewById(R.id.container_content);
        checkInit();
    }

    public void switchFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_content, fragment, fragment.getClass().getName())
                .commit();
    }

    private void checkInit(){
        checkTimes++;
        if(checkTimes > 10){
            Fragment fragment = FailedFragment.newInstance();
            switchFragment(fragment);
        }
        else if(RobotApi.getInstance().isApiConnectedService()){
            Fragment fragment = MainFragment.newInstance();
            switchFragment(fragment);
        }else
        {
            mContent.postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkInit();
                }
            },300);
        }
    }
}