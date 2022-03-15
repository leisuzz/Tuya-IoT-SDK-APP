package com.js.test;

import androidx.appcompat.app.AppCompatActivity

import android.bluetooth.BluetoothClass;
import android.graphics.Color;
import android.os.Bundle
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeActivity extends  AppCompatActivity{

    private CardView cvDevice;
    private Button btnSearch;
    private TextView tvDeviceName, tvDeviceId, tvProductId;

    String homeName = "MyHome";
    ArrayList<String> roomList;

    private HomeBeam currentHomeBeam;
    private DeviceBean currentDeviceBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initViews();

        cvDevice.setBackgroundColor(Color.LTGRAY);
        cvDevice.setClickable(false);

        roomList = new ArrayList<>();
        roomList.addAll(Arrays.asList(rooms));

        createHome(homeName, roomList);


    }

    private void createHome(String homeName, List<String> roomList){
        TuyaHomeSdk.getHomeManagerInstance().createHome(homeName,
                0, 0, "", roomList, new ITuyaHomeResultCallback() {
                    @Override
                    public void onSuccess(HomeBean bean) {
                        currentHomeBeam = bean;
                        Toast.makeText(HomeActivity.this, "Home Creation Successful", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(String errorCOde, String errorMsg) {
                        Toast.makeText(HomeActivity.this, "Home Creation Successful", Toast.LENGTH_LONG).show();

                    }
                });

    }

    private void searchDevices(String token){

        tuyaActivator = TuyaHomeSdk.getActivatorInstance().newMultiActivator(new ActivatorBuilder()
                .setSsid(ssid)
                .setPassword(password)
                .setContext(this)
                .setActivatorModel(ActivatorModelEnum.TY_EZ)
                .setTimeOut(1000)
                .setTOken(token)
                .setListener(new ITuyaSmartActivatorListener(){
                    @Override
                    public void onError(String errorCode, String errorMsg){
                        Toast.makeText(HomeActivity.this, "Device detection failed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onActiveSuccess(DeviceBean devResp) {
                        Toast.makeText(HomeActivity.this, "Device detection successful", Toast.LENGTH_LONG).show();
                        currentDeviceBean = devResp;
                        //startActivity(new Intent(HomeActivity.this, DeviceControl.class));
                    }

                    @Override
                    public void onStep(String step, Object data){
                        switch(step){
                            case ActivatorEZStepCode.DEVICE_BIND_SUCCESS:
                                Toast.makeText(HomeActivity.this, "Device bind successful", Toast.LENGTH_LONG).show();
                                break;

                            case ActivatorEZStepCode.DEVICE_FIND:
                                Toast.makeText(HomeActivity.this, "New device found!", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                })
        );
    }

    private void initViews(){
        cvDevice = findViewById(R.id.cvDevice);
        btnsearch = findViewById(R.id.btnSearch);
        tvDeviceName = findViewById(R.id.tvDeviceName);
        tvDeviceId = findViewById(R.id.tvDeviceId);
        tvProductId = findViewById(R.id.tvProductId);
    }

}
