/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;
import Models.ThongKeSP;
import Utils.Dbconnection;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Dang
 */
public class RP_ThongKe {
    private Connection conn;
    
    public RP_ThongKe(){
        this.conn = Dbconnection.getConnection();
    }
    
    public ArrayList<ThongKeSP> search(){
        String SQL = "{call sp_KhoHang()}";
        
        ArrayList<ThongKeSP> ds = new ArrayList<>();
        
        try {
            CallableStatement cs = this.conn.prepareCall(SQL);
            cs.execute();
            ResultSet rs = cs.getResultSet();
            while (rs.next()) {                
                String tenSP = rs.getString("TenSP");
                int tongSL = rs.getInt("TongSL");
                int slConLai = rs.getInt("SL_ConLai");
                int slDaBan = rs.getInt("SL_DaBan");
                ds.add(new ThongKeSP(tenSP, tongSL, slConLai, slDaBan));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ds;
    }
}
