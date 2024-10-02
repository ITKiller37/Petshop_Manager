/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repositories;
import Utils.Dbconnection;
import java.sql.*;
/**
 *
 * @author Dang
 */
public class RP_DangNhap {
    private Connection conn;
    
    public RP_DangNhap(){
        this.conn = Dbconnection.getConnection();
    }
    
    public String getRole(String taiKhoan, String matKhau){
        String SQL = "SELECT VaiTro FROM DangNhap WHERE TaiKhoan = ? AND MatKhau = ?";
        
        try {
            PreparedStatement ps = this.conn.prepareStatement(SQL);
            ps.setString(1, taiKhoan);
            ps.setString(2, matKhau);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            String vaiTro = null;
            while(rs.next()){
                 vaiTro=rs.getString("vaiTro");
            }
            return vaiTro;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
