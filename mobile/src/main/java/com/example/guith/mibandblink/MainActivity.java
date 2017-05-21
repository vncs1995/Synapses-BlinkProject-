package com.example.guith.mibandblink;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.bluetooth.*;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.*;
import	java.lang.reflect.Method;

import java.util.UUID;

/*MAC ADDRESS E4:A4:9E:82:1F:EB*/
public class MainActivity extends AppCompatActivity {

    public Button statusButton;
    public String deviceName = "";
    public String deviceHardwareAddress = "";
    public int rssi = 0;
    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                deviceName = device.getName();
                deviceHardwareAddress = device.getAddress(); // MAC address
                rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
            }
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothAdapter.startDiscovery();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);

        if(deviceHardwareAddress.equals("A4:70:D6:53:B6:9F"))   {
                   if (rssi > 1 && rssi < 30) {
                       statusButton.setBackgroundColor(Color.GREEN);
                       statusButton.setText("Safe");
                       msgBox("RSSI "+rssi);
                       msgBox("Device Address "+deviceHardwareAddress);
                       msgBox("Device Name:" + deviceName);

                   } else if (rssi > 30 && rssi < 50) {
                       statusButton.setBackgroundColor(Color.rgb(246, 136, 31));
                       statusButton.setText("Caution");
                       msgBox("RSSI "+rssi);
                       msgBox("Device Address "+deviceHardwareAddress);
                       msgBox("Device Name:" + deviceName);

                   } else {
                       statusButton.setBackgroundColor(Color.RED);
                       statusButton.setText("Careful");
                       msgBox("RSSI "+rssi);
                       msgBox("Device Address "+deviceHardwareAddress);
                       msgBox("Device Name:" + deviceName);

                   }
        }

        /*statusButton = (Button) findViewById(R.id.safeButton);

        statusButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){

                    BluetoothDevice device = bluetoothAdapter.getRemoteDevice();
                    BluetoothSocket tmp = null;
                    BluetoothSocket mmSocket = null;

                    try {
                        tmp = device.createRfcommSocketToServiceRecord(UUID.randomUUID());
                        Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
                        tmp = (BluetoothSocket) m.invoke(device, 1);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    mmSocket = tmp;

                if(device.getBondState()==BluetoothDevice.BOND_BONDED){
                    msgBox("Conectado " +device.getName());
                }else {
                    msgBox("Desconectado");
                }

            }
        });  */

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(mReceiver);
    }

    public void msgBox(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
