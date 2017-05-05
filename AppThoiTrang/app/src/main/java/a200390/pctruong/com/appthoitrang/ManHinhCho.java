package a200390.pctruong.com.appthoitrang;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ManHinhCho extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinh_cho);
        if(KetNoiMang()){
            final Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try{

                        Thread.sleep(3000);

                    }catch (Exception ex){

                    }finally {
                        Intent intent=new Intent(ManHinhCho.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
            thread.start();
        }
        else{
            Toast.makeText(this, "Kết Nối thất bại", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean KetNoiMang(){
        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo in = cm.getActiveNetworkInfo();
            if (in != null && in.isConnectedOrConnecting()) {
                Toast.makeText(this, "Ket Noi Thanh Cong", Toast.LENGTH_SHORT).show();
                return true;

            }
        }
        catch (Exception ex){
            Toast.makeText(this, "Kết Nối thất bại", Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}
