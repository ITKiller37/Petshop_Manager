/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Dang
 */
public class ThongKeKH {
    private String tenKH;
    private String sdt;
    private String diaChi;
    private int tongHD;

    public ThongKeKH() {
    }

    public ThongKeKH(String tenKH, String sdt, String diaChi, int tongHD) {
        this.tenKH = tenKH;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.tongHD = tongHD;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public int getTongHD() {
        return tongHD;
    }

    public void setTongHD(int tongHD) {
        this.tongHD = tongHD;
    }
    
    
}
