/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;
import Models.DichVu;
import Utils.Dbconnection;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Dang
 */
public class RP_DichVu {
    private Connection conn;
    
    public RP_DichVu(){
        this.conn = Dbconnection.getConnection();
    }
    
    public ArrayList<DichVu> search(String keyWord){
        String SQL = "SELECT MaDV,TenDV,LoaiDichVu.MaLDV,Gia FROM DichVu INNER JOIN LoaiDichVu ON LoaiDichVu.MaLDV = DichVu.MaLDV";
        
        if(keyWord.trim().length()!=0){
            SQL += " WHERE MaDV LIKE ? OR TenDV LIKE ?";
        }
        
        ArrayList<DichVu> ds = new ArrayList<>();
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            if(keyWord.trim().length() != 0){
                ps.setString(1,"%" + keyWord + "%");
                ps.setString(2, "%" + keyWord + "%");
            }
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {                
                String maDV = rs.getString("MaDV");
                String tenDV = rs.getString("TenDV");
                String maLDV = rs.getString("MaLDV");
                float gia = rs.getFloat("Gia");
                ds.add(new DichVu(maDV, tenDV, maLDV, gia));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
       public void creat(DichVu dv){
        String SQL = "INSERT INTO DichVu(MaDV,TenDV,MaLDV,Gia) VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, dv.getMaDV());
            ps.setString(2, dv.getTenDV());
            ps.setString(3, dv.getMaLDV());
            ps.setFloat(4, dv.getGia());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
       
         public void update(DichVu dv){
        String SQL = "UPDATE DichVu SET TenDV = ?, Gia = ? WHERE MaDV = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, dv.getTenDV());
            ps.setFloat(2, dv.getGia());
            ps.setString(3, dv.getMaDV());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
         
         public void delete(String ma){
        String SQL = "DELETE FROM DichVu WHERE MaDV = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, ma);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
         
     public float getGia(String maDichVu) {
        String SQL = "SELECT Gia FROM DichVu WHERE MaDV = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(SQL);
            ps.setString(1, maDichVu);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat("Gia");
            } else {
                System.out.println("Không tìm thấy dịch vụ: " + maDichVu);
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
     
     public String getTenDichVu(String maDV) {
    String SQL = "SELECT TenDV FROM DichVu WHERE MaDV = ?";
    String tenDV = null;

    try {
        PreparedStatement ps = this.conn.prepareStatement(SQL);
        ps.setString(1, maDV);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            tenDV = rs.getString("TenDV");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return tenDV; 
}
}
