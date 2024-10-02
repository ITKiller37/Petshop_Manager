/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Dang
 */
public class DichVu {
    private String maDV;
    private String tenDV;
    private String maLDV;
    private float gia;

    public DichVu() {
    }

    public DichVu(String maDV, String tenDV, String maLDV, float gia) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.maLDV = maLDV;
        this.gia = gia;
    }

    public String getMaDV() {
        return maDV;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    public String getMaLDV() {
        return maLDV;
    }

    public void setMaLDV(String maLDV) {
        this.maLDV = maLDV;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }
  
}
