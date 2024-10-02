/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;
import Models.Pair;
import Models.SanPham;
import Utils.Dbconnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Dang
 */
public class RP_SanPham {
    private Connection conn;
    
    public RP_SanPham(){
        this.conn = Dbconnection.getConnection();
    }
    
    public ArrayList<SanPham> search(String keyWord){
         String SQL = "SELECT * FROM SanPham";
        
        if(keyWord.trim().length()!=0){
            SQL += " WHERE MaSP LIKE ? OR TenSP LIKE ?";
        }
         ArrayList<SanPham> ds = new ArrayList<>();
         
         try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            if(keyWord.trim().length() != 0){
                ps.setString(1,"%" + keyWord + "%");
                ps.setString(2, "%" + keyWord + "%");
            }
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {                
                String maSP = rs.getString("MaSP");
                String maLSP = rs.getString("MaLSP");
                String tenSP = rs.getString("TenSP");
                float gia = rs.getFloat("Gia");
                int soLuong = rs.getInt("SoLuong");
                String chucVu = rs.getString("GhiChu");
                ds.add(new SanPham(maSP, maLSP, tenSP, gia, soLuong, chucVu));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         return ds;
    }
    
    public void creat(SanPham sp){
        String SQL = "INSERT INTO SanPham(MaSp,MaLSP,TenSP,Gia,SoLuong,GhiChu) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, sp.getMaSP());
            ps.setString(2, sp.getMaLSP());
            ps.setString(3, sp.getTenSP());
            ps.setFloat(4, sp.getGia());
            ps.setInt(5, sp.getSoLuong());
            ps.setString(6, sp.getGhiChu());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     public void update(SanPham sp){
        String SQL = "UPDATE SanPham SET TenSP = ?, Gia = ?, SoLuong = ?, GhiChu = ? WHERE MaSP = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, sp.getTenSP());
            ps.setFloat(2, sp.getGia());
            ps.setInt(3, sp.getSoLuong());
            ps.setString(4, sp.getGhiChu());
            ps.setString(5, sp.getMaSP());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     public void delete(String ma){
        String SQL = "DELETE FROM SanPham WHERE MaSP = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, ma);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     
     public float getGia(String maSanPham) {
        String SQL = "SELECT Gia FROM SanPham WHERE MaSP = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, maSanPham);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat("Gia");
            } else {
                System.out.println("Không tìm thấy sản phẩm: " + maSanPham);
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
     }
     
     public String getTenSanPham(String maSP) {
    String SQL = "SELECT TenSP FROM SanPham WHERE MaSP = ?";
    String tenSP = null;

    try {
        PreparedStatement ps = this.conn.prepareStatement(SQL);
        ps.setString(1, maSP);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            tenSP = rs.getString("TenSP");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return tenSP; 
}
}
