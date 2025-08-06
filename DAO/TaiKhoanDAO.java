/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entity.TaiKhoanEntity;
import Utils.KetNoiDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class TaiKhoanDAO {
   // ✅ Thêm tài khoản mới
    public void addAccount(TaiKhoanEntity tk) {
    // Kiểm tra trùng SĐT trước khi thêm
    String checkSql = "SELECT COUNT(*) FROM Tai_Khoan WHERE sdt = ?";
    try (Connection conn = KetNoiDB.getConnection();
         PreparedStatement psCheck = conn.prepareStatement(checkSql)) {
        psCheck.setString(1, tk.getSdt());
        ResultSet rs = psCheck.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            throw new SQLException("SĐT đã tồn tại!"); // báo lỗi nếu trùng
        }
    } catch (SQLException e) {
        throw new RuntimeException("Lỗi kiểm tra trùng SĐT: " + e.getMessage());
    }

    // Nếu không trùng thì thêm mới
    String sql = "INSERT INTO Tai_Khoan (vai_tro, ten_dang_nhap, mat_khau, so_du, sdt, email, ho_ten, trang_thai_tk) "
               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    try (Connection conn = KetNoiDB.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, tk.getVai_tro());
        ps.setString(2, tk.getTen_dang_nhap());
        ps.setString(3, tk.getMat_khau());
        ps.setInt(4, tk.getSo_du());
        ps.setString(5, tk.getSdt());
        ps.setString(6, tk.getEmail());
        ps.setString(7, tk.getHo_ten());
        ps.setString(8, tk.getTrang_thai_tk());
        ps.executeUpdate();
    } catch (SQLException e) {
        System.err.println("Lỗi thêm tài khoản: " + e.getMessage());
    }
}


    // ✅ Lấy toàn bộ danh sách tài khoản (theo ID tăng dần)
    public List<TaiKhoanEntity> getAllAccounts() {
        List<TaiKhoanEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM Tai_khoan ORDER BY id_tk ASC";
        try (Connection conn = KetNoiDB.getConnection(); 
             Statement stm = conn.createStatement(); 
             ResultSet rs = stm.executeQuery(sql)) {
            while (rs.next()) {
                TaiKhoanEntity tk = new TaiKhoanEntity(
                        rs.getString("vai_tro"),
                        rs.getString("ten_dang_nhap"),
                        rs.getString("mat_khau"),
                        rs.getInt("so_du"),
                        rs.getString("sdt"),
                        rs.getString("email"),
                        rs.getString("ho_ten"),
                        rs.getString("trang_thai_tk")
                );
                list.add(tk);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy danh sách tài khoản: " + e.getMessage());
        }
        return list;
    }

    // ✅ Lấy ID tài khoản qua số điện thoại
    public int getIdBySdt(String sdt) {
        String sql = "SELECT id_tk FROM Tai_khoan WHERE sdt = ?";
        try (Connection conn = KetNoiDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sdt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_tk");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy ID theo SĐT: " + e.getMessage());
        }
        return 0;
    }

    // ✅ Lấy ID tài khoản qua tên đăng nhập
    public int getIdByUsername(String username) {
        String sql = "SELECT id_tk FROM Tai_khoan WHERE ten_dang_nhap = ?";
        try (Connection conn = KetNoiDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_tk");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy ID theo username: " + e.getMessage());
        }
        return 0;
    }

    // ✅ Lấy số dư tài khoản qua username
    public int getSoDuByUsername(String username) {
        String sql = "SELECT so_du FROM Tai_khoan WHERE ten_dang_nhap = ?";
        try (Connection conn = KetNoiDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("so_du");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy số dư: " + e.getMessage());
        }
        return 0;
    }

    // ✅ Cập nhật thông tin tài khoản theo SĐT
    public void updateAccount(TaiKhoanEntity tk) {
        String sql = "UPDATE Tai_khoan SET vai_tro=?, ten_dang_nhap=?, mat_khau=?, so_du=?, email=?, ho_ten=?, trang_thai_tk=? "
                   + "WHERE sdt=?";
        try (Connection conn = KetNoiDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tk.getVai_tro());
            ps.setString(2, tk.getTen_dang_nhap());
            ps.setString(3, tk.getMat_khau());
            ps.setInt(4, tk.getSo_du());
            ps.setString(5, tk.getEmail());
            ps.setString(6, tk.getHo_ten());
            ps.setString(7, tk.getTrang_thai_tk());
            ps.setString(8, tk.getSdt());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật tài khoản: " + e.getMessage());
        }
    }

    // ✅ Xóa tài khoản theo ID
    public void deleteAccount(int id) {
        String sql = "DELETE FROM Tai_khoan WHERE id_tk=?";
        try (Connection conn = KetNoiDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi xóa tài khoản: " + e.getMessage());
        }
    }

    // ✅ Cập nhật số dư
    public void updateSoDu(String username, int soDu) {
        String sql = "UPDATE Tai_khoan SET so_du=? WHERE ten_dang_nhap=?";
        try (Connection conn = KetNoiDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soDu);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật số dư: " + e.getMessage());
        }
    }

    // ✅ Lấy vai trò
    public String getRole(int id) {
        String sql = "SELECT vai_tro FROM Tai_khoan WHERE id_tk=?";
        try (Connection conn = KetNoiDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("vai_tro");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy vai trò: " + e.getMessage());
        }
        return null;
    }

    // ✅ Cập nhật trạng thái tài khoản
    public void updateStatus(String username, String status) {
        String sql = "UPDATE Tai_khoan SET trang_thai_tk=? WHERE ten_dang_nhap=?";
        try (Connection conn = KetNoiDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật trạng thái: " + e.getMessage());
        }
    }

    // ✅ Lấy trạng thái tài khoản
    public String getStatus(String username) {
        String sql = "SELECT trang_thai_tk FROM Tai_khoan WHERE ten_dang_nhap=?";
        try (Connection conn = KetNoiDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("trang_thai_tk");
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy trạng thái tài khoản: " + e.getMessage());
        }
        return null;
    }
}
