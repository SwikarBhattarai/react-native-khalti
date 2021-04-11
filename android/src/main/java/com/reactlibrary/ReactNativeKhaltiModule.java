// ReactNativeKhaltiModule.java

package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.khalti.checkout.helper.Config;
import com.khalti.checkout.helper.KhaltiCheckOut;
import com.khalti.checkout.helper.OnCheckOutListener;
import com.khalti.checkout.helper.PaymentPreference;
import com.khalti.utils.Constant;
import com.khalti.utils.LogUtil;
import com.khalti.widget.KhaltiButton;

public class ReactNativeKhaltiModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public ReactNativeKhaltiModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ReactNativeKhalti";
    }

    // @ReactMethod
    // public void sampleMethod(String stringArgument, int numberArgument, Callback callback) {
    //     // TODO: Implement some actually useful functionality
    //     callback.invoke("Received numberArgument: " + numberArgument + " stringArgument: " + stringArgument);
    // }


    @ReactMethod
    public void makePayment(String publicKey, String productId, String productName, Long amount, Callback callback) {
        HashMap<String, Object> map = new HashMap<String, Object>() {{
            put("merchant_extra", "This is extra data");
            put("merchant_extra_2", "This is extra data 2");
        }};

        Config mainConfig = new Config.Builder(publicKey, productId, productName, amount, new OnCheckOutListener() {
            @Override
            public void onError(@NonNull String action, @NonNull Map<String, String> errorMap) {
                Log.i(action, errorMap.toString());
            }

            @Override
            public void onSuccess(@NonNull Map<String, Object> data) {
                Log.i("success", data.toString());
            }
        })
                .paymentPreferences(new ArrayList<PaymentPreference>() {{
                    add(PaymentPreference.KHALTI);
                    add(PaymentPreference.EBANKING);
                    add(PaymentPreference.MOBILE_BANKING);
                    add(PaymentPreference.CONNECT_IPS);
                    add(PaymentPreference.SCT);
                }})
//                .additionalData(map)
                .build();

  }
}
