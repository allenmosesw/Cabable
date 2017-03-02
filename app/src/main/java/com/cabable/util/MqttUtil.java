package com.cabable.util;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.cabable.data.LatLongSync;
import com.google.gson.Gson;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AllenMosesW on 22/01/17.
 */

public class MqttUtil {
//    private Context context;

    private final String TAG = this.getClass().getSimpleName();
    private static MqttUtil mInstance = null;

    //Mqtt varibles
    private MqttAndroidClient client;


    public static MqttUtil getInstance(Context applicationContext) {
        if (mInstance == null) {
            mInstance = new MqttUtil();
            mInstance.setupMqttClient(applicationContext);
        }
        return mInstance;
    }


    public void setupMqttClient(Context appContext) {
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(appContext, Constants.APP_SERVER_URL, clientId);

        try {
            IMqttToken token = client.connect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Log.d(TAG, "onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Log.d(TAG, "onFailure");

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void publishMsg(Location myLocation) {
        Log.d(TAG, "publicMsg");
        String topic = "test/topic";
//        String payload = "{\"_id\":1,\"status\":2,\"location\":[13.0144369,77.6492195],\"city\":\"bangalore\"}";
        String payload = "{\"_id\":1,\"status\":2,\"location\":[13.0144369,77.6492195],\"city\":\"bangalore\"}";
        List<Double> latLong = new ArrayList<>();
        latLong.add(myLocation.getLatitude());
        latLong.add(myLocation.getLongitude());
        LatLongSync latLongSync = new LatLongSync(0, 2, latLong, "bangalore");
        Gson gson = new Gson();


        Log.d(TAG, "publicMsg--PAYLOAD->" + payload);
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = gson.toJson(latLongSync).getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }



}
