/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;
import Models.HoaDon;
import Models.HoaDonChiTiet;
import Utils.Dbconnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Dang
 */
public class RP_HoaDon {
    private Connection conn;
    
    public RP_HoaDon(){
        this.conn = Dbconnection.getConnection();
    }
    
      public ArrayList<HoaDon> search(){
          String SQL = "SELECT hd.MaHD, kh.TenKH, nv.TenNV, hd.TongTien, hd.NgayTao, hd.TrangThai " +
              "FROM HoaDon hd " +
              "JOIN KhachHang kh ON kh.MaKH = hd.MaKH " +
              "JOIN NhanVien nv ON nv.MaNV = hd.MaNV";
          
          ArrayList<HoaDon> ds = new ArrayList<>();
          
          try {
              PreparedStatement ps = this.conn.prepareStatement(SQL);
              ps.execute();
              ResultSet rs = ps.getResultSet();
              while (rs.next()) {                  
                  int maHD = rs.getInt("MaHD");
                  String tenKH = rs.getString("TenKH");
                  String tenNV = rs.getString("TenNV");
                  float tongTien = rs.getFloat("TongTien");
                  String ngayTao = rs.getString("NgayTao");
                  String trangThai = rs.getString("TrangThai");
                  ds.add(new HoaDon(maHD, tenKH, tenNV, tongTien, ngayTao, trangThai));
              }
          } catch (Exception e) {
              e.printStackTrace();
          }
          return ds;
      }
      
      public int create(HoaDon hd, int maKH, int maNV) {
         String SQL = "INSERT INTO HoaDon (MaKH, MaNV, TongTien, NgayTao,TrangThai) VALUES (?, ?, ?, ?,?)";
         int generatedId = -1; // Khởi tạo giá trị mặc định cho ID
         
    try {
        PreparedStatement ps = this.conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, maKH); 
        ps.setInt(2, maNV); 
        ps.setFloat(3, hd.getTongTien());
        ps.setString(4, hd.getNgayTao()); 
        ps.setString(5, hd.getTrangThai());
        
        ps.execute();
        
          // Lấy ID của hóa đơn vừa tạo
        ResultSet generatedKeys = ps.getGeneratedKeys();
        if (generatedKeys.next()) {
            generatedId = generatedKeys.getInt(1); // Lấy ID
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
     return generatedId;
  }
      
      public void delete(int ma){
        String SQL = "DELETE FROM HoaDon WHERE MaHD = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setInt(1, ma);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      
      public void updateTrangThai(int maHD, String trangThai) throws SQLException {
        String SQL = "UPDATE HoaDon SET TrangThai = ? WHERE MaHD = ?";
        try (PreparedStatement ps = this.conn.prepareStatement(SQL)) {
           ps.setString(1, trangThai);
           ps.setInt(2, maHD);
           ps.executeUpdate();
        }
    } 
      
      public void addChiTietHoaDon(int maHD, String maSP, String maDV, int soLuong, float gia) {
          String SQL = "INSERT INTO HoaDonChiTiet (MaHD, MaSP, MaDV, SoLuong, Gia) VALUES (?, ?, ?, ?, ?)";

          try (PreparedStatement ps = this.conn.prepareStatement(SQL)) {
            ps.setInt(1, maHD);
            ps.setString(2, maSP);
            ps.setString(3, maDV);
            ps.setInt(4, soLuong);
            ps.setFloat(5, gia);
            ps.executeUpdate();
         } catch (SQLException e) {
        e.printStackTrace();
        }
    }
      
      public List<HoaDonChiTiet> getChiTietHoaDonByMaHD(int maHD) {
         List<HoaDonChiTiet> chiTietList = new ArrayList<>();
         String SQL = "SELECT MaHDCT, MaHD, MaSP, MaDV, SoLuong, Gia FROM HoaDonChiTiet WHERE MaHD = ?";

       try (PreparedStatement ps = this.conn.prepareStatement(SQL)) {
        ps.setInt(1, maHD);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            
            HoaDonChiTiet chiTiet = new HoaDonChiTiet(
                rs.getInt("MaHDCT"),
                rs.getInt("MaHD"),
                rs.getString("MaSP"), 
                rs.getString("MaDV"), 
                rs.getInt("SoLuong"),
                rs.getFloat("Gia")
            );
            chiTietList.add(chiTiet);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return chiTietList;
}
}
