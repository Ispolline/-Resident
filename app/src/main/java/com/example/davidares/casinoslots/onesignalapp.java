package com.example.davidares.casinoslots;

import android.app.Application;

import com.appsflyer.AppsFlyerLib;
import com.onesignal.OneSignal;
import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

public class onesignalapp extends Application {
    private static final String ONESIGNAL_APP_ID = "9c66595c-2451-4d43-9390-8c0e9ad102e7";

    @Override
    public void onCreate() {
        super.onCreate();
        AppsFlyerLib.getInstance().init("bSiQz4zRRTErHDbyxPM6fg", null, this);
        AppsFlyerLib.getInstance().start(this);
        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
// Creating an extended library configuration.
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder("de62eb90-e7cc-450c-83dc-94083b09c4d5").build();
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(getApplicationContext(), config);
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this);
        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
