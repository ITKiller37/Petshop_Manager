/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;
import Models.ThongKeKH;
import Utils.Dbconnection;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Dang
 */
public class RP_ThongKeKH {
    private Connection conn;
    
    public RP_ThongKeKH(){
        this.conn = Dbconnection.getConnection();
    }
    
    public ArrayList<ThongKeKH> search(){
        String SQL = "{call sp_KhachHang()}";
        
        ArrayList<ThongKeKH> ds = new ArrayList<>();
        
        try {
            CallableStatement cs = this.conn.prepareCall(SQL);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while (rs.next()) {                
                String tenKH = rs.getString("TenKH");
                String sdt = rs.getString("SDT");
                String diaChi = rs.getString("DiaChi");
                int tongHD = rs.getInt("TongHD_DaMua");
                ds.add(new ThongKeKH(tenKH, sdt, diaChi, tongHD));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
