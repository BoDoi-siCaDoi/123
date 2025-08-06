/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author ASUS
 */
public class ChiTietDonHangDTO {

    private int id_san_pham;
    private String ten_sp;
    private int soLuong;
    private int gia_sp;
    private String id_danh_muc; // Đồ ăn hoặc Đồ uống

    public ChiTietDonHangDTO() {
    }

    public ChiTietDonHangDTO(int id_san_pham, String ten_sp, int soLuong, int gia_sp, String id_danh_muc) {
        this.id_san_pham = id_san_pham;
        this.ten_sp = ten_sp;
        this.soLuong = soLuong;
        this.gia_sp = gia_sp;
        this.id_danh_muc = id_danh_muc;
    }

    public int getId_san_pham() {
        return id_san_pham;
    }

    public void setId_san_pham(int id_san_pham) {
        this.id_san_pham = id_san_pham;
    }

    public String getTen_sp() {
        return ten_sp;
    }

    public void setTen_sp(String ten_sp) {
        this.ten_sp = ten_sp;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGia_sp() {
        return gia_sp;
    }

    public void setGia_sp(int gia_sp) {
        this.gia_sp = gia_sp;
    }

    public String getId_danh_muc() {
        return id_danh_muc;
    }

    public void setId_danh_muc(String id_danh_muc) {
        this.id_danh_muc = id_danh_muc;
    }

}
