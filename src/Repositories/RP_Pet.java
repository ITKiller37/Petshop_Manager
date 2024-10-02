/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;
import Models.Pet;
import Utils.Dbconnection;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Dang
 */
public class RP_Pet {
    private Connection conn;
    
    public RP_Pet(){
        this.conn = Dbconnection.getConnection();
    }
    
    public ArrayList<Pet> search(String keyWord){
        String SQL = "SELECT pt.MaPet,pt.TenPet,pt.LoaiPet,pt.GiongLoai,pt.Tuoi,"
                + "pt.CanNang,pt.TrangThaiTiemChung,kh.MaKH FROM Pet pt JOIN KhachHang kh ON kh.MaKH = pt.MaKH";
        
        if(keyWord.trim().length()!=0){
            SQL += " WHERE TenPET LIKE ? OR LoaiPet LIKE ? OR GiongLoai LIKE ?";
        }
        
        ArrayList<Pet> ds = new ArrayList<>();
        
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
                int maPet = rs.getInt("MaPet");
                String tenPet = rs.getString("TenPet");
                String loaiPet = rs.getString("LoaiPet");
                String giongLoai = rs.getString("GiongLoai");
                int tuoi = rs.getInt("Tuoi");
                float canNang = rs.getFloat("CanNang");
                int trangThaiTiemChung = rs.getInt("TrangThaiTiemChung");
                int maKH = rs.getInt("MaKH");
                ds.add(new Pet(maPet, tenPet, loaiPet, giongLoai, tuoi, canNang, trangThaiTiemChung, maKH));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
    
     public void creat(Pet pt){
        String SQL = "INSERT INTO Pet(TenPet,LoaiPet,GiongLoai,Tuoi,CanNang,TrangThaiTiemChung,MaKH) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, pt.getTenPet());
            ps.setString(2, pt.getLoaiPet());
            ps.setString(3, pt.getGiongLoai());
            ps.setInt(4, pt.getTuoi());
            ps.setFloat(5, pt.getCanNang());
            ps.setInt(6, pt.getTrangThaiTiemChung());
            ps.setInt(7, pt.getMaKH());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
      public void update(Pet pt){
        String SQL = "UPDATE Pet SET TenPet = ?, LoaiPet = ?, GiongLoai = ?, Tuoi = ?, CanNang = ?, TrangThaiTiemChung = ?  WHERE MaPet = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, pt.getTenPet());
            ps.setString(2, pt.getLoaiPet());
            ps.setString(3, pt.getGiongLoai());
            ps.setInt(4, pt.getTuoi());
            ps.setFloat(5, pt.getCanNang());
            ps.setInt(6, pt.getTrangThaiTiemChung());
            ps.setInt(7, pt.getMaPet());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      
       public void delete(int ma){
        String SQL = "DELETE FROM Pet WHERE MaPet = ?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setInt(1, ma);
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
