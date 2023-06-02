package com.example.magang;

public class ItemRiwayat {
    private String noResi;
    private String namaBarang;
    private String tanggal;
    private String jam;

    public ItemRiwayat(String noResi, String namaBarang, String tanggal, String jam) {
        this.noResi = noResi;
        this.namaBarang = namaBarang;
        this.tanggal = tanggal;
        this.jam = jam;
    }

    public String getNoResi() {
        return noResi;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getJam() {
        return jam;
    }

    @Override
    public String toString() {
        return "No. Resi: " + noResi + ", Nama Barang: " + namaBarang + ", Tanggal: " + tanggal + ", Jam: " + jam;
    }
}