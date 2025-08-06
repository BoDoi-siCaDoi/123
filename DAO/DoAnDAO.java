/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Utils.KetNoiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import Entity.DoAn;
/**
 *
 * @author ASUS
 */
public class DoAnDAO {
         public static int createSanPham(DoAn doAn){
        String sql = "INSERT INTO San_pham VALUES (?, ?, ?)";
        try(Connection con = KetNoiDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);){
            ps.setString(1, doAn.getTen_sp());
            ps.setInt(2, doAn.getId_danh_muc());
            ps.setInt(3, doAn.getGia_sp());
            int ketQua = ps.executeUpdate();
            return ketQua;
        } catch (SQLException e){
            return 0;
    }}        
        public int xoaSanPham(String ten_sp){
        String sql = "DELETE FROM San_pham WHERE ten_sp = ?";
        try(Connection con = KetNoiDB.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);){
            ps.setString(1, ten_sp);
            int ketQua = ps.executeUpdate();
            return ketQua;
        } catch (SQLException e){
            return 0;
    }}
        public int SuaSanPham(DoAn doAn){
        try (Connection conn = KetNoiDB.getConnection()) {
            String sql = "UPDATE San_pham SET id_danh_muc = ?, gia_sp = ? WHERE ten_sp = ?";
            PreparedStatement ps = conn.prepareStatement(sql);            
            ps.setInt(1, doAn.getId_danh_muc());            
            ps.setInt(2, doAn.getGia_sp());
            ps.setString(3, doAn.getTen_sp());
            ps.executeUpdate();
            }
           
         catch (Exception e) {
            System.out.println("Error: " + e);
            e.printStackTrace();           
        }
        return 0;
    }
}
