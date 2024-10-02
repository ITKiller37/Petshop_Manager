/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Dang
 */
public class Pet {
    private int maPet;
    private String tenPet;
    private String loaiPet;
    private String giongLoai;
    private int tuoi;
    private float canNang;
    private int trangThaiTiemChung;
    private int maKH;

    public Pet() {
    }

    public Pet(int maPet, String tenPet, String loaiPet, String giongLoai, int tuoi, float canNang, int trangThaiTiemChung, int maKH) {
        this.maPet = maPet;
        this.tenPet = tenPet;
        this.loaiPet = loaiPet;
        this.giongLoai = giongLoai;
        this.tuoi = tuoi;
        this.canNang = canNang;
        this.trangThaiTiemChung = trangThaiTiemChung;
        this.maKH = maKH;
    }

    public int getMaPet() {
        return maPet;
    }

    public void setMaPet(int maPet) {
        this.maPet = maPet;
    }

    public String getTenPet() {
        return tenPet;
    }

    public void setTenPet(String tenPet) {
        this.tenPet = tenPet;
    }

    public String getLoaiPet() {
        return loaiPet;
    }

    public void setLoaiPet(String loaiPet) {
        this.loaiPet = loaiPet;
    }

    public String getGiongLoai() {
        return giongLoai;
    }

    public void setGiongLoai(String giongLoai) {
        this.giongLoai = giongLoai;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public float getCanNang() {
        return canNang;
    }

    public void setCanNang(float canNang) {
        this.canNang = canNang;
    }

    public int getTrangThaiTiemChung() {
        return trangThaiTiemChung;
    }

    public void setTrangThaiTiemChung(int trangThaiTiemChung) {
        this.trangThaiTiemChung = trangThaiTiemChung;
    }

    public int getMaKH() {
        return maKH;
    }

    public void setMaKH(int maKH) {
        this.maKH = maKH;
    }
    
    
}
