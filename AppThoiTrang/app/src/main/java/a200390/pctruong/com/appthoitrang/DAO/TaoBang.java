package a200390.pctruong.com.appthoitrang.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by PCTRUONG on 2/11/2017.
 */

public class TaoBang extends SQLiteOpenHelper {
    public static final String DB_NAME="ThoiTrang_NamNu.sqlite";
    public static String TB_NAME="TB_AoNamNu";
    public static String TB_YeuThich="TB_YeuThich";
    public static String Id="id";
    public static String Ten="ten";
    public static String AnhNho="anhnho";
    public static String AnhLon="anhlon";
    public static String Gia="gia";
    public static String MaLoaiCha="maloaicha";
    public static String SLSizeS="slsize_s";
    public static String SLSizeM="slsize_m";
    public static String MaLoaiID="maloai_id";
    public static String MoTa="MoTa";
    Context context;
    String duongDanDatabase = "";
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public TaoBang(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
        duongDanDatabase = context.getFilesDir().getParent() + "/databases/" + DB_NAME;
    }



    public SQLiteDatabase openDatabase(){
        return SQLiteDatabase.openDatabase(duongDanDatabase, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public void copyDatabase(){
        try {
            InputStream is =  context.getAssets().open(DB_NAME);
            OutputStream os = new FileOutputStream(duongDanDatabase);
            byte[] buffer = new byte[1024];
            int lenght = 0;
            while((lenght = is.read(buffer)) > 0){
                os.write(buffer, 0, lenght);
            }

            os.flush();
            os.close();
            is.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createDatabase(){
        boolean kt = KiemTraDB();
        if(kt){
            Log.d("KetNoi", "Máy đã có database");
        }else{
            Log.d("KetNoi", "Máy chưa có database tiến hành copy dữ liệu");
            this.getWritableDatabase();
            copyDatabase();
        }
    }


    public boolean KiemTraDB(){
        SQLiteDatabase kiemTraDB = null;
        try{
            kiemTraDB = SQLiteDatabase.openDatabase(duongDanDatabase, null, SQLiteDatabase.OPEN_READONLY);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(kiemTraDB !=null){
            kiemTraDB.close();
        }
        return kiemTraDB !=null ? true : false;
    }
}
