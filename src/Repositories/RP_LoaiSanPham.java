/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;
import Models.LoaiSanPham;
import Models.Pair;
import Utils.Dbconnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Dang
 */
public class RP_LoaiSanPham {
    private Connection conn;
    
    public RP_LoaiSanPham(){
        this.conn = Dbconnection.getConnection();
    }
    
    public ArrayList<LoaiSanPham> search(){
        String SQL = "SELECT * FROM LoaiSanPham";
        
         ArrayList<LoaiSanPham> ds = new ArrayList<>();
         
         try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);     
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {                
                String maLSP = rs.getString("MaLSP");
                String tenLSP = rs.getString("TenLSP");             
                ds.add(new LoaiSanPham(maLSP, tenLSP));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         return ds;
    }
    
    public void creat(LoaiSanPham lsp){
        String SQL = "INSERT INTO LoaiSanPham(MaLSP,TenLSP) VALUES(?,?)";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, lsp.getMaLSP());
            ps.setString(2, lsp.getTenLSP());       
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update(LoaiSanPham lsp){
        String SQL = "UPDATE LoaiSanPham SET TenLSP = ? WHERE MaLSP = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, lsp.getTenLSP());
            ps.setString(2, lsp.getMaLSP());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void delete(String ma){
        String SQL = "DELETE FROM LoaiSanPham WHERE MaLSP = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, ma);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
      public List<String> getDanhSachLoaiSanPham() {
        String SQL = "SELECT TenLSP FROM LoaiSanPham";
        List<String> danhSachLoaiSanPham = new ArrayList<>();
        
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                danhSachLoaiSanPham.add(rs.getString("TenLSP"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return danhSachLoaiSanPham;
    }
      
      public List<Pair<String, String>> getDanhSachSanPhamTheoLoai(String loaiSanPham) {
         String SQL = "SELECT sp.MaSP, sp.TenSP FROM SanPham sp " +
                      "JOIN LoaiSanPham lsp ON sp.MaLSP = lsp.MaLSP " +
                      "WHERE lsp.TenLSP = ?";
         List<Pair<String, String>> danhSachSanPham = new ArrayList<>();;
    
       try {
        PreparedStatement ps = this.conn.prepareStatement(SQL);
        ps.setString(1, loaiSanPham);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
           String maSP = rs.getString("MaSP");
            String tenSP = rs.getString("TenSP");
            danhSachSanPham.add(new Pair<>(maSP, tenSP));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return danhSachSanPham;
}
}
