package a200390.pctruong.com.appthoitrang.DTO;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by PCTRUONG on 2/11/2017.
 */

public class AoNamNu implements Serializable {
    Integer Ma ,Gia,SLSizeS,SLSizeM,MaLoaiCha,MaLoaiID;
    String MoTa;
    String Ten;
    Bitmap AnhNho,AnhLon;
    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }



    public Integer getMa() {
        return Ma;
    }

    public void setMa(Integer ma) {
        Ma = ma;
    }

    public Integer getGia() {
        return Gia;
    }

    public void setGia(Integer gia) {
        Gia = gia;
    }

    public Integer getSLSizeS() {
        return SLSizeS;
    }

    public void setSLSizeS(Integer SLSizeS) {
        this.SLSizeS = SLSizeS;
    }

    public Integer getSLSizeM() {
        return SLSizeM;
    }

    public void setSLSizeM(Integer SLSizeM) {
        this.SLSizeM = SLSizeM;
    }

    public Integer getMaLoaiCha() {
        return MaLoaiCha;
    }

    public void setMaLoaiCha(Integer maLoaiCha) {
        MaLoaiCha = maLoaiCha;
    }

    public Integer getMaLoaiID() {
        return MaLoaiID;
    }

    public void setMaLoaiID(Integer maLoaiID) {
        MaLoaiID = maLoaiID;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public Bitmap getAnhNho() {
        return AnhNho;
    }

    public void setAnhNho(Bitmap anhNho) {
        AnhNho = anhNho;
    }

    public Bitmap getAnhLon() {
        return AnhLon;
    }

    public void setAnhLon(Bitmap anhLon) {
        AnhLon = anhLon;
    }
}
