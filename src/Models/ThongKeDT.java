/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Dang
 */
public class ThongKeDT {
    private String ngayTao;
    private int tongHD;
    private int tongDoanhThu;
    private int caoNhat;
    private float trungBinh;
    private int thapNhat;

    public ThongKeDT() {
    }

    public ThongKeDT(String ngayTao, int tongHD, int tongDoanhThu, int caoNhat, float trungBinh, int thapNhat) {
        this.ngayTao = ngayTao;
        this.tongHD = tongHD;
        this.tongDoanhThu = tongDoanhThu;
        this.caoNhat = caoNhat;
        this.trungBinh = trungBinh;
        this.thapNhat = thapNhat;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getTongHD() {
        return tongHD;
    }

    public void setTongHD(int tongHD) {
        this.tongHD = tongHD;
    }

    public int getTongDoanhThu() {
        return tongDoanhThu;
    }

    public void setTongDoanhThu(int tongDoanhThu) {
        this.tongDoanhThu = tongDoanhThu;
    }

    public int getCaoNhat() {
        return caoNhat;
    }

    public void setCaoNhat(int caoNhat) {
        this.caoNhat = caoNhat;
    }

    public float getTrungBinh() {
        return trungBinh;
    }

    public void setTrungBinh(int trungBinh) {
        this.trungBinh = trungBinh;
    }

    public int getThapNhat() {
        return thapNhat;
    }

    public void setThapNhat(int thapNhat) {
        this.thapNhat = thapNhat;
    }
    
    
}
