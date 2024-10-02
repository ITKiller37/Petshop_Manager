/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author Dang
 */
public class ThongKeSP {
    private String tenSP;
    private int tongSL;
    private int slConLai;
    private int slDaBan;

    public ThongKeSP() {
    }

    public ThongKeSP(String tenSP, int tongSL, int slConLai, int slDaBan) {
        this.tenSP = tenSP;
        this.tongSL = tongSL;
        this.slConLai = slConLai;
        this.slDaBan = slDaBan;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public int getTongSL() {
        return tongSL;
    }

    public void setTongSL(int tongSL) {
        this.tongSL = tongSL;
    }

    public int getSlConLai() {
        return slConLai;
    }

    public void setSlConLai(int slConLai) {
        this.slConLai = slConLai;
    }

    public int getSlDaBan() {
        return slDaBan;
    }

    public void setSlDaBan(int slDaBan) {
        this.slDaBan = slDaBan;
    }
    
    
}
