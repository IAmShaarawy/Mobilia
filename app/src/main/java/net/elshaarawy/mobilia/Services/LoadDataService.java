package net.elshaarawy.mobilia.Services;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import net.elshaarawy.mobilia.Activities.SplashActivity;
import net.elshaarawy.mobilia.R;
import net.elshaarawy.mobilia.Widgets.OffersWidget;

/**
 * Created by elshaarawy on 23-May-17.
 */

public class LoadDataService extends Service {

    private static final String EXTRA_WITH_DIALOG = "extra_with_dialog";
    private LoadTask mLoadTask;

    private ContentResolver mResolver;


    @Override
    public void onCreate() {
        super.onCreate();

        mLoadTask = new LoadTask();

        mResolver = getContentResolver();


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mLoadTask.execute();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class LoadTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // TODO Init firebase variables and instances
        }

        @Override
        protected Boolean doInBackground(String... params) {
            //TODO Load Data from Firebase and insert it in cache Database using Content Provider

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            SplashActivity.sendBroadCastToMe(LoadDataService.this, aBoolean);
            OffersWidget.sendBroadCastToMe(LoadDataService.this);

            LoadDataService.this.stopSelf();
        }
    }

    public static void startMe(Context context) {
        Intent intent = new Intent(context, LoadDataService.class);
        context.startService(intent);
    }
}
