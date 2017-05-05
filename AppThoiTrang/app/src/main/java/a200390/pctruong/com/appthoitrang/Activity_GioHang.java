package a200390.pctruong.com.appthoitrang;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import a200390.pctruong.com.appthoitrang.DAO.XuLyDuLieu;
import a200390.pctruong.com.appthoitrang.DTO.AoNamNu;

public class Activity_GioHang extends AppCompatActivity  {
    TextView txt_SPGH ,txtGia_GH,txtSL_GH ,txtTongTien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_giohang);
        txt_SPGH= (TextView) findViewById(R.id.txt_SP_GioHang);
        txtGia_GH= (TextView) findViewById(R.id.txt_Gia_GH);
        txtSL_GH = (TextView) findViewById(R.id.txt_SL_GioHang);
        txtTongTien = (TextView) findViewById(R.id.txt_TongTien);
        Intent intent=getIntent();
        int ID=intent.getIntExtra("ID",0);
        int SL=intent.getIntExtra("SL",0);
        int GIA=intent.getIntExtra("GIA",0);
        XuLyDuLieu xuLyDuLieu=new XuLyDuLieu(this);
        AoNamNu aoNamNu=xuLyDuLieu.LayChiTietSanPham(ID);

        txt_SPGH.setText(aoNamNu.getTen());
        txtSL_GH.setText("Số lượng:" +SL );
        txtGia_GH.setText("Giá " +aoNamNu.getGia());

        int Tong=SL*aoNamNu.getGia();

        txtTongTien.setText("Tổng Tiền :" +String.valueOf(Tong) );




    }

    private void init() {

    }

}
