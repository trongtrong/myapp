package com.phongbm.englock;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Duc Nguyen on 4/3/2017.
 */

public class Database {
    private String path;//duuong dan cua file cco so du lieu
    private SQLiteDatabase sqLiteDatabase;
    public Database(){

    }
    //chuyen file dữ liệu từ asets vào trong internal app(bộ nhớ trong của ứng dụng)
    public void copyDatabase(Context context){
        try {
            // buoc 0: kiem tra xem databa da co chua
            path = Environment.getDataDirectory().getAbsolutePath() + "/data/com.phongbm.englock/Database";
            File file = new File(path);
            if(file.exists()){
                return;
            }
            //step 1: Mở một luồng để đọc dữ liệu
            //sử dụng input stream
            InputStream is = context.getAssets().open("Database.db");
            //buoc 2: mo 1 luong de ghi du lieu
            FileOutputStream fos = new FileOutputStream(file);

            //buoc 3: tiến hành đọc ghi
            byte[] b = new byte[1024];
            int length;
            //trong ghi doc du lieu
            while ((length = is.read(b)) != -1){
                fos.write(b, 0, length);
            }
            //dong chuong trinh
            is.close();
            fos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        }
    private void open(){
        sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
    }
    private void close(){
        sqLiteDatabase.close();
    }
    public Word[] getRandomTwoWord(){
        open();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Technology order by Random() limit 2", null, null);
        Word[] words = new Word[2];
        cursor.moveToFirst();
        // sau ban ghi cuoi cung
        int i = 0;
        while(!cursor.isAfterLast()){
            String en = cursor.getString(0);
            String vi = cursor.getString(1);
            words[i] = new Word(en,vi);
            i++;
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return words;
    }
}
