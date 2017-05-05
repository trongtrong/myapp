package a200390.pctruong.com.appthoitrang;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import a200390.pctruong.com.appthoitrang.DAO.XuLyDuLieu;
import a200390.pctruong.com.appthoitrang.DTO.AoNamNu;
import a200390.pctruong.com.appthoitrang.Fragment.Fragment_ItemPagerAdapter;

/**
 * Created by PCTRUONG on 2/11/2017.
 */

public class Activity_DS_Detail extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView, img_LayAnh;
    TextView txt_ten, txt_gia, txt_mota, txt_SL_HienCo;
    Button btnmuahang;
    ImageButton img_yeuthich;
    Spinner spinner;
    AoNamNu aoNamNu;
    EditText edtsl;
    int id;
    XuLyDuLieu xuLyDuLieu;
    int sl;
    ArrayAdapter<String> adapter;
    String size;
    LinearLayout linearLayout_chitiet;
    FragmentManager manager;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_detail);
        manager = getFragmentManager();
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_item_detail);
        viewPager = (ViewPager) findViewById(R.id.viewpager_item_detail);

        imageView = (ImageView) findViewById(R.id.img_anhlon);
        txt_ten = (TextView) findViewById(R.id.txt_ten_sanpham);
        txt_gia = (TextView) findViewById(R.id.txt_gia_sanpham);
        linearLayout_chitiet = (LinearLayout) findViewById(R.id.linearlayout_chitet);
        linearLayout_chitiet.setOnClickListener(On_Click_Chitiet);
        btnmuahang = (Button) findViewById(R.id.btn_muahang);
        img_yeuthich = (ImageButton) findViewById(R.id.img_yeuthich);
        img_yeuthich.setOnClickListener(Onclick_YeuThich);
        btnmuahang.setOnClickListener(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        xuLyDuLieu = new XuLyDuLieu(this);
        aoNamNu = xuLyDuLieu.LayChiTietSanPham(id);
        imageView.setImageBitmap(aoNamNu.getAnhLon());
        txt_ten.setText(aoNamNu.getTen());
        txt_gia.setText(aoNamNu.getGia() + "VND");


    }

    View.OnClickListener On_Click_Chitiet = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Dialog dialog = new Dialog(Activity_DS_Detail.this);
            dialog.setContentView(R.layout.item_detail);
            Fragment_ItemPagerAdapter fragment_itemPagerAdapter = new Fragment_ItemPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(fragment_itemPagerAdapter);
            tabLayout.setupWithViewPager(viewPager);
            dialog.show();
        }
    };
    View.OnClickListener Onclick_YeuThich = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AoNamNu aonamnu = new AoNamNu();
            aonamnu.setTen(aoNamNu.getTen());
            aonamnu.setAnhLon(aoNamNu.getAnhLon());
            aonamnu.setAnhNho(aoNamNu.getAnhNho());
            aonamnu.setGia(aoNamNu.getGia());
            aonamnu.setMoTa(aoNamNu.getMoTa());
            boolean kt = xuLyDuLieu.AddProLove(aonamnu);
            if (kt) {
                Toast.makeText(Activity_DS_Detail.this, " Thêm thành  công 1 sản phẩm vào mục yêu thích", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Activity_DS_Detail.this, " Có lỗi sảy ra ", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View v) {
        Dialog dialog = new Dialog(Activity_DS_Detail.this);
        dialog.setContentView(R.layout.activity__muahang);
        dialog.setTitle(aoNamNu.getTen());
        img_LayAnh = (ImageView) dialog.findViewById(R.id.img_LayAnh);
        spinner = (Spinner) dialog.findViewById(R.id.spiner);
        edtsl = (EditText) dialog.findViewById(R.id.edtSoLuong);
        txt_SL_HienCo = (TextView) dialog.findViewById(R.id.txt_LaySLHienCo);
        Button btnTiepTuc = (Button) dialog.findViewById(R.id.btn_TiepTuc);
        Button btnGioHang = (Button) dialog.findViewById(R.id.btn_GioHang);
        img_LayAnh.setImageBitmap(aoNamNu.getAnhNho());
        ArrayList<String> list = new ArrayList<>();
        list.add("S");
        list.add("M");
        adapter = new ArrayAdapter<String>(Activity_DS_Detail.this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);

        dialog.show();
        btnGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                String kt_sl = (edtsl.getText().toString()).trim();
                size = (String) spinner.getSelectedItem();
                if (kt_sl == null && kt_sl.equals("")) {
                    Toast.makeText(Activity_DS_Detail.this, " Số lượng không được để rỗng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (kt_sl.length() > 0) {
                    sl = Integer.parseInt(kt_sl);

                    if (sl != 0 && sl > 0) {
                        if (size.equals("S")) {
                            int dem = xuLyDuLieu.KiemTras(id);
                            if (sl <= dem) {
                                txt_SL_HienCo.setText("");
                                intent = new Intent(Activity_DS_Detail.this, Activity_GioHang.class);
                                intent.putExtra("ID", id);
                                intent.putExtra("Gia", aoNamNu.getGia());
                                intent.putExtra("SL", sl);
                                startActivity(intent);
                            } else {

                                txt_SL_HienCo.setText("Số lượng Size S hiện có :" + dem);
                            }

                        }
                        if (size.equals("M")) {
                            int dem = xuLyDuLieu.KiemTram(id);

                            if (sl > dem) {
                                txt_SL_HienCo.setText("Số lượng Size S hiện có :" + dem);
                            } else {
                                txt_SL_HienCo.setText("");
                                intent = new Intent(Activity_DS_Detail.this, Activity_GioHang.class);
                                intent.putExtra("ID", id);
                                intent.putExtra("SL", sl);
                                startActivity(intent);
                            }

                        }

                    } else {
                        Toast.makeText(Activity_DS_Detail.this, "SL không để rỗng ", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });

        btnTiepTuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

}
