package net.elshaarawy.mobilia.Activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import net.elshaarawy.mobilia.R;
import net.elshaarawy.mobilia.Services.LoadDataService;
import net.elshaarawy.mobilia.Utils.PreferenceUtil;

import static net.elshaarawy.mobilia.Utils.PreferenceUtil.DefaultKeys;

public class SplashActivity extends AppCompatActivity {

    private static final String BROADCAST_ACTION = "net.elshaarawy.mobilia.Activities.SplashActivity";
    private LocalBroadcastManager mLocalBroadcastManager;
    private PreferenceUtil mPreferenceUtil;
//    private ProgressDialog mProgressDialog;
    private boolean mChangeConfigurationFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mChangeConfigurationFlag = savedInstanceState == null;


        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mPreferenceUtil = new PreferenceUtil(this, DefaultKeys.DEFAULT_SHARED_PREFERENCE);

//        mProgressDialog = new ProgressDialog(this);
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setMessage(getString(R.string.progress_dialog_message));
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.setCanceledOnTouchOutside(false);

        if (!mPreferenceUtil.getBoolean(DefaultKeys.PREF_IS_FIRST_TIME) && mChangeConfigurationFlag) {

            LoadDataService.startMe(this, true);
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                }
            }, 1000);
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(intent.EXTRA_RESULT_RECEIVER, false);
            if (isSuccess) {
                mPreferenceUtil.editValue(DefaultKeys.PREF_IS_FIRST_TIME, true);
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            } else {
                Toast.makeText(SplashActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
            }
//            mProgressDialog.hide();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(BROADCAST_ACTION);
        mLocalBroadcastManager.registerReceiver(broadcastReceiver, filter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocalBroadcastManager.unregisterReceiver(broadcastReceiver);
    }

    public static void sendBroadCastToMe(Context context, boolean isSuccess) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        Intent intent = new Intent(BROADCAST_ACTION);
        intent.putExtra(Intent.EXTRA_RESULT_RECEIVER, isSuccess);
        broadcastManager.sendBroadcast(intent);
    }
}
