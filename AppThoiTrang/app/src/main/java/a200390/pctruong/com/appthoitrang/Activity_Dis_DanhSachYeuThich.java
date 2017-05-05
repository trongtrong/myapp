package a200390.pctruong.com.appthoitrang;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import a200390.pctruong.com.appthoitrang.Adapter.Custom_Adapter;
import a200390.pctruong.com.appthoitrang.DAO.XuLyDuLieu;
import a200390.pctruong.com.appthoitrang.DTO.AoNamNu;

/**
 * Created by PVTruong on 22/02/2017.
 */

public class Activity_Dis_DanhSachYeuThich extends AppCompatActivity {
    RecyclerView recyclerView;
    Custom_Adapter adapter;
    ArrayList<AoNamNu> arrayList;
    XuLyDuLieu xuLyDuLieu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_namnu);
        getSupportActionBar().setTitle("Sản Phẩm Yêu Thích ");
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        xuLyDuLieu = new XuLyDuLieu(this);
        init();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                this,
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        final int id = arrayList.get(position).getMa();
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Thông Báo");
                        builder.setMessage("Bạn có muốn xóa SP " + arrayList.get(position).getTen());
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean kt = xuLyDuLieu.XoaSPYeuThich(id);
                                if (kt) {
                                    Toast.makeText(Activity_Dis_DanhSachYeuThich.this, "Xóa thành công ", Toast.LENGTH_SHORT).show();
                                    init();
                                } else {
                                    Toast.makeText(Activity_Dis_DanhSachYeuThich.this, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.create().show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
    }

    private void init() {
        arrayList = xuLyDuLieu.LayDanhSachYeuThich();
        adapter = new Custom_Adapter(R.layout.item_custom, this, arrayList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
