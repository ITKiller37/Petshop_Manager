/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Dang
 */
public class LoaiDichVu {
    private String maLDV;
    private String tenLDV;
    private String ghiChu;

    public LoaiDichVu() {
    }

    public LoaiDichVu(String maLDV, String tenLDV, String ghiChu) {
        this.maLDV = maLDV;
        this.tenLDV = tenLDV;
        this.ghiChu = ghiChu;
    }

    public String getMaLDV() {
        return maLDV;
    }

    public void setMaLDV(String maLDV) {
        this.maLDV = maLDV;
    }

    public String getTenLDV() {
        return tenLDV;
    }

    public void setTenLDV(String tenLDV) {
        this.tenLDV = tenLDV;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    
}
