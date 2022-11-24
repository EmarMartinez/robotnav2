package com.miempresa.pruebanav1.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.RemoteException;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ainirobot.coreservice.client.Definition;
import com.ainirobot.coreservice.client.RobotApi;
import com.ainirobot.coreservice.client.listener.ActionListener;
import com.ainirobot.coreservice.client.listener.CommandListener;
import com.miempresa.pruebanav1.R;
import com.miempresa.pruebanav1.utils.LogTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NavigationFragment extends BaseFragment {

    private Button mTurn_direction;
    private Button mStop_navigation;
    private Button mStart_navigation;
    private EditText mNavigation_point;

    @Override
    public View onCreateView(Context context) {
        View root = mInflater.inflate(R.layout.fragment_navigation_layout, null, false);
        initViews(root);
        return root;
    }

    private void initViews(View root) {
        mTurn_direction = (Button) root.findViewById(R.id.turn_direction);
        mStop_navigation = (Button) root.findViewById(R.id.stop_navigation);
        mStart_navigation = (Button) root.findViewById(R.id.start_navigation);
        mNavigation_point = (EditText)root.findViewById(R.id.et_navigation_point);
        LogTools.info(RobotApi.getInstance().getMapName());


        RobotApi.getInstance().getPlaceList(0, new CommandListener() {
            @Override
            public void onResult(int result, String message) {
                try {
                    JSONArray jsonArray = new JSONArray(message);
                    int length = jsonArray.length();
                    for (int i = 0; i < length; i++) {
                        JSONObject json = jsonArray.getJSONObject(i);
                        json.getDouble("x"); //x coordinate
                        json.getDouble("y"); //y coordinate
                        json.getDouble("theta"); //z coordinate
                        json.getString("name"); //position name
                        LogTools.info(json.toString());
                    }
                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
                }
            }
        });

        mStart_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNavigation();
            }
        });

        mStop_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopNavigation();
            }
        });

        mTurn_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeSpecialPlaceTheta();
            }
        });

    }

    private String getNavigationPoint(){
        String leadPoint = mNavigation_point.getText().toString();
        if(TextUtils.isEmpty(leadPoint)){
            leadPoint = mNavigation_point.getHint().toString();
        }
        return leadPoint;
    }


    private void startNavigation() {
        RobotApi.getInstance().startNavigation(0, getNavigationPoint(), 1.5, 10 * 1000, mNavigationListener);
    }


    private void stopNavigation() {
        RobotApi.getInstance().stopNavigation(0);
    }


    private void resumeSpecialPlaceTheta() {
        String navigationPoint = getNavigationPoint();
        if(TextUtils.isEmpty(navigationPoint)){
            LogTools.info("Point not exist: " + navigationPoint);
            return;
        }else{
            LogTools.info("Target point: " + navigationPoint);
        }
        RobotApi.getInstance().resumeSpecialPlaceTheta(0,navigationPoint, new CommandListener() {
            @Override
            public void onResult(int result, String message, String extraData) {
                super.onResult(result, message, extraData);
                LogTools.info("resumeSpecialPlaceTheta result: " + result + " message: "+  message);
            }

            @Override
            public void onStatusUpdate(int status, String data, String extraData) {
                super.onStatusUpdate(status, data, extraData);
                LogTools.info("onStatusUpdate result: " + status + " message: "+  data);
            }

            @Override
            public void onError(int errorCode, String errorString, String extraData) throws RemoteException {
                super.onError(errorCode, errorString, extraData);
                LogTools.info("onError result: " + errorCode + " message: "+  errorString);
            }
        });
    }

    private ActionListener mNavigationListener = new ActionListener() {

        @Override
        public void onResult(int status, String response) throws RemoteException {

            switch (status) {
                case Definition.RESULT_OK:
                    if ("true".equals(response)) {
                        LogTools.info("startNavigation result: " + status +"(Navigation success)"+ " message: "+  response);
                    } else {
                        LogTools.info("startNavigation result: " + status +"(Navigation failed)"+ " message: "+  response);
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onError(int errorCode, String errorString) throws RemoteException {
            switch (errorCode) {
                case Definition.ERROR_NOT_ESTIMATE:
                    LogTools.info("onError result: " + errorCode +"(not estimate)"+ " message: "+  errorString);
                    break;
                case Definition.ERROR_IN_DESTINATION:
                    LogTools.info("onError result: " + errorCode +"(in destination, no action)"+ " message: "+  errorString);
                    break;
                case Definition.ERROR_DESTINATION_NOT_EXIST:
                    LogTools.info("onError result: " + errorCode +"(destination not exist)"+ " message: "+  errorString);
                    break;
                case Definition.ERROR_DESTINATION_CAN_NOT_ARRAIVE:
                    LogTools.info("onError result: " + errorCode +"(avoid timeout, can not arrive)"+ " message: "+  errorString);
                    break;
                case Definition.ACTION_RESPONSE_ALREADY_RUN:
                    LogTools.info("onError result: " + errorCode +"(already started, please stop first)"+ " message: "+  errorString);
                    break;
                case Definition.ACTION_RESPONSE_REQUEST_RES_ERROR:
                    LogTools.info("onError result: " + errorCode +"(wheels are busy for other actions, please stop first)"+ " message: "+  errorString);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onStatusUpdate(int status, String data) throws RemoteException {
            switch (status) {
                case Definition.STATUS_NAVI_AVOID:
                    LogTools.info("onStatusUpdate result: " + status +"(can not avoid obstacles)"+ " message: "+  data);
                    break;
                case Definition.STATUS_NAVI_AVOID_END:
                    LogTools.info("onStatusUpdate result: " + status +"(Obstacle removed)"+ " message: "+  data);
                    break;
                default:
                    break;
            }
        }
    };

    public static Fragment newInstance() {
        return new NavigationFragment();
    }
}