package com.miempresa.pruebanav1.application;

import android.app.Application;
import android.content.Context;
import android.os.HandlerThread;
import android.util.Log;

import com.ainirobot.coreservice.client.ApiListener;
import com.ainirobot.coreservice.client.RobotApi;
import com.ainirobot.coreservice.client.module.ModuleCallbackApi;
import com.ainirobot.coreservice.client.speech.SkillApi;

public class RobotApp extends Application {

    private static final String TAG = RobotApp.class.getName();

    private Context mContext;
    private SkillApi mSkillApi;

    private SpeechCallback mSkillCallback;
    private HandlerThread mApiCallbackThread;
    private ModuleCallbackApi mModuleCallback;
    private static RobotApp mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mApplication = this;
        init();
        initRobotApi();
    }

    private void init() {
        mSkillCallback = new SpeechCallback();
        mModuleCallback = new ModuleCallback();
        mApiCallbackThread = new HandlerThread("RobotDemo");
        mApiCallbackThread.start();
    }

    public static RobotApp getInstance() {
        return mApplication;
    }

    private void initRobotApi() {
        RobotApi.getInstance().connectServer(mContext, new ApiListener() {
            @Override
            public void handleApiDisabled() {
                Log.i(TAG, "handleApiDisabled");
            }

            /**
             * Server connected, set callback to handle message
             *
             * Start connect RobotOS, init and make it ready to use
             */
            @Override
            public void handleApiConnected() {
                Log.i(TAG, "handleApiConnected");
                addApiCallBack();
                initSkillApi();
            }

            /**
             * Disconnect RobotOS
             */
            @Override
            public void handleApiDisconnected() {
                Log.i(TAG, "handleApiDisconnected");
            }
        });
    }

    private void addApiCallBack() {
        Log.d(TAG, "CoreService connected ");
        RobotApi.getInstance().setCallback(mModuleCallback);
        RobotApi.getInstance().setResponseThread(mApiCallbackThread);
    }

    private void initSkillApi() {
        mSkillApi = new SkillApi();
        ApiListener apiListener = new ApiListener() {
            @Override
            public void handleApiDisabled() {
            }

            /**
             * Handle speech service
             */
            @Override
            public void handleApiConnected() {
                mSkillApi.registerCallBack(mSkillCallback);
            }

            /**
             * Disconnect speech service
             */
            @Override
            public void handleApiDisconnected() {
            }
        };
        mSkillApi.addApiEventListener(apiListener);
        mSkillApi.connectApi(mContext);
    }

    public SkillApi getSkillApi() {
        if (mSkillApi.isApiConnectedService()) {
            return mSkillApi;
        }
        return null;
    }
}
