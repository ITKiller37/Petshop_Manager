/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;
import Models.LoaiDichVu;
import Models.Pair;
import Utils.Dbconnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Dang
 */
public class RP_LoaiDichVu {
    private Connection conn;
    
    public RP_LoaiDichVu(){
        this.conn = Dbconnection.getConnection();
    }
    
    public ArrayList<LoaiDichVu> search(String keyWord){
        String SQL = "SELECT * FROM LoaiDichVu";
        
        if(keyWord.trim().length()!=0){
            SQL += " WHERE MaLDV LIKE ? OR TenLDV LIKE ?";
        }
        
        ArrayList<LoaiDichVu> ds = new ArrayList<>();
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            if(keyWord.trim().length() != 0){
                ps.setString(1,"%" + keyWord + "%");
                ps.setString(2, "%" + keyWord + "%");
            }
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {                
                String maLDV = rs.getString("MaLDV");
                String tenLDV = rs.getString("TenLDV");
                String ghiChu = rs.getString("GhiChu");
                ds.add(new LoaiDichVu(maLDV, tenLDV, ghiChu));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    public void creat(LoaiDichVu ldv){
        String SQL = "INSERT INTO LoaiDichVu(MaLDV,TenLDV,GhiChu) VALUES(?,?,?)";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, ldv.getMaLDV());
            ps.setString(2, ldv.getTenLDV());
            ps.setString(3, ldv.getGhiChu());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update(LoaiDichVu ldv){
        String SQL = "UPDATE LoaiDichVu SET TenLDV = ?, GhiChu = ? WHERE MaLDV = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, ldv.getTenLDV());
            ps.setString(2, ldv.getGhiChu());
            ps.setString(3, ldv.getMaLDV());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void delete(String ma){
        String SQL = "DELETE FROM LoaiDichVu WHERE MaLDV = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, ma);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     public List<String> getDanhSachLoaiDichVu() {
        String SQL = "SELECT TenLDV FROM LoaiDichVu";
        List<String> danhSachLoaiDichVu = new ArrayList<>();
        
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                danhSachLoaiDichVu.add(rs.getString("TenLDV"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return danhSachLoaiDichVu;
    }
     
     public List<Pair<String, String>> getDanhSachDichVuTheoLoai(String loaiDichVu) {
    String SQL = "SELECT dv.MaDV, dv.TenDV FROM DichVu dv " +
                 "JOIN LoaiDichVu ldv ON dv.MaLDV = ldv.MaLDV " +
                 "WHERE ldv.TenLDV = ?";
     List<Pair<String, String>> danhSachDichVu = new ArrayList<>();;
    
    try {
        PreparedStatement ps = this.conn.prepareStatement(SQL);
        ps.setString(1, loaiDichVu);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
           String maDV = rs.getString("MaDV");
            String tenDV = rs.getString("TenDV");
            danhSachDichVu.add(new Pair<>(maDV, tenDV));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return danhSachDichVu;
}
}
