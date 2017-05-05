package a200390.pctruong.com.appthoitrang.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a200390.pctruong.com.appthoitrang.R;

/**
 * Created by PVTruong on 27/02/2017.
 */

public class Fragment_DanhGia extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_tl_danhgia,container,false);
        return view ;
    }
}
