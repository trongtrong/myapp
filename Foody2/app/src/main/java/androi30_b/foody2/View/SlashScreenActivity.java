package androi30_b.foody2.View;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import androi30_b.foody2.R;

public class SlashScreenActivity extends AppCompatActivity {
    TextView txtPhienBan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flashscreen);
        txtPhienBan = (TextView) findViewById(R.id.txtPhienBan);


        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            txtPhienBan.setText(getString(R.string.phienban) + " " + packageInfo.versionName);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getBaseContext(), DangNhapActivity.class);
                    startActivity(intent);
                }
            },2000);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
