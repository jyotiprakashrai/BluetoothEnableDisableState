package com.example.jyotiprakash.bluetoothapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();

    BluetoothAdapter mBluetoothAdapter;
    Button btnONOF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btnONOF.setOnClickListener(this);
    }

    public void enableDisableBT(){
        if (mBluetoothAdapter == null){
            Toast.makeText(this, "Your device does not have bluetooth capabilities", Toast.LENGTH_SHORT).show();
            Log.d(TAG,"Inside enableDisableBT: device does not have bluetooth capabilities");
        }
        if (!mBluetoothAdapter.isEnabled()){
            Log.d(TAG, "Enabling Bluetooth");
            startActivity(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE));

            IntentFilter BTIntentFilter = new IntentFilter(mBluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1,BTIntentFilter);

        }
        if (mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.disable();
            Log.d(TAG, "Disabling Bluetooth");
            IntentFilter BTIntentFilter = new IntentFilter(mBluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1,BTIntentFilter);
        }
    }

    private final BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //When discovery find a device
            if (action.equals(mBluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state= intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, mBluetoothAdapter.ERROR);

                switch(state){
                    case BluetoothAdapter.STATE_OFF:
                        Log.d(TAG, "onReceive: STATE OFF");
                        break;
                    case BluetoothAdapter.STATE_TURNING_OFF:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING OFF");
                        break;
                    case BluetoothAdapter.STATE_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE ON");
                        break;
                    case BluetoothAdapter.STATE_TURNING_ON:
                        Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                        break;
                }

            }
        }
    };


    public void initView(){
        btnONOF= (Button) findViewById(R.id.btnONOFF);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnONOFF:
                Log.d(TAG, "mBroadcastReceiver1: STATE TURNING ON");
                enableDisableBT();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: unRegistering Broadcast Receiver");
        unregisterReceiver(mBroadcastReceiver1);
    }
}
