package com.phongbm.englock;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;

// Để sử dụng Service cần 2 bước:
// Bước 1: Đăng ký với hệ thống Android.
// Đăng ký ở trong file AndroidManifest.xml
// Bước 2: Kích hoạt/ Hủy kích hoạt
public class EnglockService extends Service {
    // Khai báo thuộc tính
    private ScreenStateReceiver screenStateReceiver;
    private WindowManager windowManager;
    private LockScreenView lockScreenView;
    private boolean isLockScreenAdded; //bolean mac dịnh là false
    // đối tượng dùng để truyền và nhận tin nhắn
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        // Phương thức đầu tiên được chạy khi
        // Service được kích hoạt
        registerScreenStateReceiver();
        disableKeyguard();
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1000){
                    hideLockScreen();

                }
            }
        };
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Tùy chọn START_STICKY cho phép
        // Service tự động sống lại và chạy tiếp
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        unregisterScreenStateReceiver();
        enableKeyguard();
        super.onDestroy();
    }

    // Hiển thị giao diện câu hỏi
    private void showLockScreenView() {
        if(isLockScreenAdded){
            lockScreenView.bindData();
            return;
        }

        // Tạo ra đối tượng
        lockScreenView = new LockScreenView(this, handler);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Hiển thị
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.CENTER;
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.TRANSPARENT;

        windowManager.addView(lockScreenView, params);
        isLockScreenAdded = true;
    }
    //mở khóa
    private void hideLockScreen(){
        if(isLockScreenAdded){
            windowManager.removeView(lockScreenView);;
            isLockScreenAdded = false;
        }
    }

    // Hủy bảo vệ màn hình
    // Thì chúng ta mới có thể hiển thị được Custom View
    private void disableKeyguard() {
        KeyguardManager manager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = manager.newKeyguardLock("IN");
        lock.disableKeyguard();
    }

    // Kích hoạt lại bảo vệ màn hình
    private void enableKeyguard() {
        KeyguardManager manager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = manager.newKeyguardLock("IN");
        lock.reenableKeyguard();
    }


    // Đối tượng BroadcastReceiver được dùng để
    // lắng nghe các "sự kiện" được phát ra bởi hệ thống
    // Cụ thể: lắng nghe sự kiện "SCREEN_OFF"
    private class ScreenStateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Đoạn code bên dưới được chạy khi
            // màn hình tắt
            // Hiển thị giao diện câu hỏi
            showLockScreenView();
        }
    }

    // Dùng để đăng ký sử dụng BR
    private void registerScreenStateReceiver() {
        // Bước 1: Khởi tạo đối tượng
        screenStateReceiver = new ScreenStateReceiver();

        // Bước 2: Tạo bộ lọc
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);

        // Bước 3: Đăng ký
        registerReceiver(screenStateReceiver, filter);
    }

    // Dùng để hủy đăng ký sử dụng BR
    private void unregisterScreenStateReceiver() {
        // Hủy đăng ký
        unregisterReceiver(screenStateReceiver);
    }

}