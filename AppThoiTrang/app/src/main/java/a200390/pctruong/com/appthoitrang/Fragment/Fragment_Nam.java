package a200390.pctruong.com.appthoitrang.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import a200390.pctruong.com.appthoitrang.Activity_DS_Detail;
import a200390.pctruong.com.appthoitrang.Adapter.Custom_Adapter;
import a200390.pctruong.com.appthoitrang.DAO.XuLyDuLieu;
import a200390.pctruong.com.appthoitrang.DTO.AoNamNu;
import a200390.pctruong.com.appthoitrang.R;
import a200390.pctruong.com.appthoitrang.RecyclerItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_Nam extends Fragment {

    RecyclerView recyclerView;
    Custom_Adapter adapter;
    ArrayList<AoNamNu> arrayList;
    XuLyDuLieu xuLyDuLieu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_namnu, container, false);
        recyclerView= (RecyclerView) view.findViewById(R.id.recycle_view);
        xuLyDuLieu=new XuLyDuLieu(getActivity());
        arrayList=xuLyDuLieu.LayDanhSachAoNam();
        adapter=new Custom_Adapter(R.layout.item_custom,getActivity(),arrayList);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getActivity(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent=new Intent(getActivity(),Activity_DS_Detail.class);
                        intent.putExtra("id",arrayList.get(position).getMa());
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // Sự kiện khi nhấn giữ vào item
                    }
                }));

        return view;
    }

}
