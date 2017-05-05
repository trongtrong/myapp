package a200390.pctruong.com.appthoitrang.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import a200390.pctruong.com.appthoitrang.Adapter.Custom_Adapter;
import a200390.pctruong.com.appthoitrang.DTO.AoNamNu;

/**
 * Created by PCTRUONG on 2/11/2017.
 */

public class XuLyDuLieu {
    Context context;
    TaoBang taoBang;
    SQLiteDatabase db;

    public XuLyDuLieu(Context context) {
        this.context = context;
        taoBang=new TaoBang(context);
        taoBang.createDatabase();
        db=taoBang.openDatabase();
    }
    public boolean AddProLove(AoNamNu aoNamNu){
        db=taoBang.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(TaoBang.Ten,aoNamNu.getTen());


        Bitmap anhlon=aoNamNu.getAnhLon();
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        anhlon.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream);
        byte[] anh_lon = byteArrayBitmapStream.toByteArray();
        values.put(TaoBang.AnhLon,anh_lon);

        Bitmap anhnho=aoNamNu.getAnhLon();
        ByteArrayOutputStream byteArrayBitmapStream1 = new ByteArrayOutputStream();
        anhlon.compress(Bitmap.CompressFormat.PNG, 100, byteArrayBitmapStream1);
        byte[] anh_nho = byteArrayBitmapStream.toByteArray();
        values.put(TaoBang.AnhNho, anh_nho);


        values.put(TaoBang.Gia,aoNamNu.getGia());
        values.put(TaoBang.MoTa,aoNamNu.getMoTa());
        long id=db.insert(TaoBang.TB_YeuThich,null,values);
        if(id!=0){
            return true;
        }
        return false;
    }
    public boolean XoaSPYeuThich(int id){
        db=taoBang.getReadableDatabase();
        long kt=db.delete(TaoBang.TB_YeuThich,TaoBang.Id + " = ?",new String[]{String.valueOf(id)});
        if(kt!=0){
            return true;
        }
        return false;
    }
    public ArrayList<AoNamNu> LayDanhSachYeuThich(){
        db=taoBang.getReadableDatabase();
        ArrayList<AoNamNu> arr=new ArrayList<>();
        String sql="select * from " +TaoBang.TB_YeuThich  ;
        Cursor cursor=db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            AoNamNu aoNamNu=new AoNamNu();
            aoNamNu.setMa(cursor.getInt(0));
            aoNamNu.setTen(cursor.getString(1));

            byte [] anhlon=cursor.getBlob(3);
            Bitmap bitmap_lon= BitmapFactory.decodeByteArray(anhlon,0,anhlon.length);
            aoNamNu.setAnhLon(bitmap_lon);

            byte [] anhnho=cursor.getBlob(2);
            Bitmap bitmap_nho= BitmapFactory.decodeByteArray(anhnho,0,anhnho.length);
            aoNamNu.setAnhNho(bitmap_nho);

            aoNamNu.setGia(cursor.getInt(4));;
            aoNamNu.setMoTa(cursor.getString(5));
            arr.add(aoNamNu);

        }
        return arr;
    }
    public AoNamNu LayChiTietSanPhamYeuThich(int id){

        AoNamNu aoNamNu=new AoNamNu();
        String sql="select * from " +TaoBang.TB_YeuThich + " where " +TaoBang.Id+ " =  " +id  ;
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            aoNamNu.setMa(cursor.getInt(0));
            aoNamNu.setTen(cursor.getString(1));

            byte [] anhnho=cursor.getBlob(2);
            Bitmap bitmap_nho= BitmapFactory.decodeByteArray(anhnho,0,anhnho.length);
            aoNamNu.setAnhNho(bitmap_nho);

            byte [] anhlon=cursor.getBlob(3);
            Bitmap bitmap_lon= BitmapFactory.decodeByteArray(anhlon,0,anhlon.length);
            aoNamNu.setAnhLon(bitmap_lon);

            aoNamNu.setGia(cursor.getInt(4));
            aoNamNu.setMaLoaiCha(cursor.getInt(5));
            aoNamNu.setSLSizeS(cursor.getInt(6));
            aoNamNu.setSLSizeM(cursor.getInt(7));
            aoNamNu.setMaLoaiCha(cursor.getInt(8));
            aoNamNu.setMoTa(cursor.getString(9));
            cursor.moveToNext();

        }
        return aoNamNu;
    }
    public AoNamNu LayChiTietSanPham(int id){

        AoNamNu aoNamNu=new AoNamNu();
        String sql="select * from " +TaoBang.TB_NAME + " where " +TaoBang.Id+ " =  " +id  ;
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            aoNamNu.setMa(cursor.getInt(0));
            aoNamNu.setTen(cursor.getString(1));

            byte [] anhnho=cursor.getBlob(2);
            Bitmap bitmap_nho= BitmapFactory.decodeByteArray(anhnho,0,anhnho.length);
            aoNamNu.setAnhNho(bitmap_nho);

            byte [] anhlon=cursor.getBlob(3);
            Bitmap bitmap_lon= BitmapFactory.decodeByteArray(anhlon,0,anhlon.length);
            aoNamNu.setAnhLon(bitmap_lon);

            aoNamNu.setGia(cursor.getInt(4));
            aoNamNu.setMaLoaiCha(cursor.getInt(5));
            aoNamNu.setSLSizeS(cursor.getInt(6));
            aoNamNu.setSLSizeM(cursor.getInt(7));
            aoNamNu.setMaLoaiCha(cursor.getInt(8));
            aoNamNu.setMoTa(cursor.getString(9));
            cursor.moveToNext();

        }
        return aoNamNu;
    }
    public ArrayList<AoNamNu> LayDanhSachAoNam(){
        db=taoBang.getReadableDatabase();
        ArrayList<AoNamNu> arr=new ArrayList<>();
        String sql="select * from " +TaoBang.TB_NAME + " where " +TaoBang.MaLoaiCha+ " = 0 " ;
        Cursor cursor=db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            AoNamNu aoNamNu=new AoNamNu();
            aoNamNu.setMa(cursor.getInt(0));
            aoNamNu.setTen(cursor.getString(1));
            byte [] anhnho=cursor.getBlob(2);
            Bitmap bitmap_nho= BitmapFactory.decodeByteArray(anhnho,0,anhnho.length);
            aoNamNu.setAnhNho(bitmap_nho);

            byte [] anhlon=cursor.getBlob(3);
            Bitmap bitmap_lon= BitmapFactory.decodeByteArray(anhlon,0,anhlon.length);
            aoNamNu.setAnhLon(bitmap_lon);
            aoNamNu.setGia(cursor.getInt(4));
            aoNamNu.setMaLoaiCha(cursor.getInt(5));
            aoNamNu.setSLSizeS(cursor.getInt(6));
            aoNamNu.setSLSizeM(cursor.getInt(7));
            aoNamNu.setMaLoaiCha(cursor.getInt(8));
            aoNamNu.setMoTa(cursor.getString(9));
            arr.add(aoNamNu);

        }
        return arr;
    }

    public ArrayList<AoNamNu> LayDanhSachAoNu(){
        db=taoBang.getReadableDatabase();
        ArrayList<AoNamNu> arr=new ArrayList<>();
        String sql="select * from " +TaoBang.TB_NAME + " where " +TaoBang.MaLoaiCha+ " = 2 " ;
        Cursor cursor=db.rawQuery(sql,null);
        while (cursor.moveToNext()){
            AoNamNu aoNamNu=new AoNamNu();
            aoNamNu.setMa(cursor.getInt(0));
            aoNamNu.setTen(cursor.getString(1));
            byte [] anhnho=cursor.getBlob(2);
            Bitmap bitmap_nho= BitmapFactory.decodeByteArray(anhnho,0,anhnho.length);
            aoNamNu.setAnhNho(bitmap_nho);

            byte [] anhlon=cursor.getBlob(3);
            Bitmap bitmap_lon= BitmapFactory.decodeByteArray(anhlon,0,anhlon.length);
            aoNamNu.setAnhLon(bitmap_lon);
            aoNamNu.setGia(cursor.getInt(4));
            aoNamNu.setMaLoaiCha(cursor.getInt(5));
            aoNamNu.setSLSizeS(cursor.getInt(6));
            aoNamNu.setSLSizeM(cursor.getInt(7));
            aoNamNu.setMaLoaiCha(cursor.getInt(8));
            aoNamNu.setMoTa(cursor.getString(9));
            arr.add(aoNamNu);

        }
        return arr;
    }
    public int KiemTras(int id){
        int kiemtra=0;
        String kt="select " +TaoBang.SLSizeS + ", sum( " +TaoBang.SLSizeS + " ) from  " + TaoBang.TB_NAME + " where " +TaoBang.Id + " = " +id + " and " +TaoBang.SLSizeS+ " != '' " ;
        Cursor cursor=db.rawQuery(kt,null);
        while (cursor.moveToNext()){
            kiemtra=cursor.getInt(0);
        }
        return kiemtra;
    }
    public int KiemTram(int id){
        int kiemtra=0;
        String kt="select " +TaoBang.SLSizeM + ", sum( " +TaoBang.SLSizeM + " ) from  " + TaoBang.TB_NAME + " where " +TaoBang.Id + " = " +id + " and " +TaoBang.SLSizeM+ " != '' " ;
        Cursor cursor=db.rawQuery(kt,null);
        while (cursor.moveToNext()){
            kiemtra=cursor.getInt(0);
        }
        return kiemtra;
    }
    public void UpdateSL(int sl){
        String sql="update " + TaoBang.TB_NAME+  " set " + TaoBang.SLSizeS+ " = " +TaoBang.SLSizeS+ " - " +sl + " where " +TaoBang.Id+ " = 2 and " +TaoBang.MaLoaiCha+ " = 0" ;
        Cursor cursor=db.rawQuery(sql,null);
        cursor.moveToFirst();
        db.close();
    }
}
