package com.example.broadcastbestpractice;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by W on 2018/12/2.
 */

public class BaseActivity extends AppCompatActivity {
    private ForceOfflineReceiver receive;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcastbestpractice.FORCE_OFFLINE");
        receive = new ForceOfflineReceiver();
        registerReceiver(receive,intentFilter);
    }

    @Override
    protected void onPause(){
        super.onPause();
        if (receive != null){
            unregisterReceiver(receive);
            receive = null;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    class ForceOfflineReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(final Context context, Intent intent){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Waring");
            builder.setMessage("You are to be force offline. Please try to login again.");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCollector.finishAll();;
                    Intent intent = new Intent(context,LoginActivity.class);
                    startActivity(intent);

                }
            });
            builder.show();
        }
    }
}
