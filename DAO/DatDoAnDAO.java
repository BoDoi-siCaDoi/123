/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Entity.DonHangEntity;
import Entity.ChiTietDonHang;
import Entity.ListDoAn;
import Entity.ListDoUong;
import Utils.KetNoiDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class DatDoAnDAO {
    // Lấy giá sản phẩm theo tên
    public int getPrice(String tenSP) {
        String sql = "SELECT gia_sp FROM San_pham WHERE ten_sp = ?";
        try (Connection conn = KetNoiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenSP);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("gia_sp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy danh sách đồ ăn
    public List<ListDoAn> getListDoAn() {
        return getSanPhamTheoDanhMuc(2);
    }

    // Lấy danh sách đồ uống
    public List<ListDoUong> getListDoUong() {
        return getSanPhamTheoDanhMuc(1);
    }

    // Lấy sản phẩm theo danh mục (1: Đồ uống, 2: Đồ ăn)
    private List getSanPhamTheoDanhMuc(int idDanhMuc) {
        String sql = "SELECT id_san_pham, ten_sp, gia_sp FROM San_pham WHERE id_danh_muc = ?";
        List result = new ArrayList<>();
        try (Connection conn = KetNoiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDanhMuc);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (idDanhMuc == 1)
                    result.add(new ListDoUong(rs.getInt(1), rs.getString(2), rs.getInt(3)));
                else
                    result.add(new ListDoAn(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Tạo đơn hàng mới (truyền id_tk từ ngoài vào)
    public int taoDonHangMoi(int id_tk, int tongTien, String thoiGian) {
        String sql = "INSERT INTO Don_hang (id_tk, tong_tien, thoi_gian, trang_thai) VALUES (?, ?, ?, ?)";
        try (Connection conn = KetNoiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, id_tk);
            ps.setInt(2, tongTien);
            ps.setString(3, thoiGian);
            ps.setBoolean(4, false); // mặc định trạng thái = false (chưa xử lý)
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Lưu chi tiết đơn hàng
    public boolean luuChiTietDonHang(int idDonHang, List<ChiTietDonHang> chiTietLst) {
        String sql = "INSERT INTO Chi_tiet_don_hang (id_don_hang, id_san_pham, so_luong, gia, ghi_chu) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = KetNoiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (ChiTietDonHang ct : chiTietLst) {
                ps.setInt(1, idDonHang);
                ps.setInt(2, ct.getId_san_pham());
                ps.setInt(3, ct.getSo_luong());
                ps.setInt(4, ct.getGia());
                ps.setString(5, ct.getGhi_chu());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Đọc danh sách đơn hàng chưa xử lý
    public List<DonHangEntity> getDonHangChuaXuLy() {
        List<DonHangEntity> list = new ArrayList<>();
        String sql = "SELECT * FROM Don_hang WHERE trang_thai = 0";
        try (Connection conn = KetNoiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new DonHangEntity(
                        rs.getInt("id_don_hang"),
                        rs.getInt("id_tk"),
                        rs.getInt("tong_tien"),
                        rs.getString("thoi_gian"),
                        rs.getBoolean("trang_thai")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cập nhật trạng thái đơn hàng
    public boolean capNhatTrangThai(int idDonHang) {
        String sql = "UPDATE Don_hang SET trang_thai = 1 WHERE id_don_hang = ?";
        try (Connection conn = KetNoiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDonHang);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
