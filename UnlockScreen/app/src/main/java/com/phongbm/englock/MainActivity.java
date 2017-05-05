package com.phongbm.englock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    // Khai báo thuộc tính
    private Switch swtEnglockService;
    public SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermission();
        initViews();
        initListeners();
    }

    private void requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //neu chua dc cap quyền
            if(!Settings.canDrawOverlays(this)){
                //xin quyền bằng cách bật giao diện, giao diện này là của hệ thống
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intent, 200);

            }
        }else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //neu chua dc cap quyền
                if (!Settings.canDrawOverlays(this)) {
                    finish();
                }
            }
        }
    }
    private void initViews() {
        // Ánh xạ đối tượng Switch từ bên file giao diện .xml
        swtEnglockService = (Switch) findViewById(R.id.swt_englock_service);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        //lấy ra trạng thá lưu trữ để hiện thị lên giao diện switch
        if(pref.getBoolean("STATUS", false == true)){ // có thể viết là if(pref.gerbolean("status", false))
            swtEnglockService.setChecked(true);
        }else {
            swtEnglockService.setChecked(false);
        }
    }

    private void initListeners() {
        // Đăng ký lắng nghe sự kiện
        // người dùng chọn chức năng On hoặc Off
        swtEnglockService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { // Người dùng chọn On
                    // Kích hoạt Service
                    // Sử dụng Intent để kích hoạt
                    Intent intent = new Intent(getBaseContext(), EnglockService.class);
                    startService(intent);
                    //lưu trạng thái on vào share preferences
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("STATUS", true);
                    editor.apply();
                } else { // Người dùng chọn Off
                    // Hủy kích hoạt Service
                    Intent intent = new Intent(getBaseContext(), EnglockService.class);
                    stopService(intent);
                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean("STATUS", false);
                    editor.apply();
                }
            }
        });
    }

}