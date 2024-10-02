/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;
import Models.NhanVien;
import Models.Pair;
import Utils.Dbconnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Dang
 */
public class RP_NhanVien {
    private Connection conn;
    
    public RP_NhanVien(){
        this.conn = Dbconnection.getConnection();
    }
    
    public ArrayList<NhanVien> search(String keyWord){
         String SQL = "SELECT * FROM NhanVien";
        
        if(keyWord.trim().length()!=0){
            SQL += " WHERE TenNV LIKE ? OR SDT LIKE ? OR Email LIKE ?";
        }
         ArrayList<NhanVien> ds = new ArrayList<>();
         
         try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            if(keyWord.trim().length() != 0){
                ps.setString(1,"%" + keyWord + "%");
                ps.setString(2, "%" + keyWord + "%");
                ps.setString(3, "%" + keyWord + "%");
            }
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {                
                int maNV = rs.getInt("MaNV");
                String tenNV = rs.getString("TenNV");
                String sdt = rs.getString("SDT");
                String email = rs.getString("Email");
                String chucVu = rs.getString("ChucVu");
                String congViec = rs.getString("CongViec");
                float luong = rs.getFloat("Luong");
                ds.add(new NhanVien(maNV, tenNV, sdt, email, chucVu, congViec, luong));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
         return ds;
    }
    
    public void creat(NhanVien nv){
        String SQL = "INSERT INTO NhanVien(TenNV,SDT,Email,ChucVu,CongViec,Luong) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, nv.getTenNV());
            ps.setString(2, nv.getSdt());
            ps.setString(3, nv.getEmail());
            ps.setString(4, nv.getChucVu());
            ps.setString(5, nv.getCongViec());
            ps.setFloat(6, nv.getLuong());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     public void update(NhanVien nv){
        String SQL = "UPDATE NhanVien SET TenNV = ?, SDT = ?, Email = ?, ChucVu = ?, CongViec = ?, Luong = ? WHERE MaNV = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, nv.getTenNV());
            ps.setString(2, nv.getSdt());
            ps.setString(3, nv.getEmail());
            ps.setString(4, nv.getChucVu());
            ps.setString(5, nv.getCongViec());
            ps.setFloat(6, nv.getLuong());
            ps.setInt(7, nv.getMaNV());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     public void delete(int ma){
        String SQL = "DELETE FROM NhanVien WHERE MaNV = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setInt(1, ma);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     public List<Pair<Integer, String>>  getDanhSachNhanVien(){
         String SQL = "SELECT MaNV, TenNV FROM NhanVien";
         
         List<Pair<Integer, String>> dsNhanVien = new ArrayList<>();
         try {
             PreparedStatement ps = this.conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery();
             while (rs.next()) { 
                 int maNV = rs.getInt("MaNV");
                 String tenNV = rs.getString("TenNV");                
                 dsNhanVien.add(new Pair<>(maNV, tenNV));
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return dsNhanVien;
     }
}
