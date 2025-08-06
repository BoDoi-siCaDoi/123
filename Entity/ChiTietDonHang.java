/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author ASUS
 */
public class ChiTietDonHang {

    int id_don_hang, id_san_pham, so_luong, gia;
    String ghi_chu;

    public ChiTietDonHang() {
    }

    public ChiTietDonHang(int id_don_hang, int id_san_pham, int so_luong, int gia, String ghi_chu) {
        this.id_don_hang = id_don_hang;
        this.id_san_pham = id_san_pham;
        this.so_luong = so_luong;
        this.gia = gia;
        this.ghi_chu = ghi_chu;
    }

    public int getId_don_hang() {
        return id_don_hang;
    }

    public void setId_don_hang(int id_don_hang) {
        this.id_don_hang = id_don_hang;
    }

    public int getId_san_pham() {
        return id_san_pham;
    }

    public void setId_san_pham(int id_san_pham) {
        this.id_san_pham = id_san_pham;
    }

    public int getSo_luong() {
        return so_luong;
    }

    public void setSo_luong(int so_luong) {
        this.so_luong = so_luong;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getGhi_chu() {
        return ghi_chu;
    }

    public void setGhi_chu(String ghi_chu) {
        this.ghi_chu = ghi_chu;
    }

}
