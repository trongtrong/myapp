package a200390.pctruong.com.appthoitrang.Fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by PCTRUONG on 2/11/2017.
 */

public class Pager_Adapter extends FragmentPagerAdapter {
    public Pager_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0 : fragment=new Fragment_Nam();break;
            case 1 : fragment=new Fragment_Nu() ; break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0 : title="Thời Trang Nam" ;break;
            case 1 : title="Thời Trang Nữ" ; break;
        }
        return title;
    }
}
