package pemesanan;

import java.util.ArrayList;

public class Transaksi {
    private String namaPelanggan;
    private Bus busDipilih;
    private ArrayList<Integer> daftarKursi;
    private boolean paketMakan;
    private double totalHarga;
    private double pajak;
    private String idTransaksi;
    private String tanggalKeberangkatan;

    public Transaksi(String nama, Bus bus, ArrayList<Integer> kursiList, boolean makan, String id, String tanggalKeberangkatan) {
        this.namaPelanggan = nama;
        this.busDipilih = bus;
        this.daftarKursi = new ArrayList<>(kursiList);
        this.paketMakan = makan;
        this.idTransaksi = id;
        this.tanggalKeberangkatan = tanggalKeberangkatan;
        hitungTotal();
    }

    private void hitungTotal() {
        double hargaMakanan = paketMakan ? 35000.0 : 0.0;
        double subTotal = busDipilih.getHarga() + hargaMakanan;
        this.pajak = subTotal * 0.11;
        this.totalHarga = subTotal + pajak;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public String getTanggalKeberangkatan() {
        return tanggalKeberangkatan;
    }

    public Bus getBusDipilih() {
        return busDipilih;
    }

    public int getNomorKursi() {
        return daftarKursi.isEmpty() ? 0 : daftarKursi.get(0);
    }

    public boolean isPaketMakan() {
        return paketMakan;
    }

    public double getPajak() {
        return pajak;
    }

    public double getTotal() {
        return totalHarga;
    }

    public String getRute() {
        String a = busDipilih.getAsalKeberangkatan().length() > 8 ? busDipilih.getAsalKeberangkatan().substring(0, 8) + "." : busDipilih.getAsalKeberangkatan();
        String b = busDipilih.getTujuan().length() > 8 ? busDipilih.getTujuan().substring(0, 8) + "." : busDipilih.getTujuan();
        return a + " to " + b;
    }
}