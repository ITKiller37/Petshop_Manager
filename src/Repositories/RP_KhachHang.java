/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;
import Models.KhachHang;
import Utils.Dbconnection;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Dang
 */
public class RP_KhachHang {
    private Connection conn;
    
    public RP_KhachHang(){
        this.conn = Dbconnection.getConnection();
    }
    
    public ArrayList<KhachHang> search(String keyWord){
        String SQL = "SELECT * FROM KhachHang";
        
        if(keyWord.trim().length()!=0){
            SQL += " WHERE TenKH LIKE ? OR SDT LIKE ? OR Email LIKE ?";
        }
        
        ArrayList<KhachHang> ds = new ArrayList<>();
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
                int maKH = rs.getInt("MaKH");
                String tenKH = rs.getString("TenKH");
                String sdt = rs.getString("SDT");
                String email = rs.getString("Email");
                String diaChi = rs.getString("DiaChi");
                ds.add(new KhachHang(maKH, tenKH, sdt, email, diaChi));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
    public void creat(KhachHang kh){
        String SQL = "INSERT INTO KhachHang(TenKH,SDT,Email,DiaChi) VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSdt());
            ps.setString(3, kh.getEmail());
            ps.setString(4, kh.getDiaChi());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
     public void update(KhachHang kh){
        String SQL = "UPDATE KhachHang SET TenKH = ?, SDT = ?, Email = ?, DiaChi = ? WHERE MaKH = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, kh.getTenKH());
            ps.setString(2, kh.getSdt());
            ps.setString(3, kh.getEmail());
            ps.setString(4, kh.getDiaChi());
            ps.setInt(5, kh.getMaKH());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
      public void delete(int ma){
        String SQL = "DELETE FROM KhachHang WHERE MaKH = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setInt(1, ma);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      
      public KhachHang getKhachHangByMaKH(int maKH) {
        String SQL = "SELECT TenKH, SDT FROM KhachHang WHERE MaKH = ?";
        KhachHang kh = null;

        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setInt(1, maKH);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String tenKH = rs.getString("TenKH");
                String sdt = rs.getString("SDT");
                kh = new KhachHang(maKH,tenKH, sdt,null,null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kh;
    }
      
      public KhachHang getCustomerByName(String customerName) {
    String SQL = "SELECT MaKH, TenKH, SDT FROM KhachHang WHERE TenKH = ?";
    KhachHang kh = null;

    try {
        PreparedStatement ps = this.conn.prepareStatement(SQL);
        ps.setString(1, customerName);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int maKH = rs.getInt("MaKH");
            String tenKH = rs.getString("TenKH");
            String sdt = rs.getString("SDT");
            kh = new KhachHang(maKH, tenKH, sdt, null, null); // Thêm thông tin địa chỉ và email là null
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return kh;
}
    
}
