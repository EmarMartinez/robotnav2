package com.miempresa.pruebanav1.application;

import android.os.RemoteException;
import android.util.Log;

import com.ainirobot.coreservice.client.module.ModuleCallbackApi;
import com.miempresa.pruebanav1.utils.LogTools;

public class ModuleCallback extends ModuleCallbackApi {

    private static final String TAG = ModuleCallback.class.getName();

    @Override
    public boolean onSendRequest(int reqId, String reqType, String reqText, String reqParam)
            throws RemoteException {
        //receive voice command,
        //reqTyp : voice command type
        //reqText : voice to text
        //reqParam : voice command parameter
        Log.d(TAG, "Nueva peticion: " + " de tipo:" + reqType + " texto:" + reqText + " reqParam = " + reqParam);
        String text = "Nueva peticion: " + " de tipo:" + reqType + " texto:" + reqText + " reqParam = " + reqParam;
        LogTools.info(text);
        return true;
    }
    @Override
    public void onRecovery()
            throws RemoteException {
        //When receiving the event, regain control of the robot
        Log.d(TAG, "onRecovery");
        LogTools.info("onRecovery");
    }
    @Override
    public void onSuspend()
            throws RemoteException {
        //Control is deprived by the system. When receiving this event, all Api calls are invalid
        Log.d(TAG, "onSuspend");
        LogTools.info("onSuspend");
    }

    @Override
    public void onHWReport(int function, String type, String message) throws RemoteException {
        //hardware error callback
        Log.i(TAG, "onHWReport funcion:" + function + " tipo:" + type + " mensaje:" + message);
        LogTools.info("onHWReport funcion:" + function + " tipo:" + type + " mensaje:" + message);
    }
}
