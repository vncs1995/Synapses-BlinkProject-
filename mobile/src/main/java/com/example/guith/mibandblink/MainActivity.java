package com.example.guith.mibandblink;

import android.bluetooth.le.BluetoothLeScanner;
import android.content.Context;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.bluetooth.*;
import android.widget.Toast;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.*;



public class MainActivity extends AppCompatActivity {

    public Button btnClick;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClick = (Button) findViewById(R.id.vibrateButton);





        btnClick.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                BroadcastReceiver receiver = new BroadcastReceiver(){
                    @Override
                    public void onReceive(Context context, Intent intent) {

                        String action = intent.getAction();
                        if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                            int  rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                            Toast.makeText(context,"  RSSI: " + rssi + "dBm", Toast.LENGTH_SHORT).show();
                            msgBox(""+rssi, ""+rssi);
                        }
                    }
                };

                registerReceiver(receiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
            }
        });
    }


    public void msgBox(String title, String msg){


    }
}
