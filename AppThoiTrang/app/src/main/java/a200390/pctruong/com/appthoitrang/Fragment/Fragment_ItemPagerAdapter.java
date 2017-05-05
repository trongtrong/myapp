package a200390.pctruong.com.appthoitrang.Fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by PVTruong on 27/02/2017.
 */

public class Fragment_ItemPagerAdapter extends FragmentPagerAdapter {
    public Fragment_ItemPagerAdapter( FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0 : fragment=new Fragment_ChiTiet(); break;
            case 1 : fragment=new Fragment_DanhGia(); break;
            case 2 : fragment=new Fragment_HoiDap() ; break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String tieude="";
        switch (position){
            case 0 : tieude = " Chi Tiết" ; break;
            case 1 : tieude =" Đánh Giá" ; break;
            case 2 : tieude =" Hỏi Đáp";
        }
        return tieude;
    }
}
