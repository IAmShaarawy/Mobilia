package net.elshaarawy.mobilia;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class MobiliaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
