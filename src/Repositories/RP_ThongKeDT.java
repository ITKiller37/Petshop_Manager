/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;
import Models.ThongKeDT;
import Utils.Dbconnection;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Dang
 */
public class RP_ThongKeDT {
     private Connection conn;
    
    public RP_ThongKeDT(){
        this.conn = Dbconnection.getConnection();
    }
    
    public ArrayList<ThongKeDT> search(){
        String SQL = "{call sp_DoanhThu()}";
        
        ArrayList<ThongKeDT> ds = new ArrayList<>();
        
        try {
            CallableStatement cs = this.conn.prepareCall(SQL);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while (rs.next()) {                
                String ngayTao = rs.getString("Ngay");
                int tongHD = rs.getInt("TongSo_HD");
                int tongDT = rs.getInt("TongThuNhap");
                int caoNhat = rs.getInt("CaoNhat");
                float trungBinh = rs.getFloat("TrungBinh");
                int thapNhat = rs.getInt("ThapNhat");
                ds.add(new ThongKeDT(ngayTao, tongHD, tongDT, caoNhat, trungBinh, thapNhat));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
