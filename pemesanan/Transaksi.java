package pemesanan;

import java.util.ArrayList;

public class Transaksi {
    private ArrayList<String> daftarNamaPenumpang;
    private Bus busDipilih;
    private ArrayList<Integer> daftarKursi;
    private boolean paketMakan;
    private double total;
    private double pajak;
    private String idTransaksi;
    private String tanggalKeberangkatan;

    public Transaksi(ArrayList<String> namaList, Bus bus, ArrayList<Integer> kursiList, boolean makan, String id, String tanggalKeberangkatan) {
        this.daftarNamaPenumpang = new ArrayList<>(namaList);
        this.busDipilih = bus;
        this.daftarKursi = new ArrayList<>(kursiList);
        this.paketMakan = makan;
        this.idTransaksi = id;
        this.tanggalKeberangkatan = tanggalKeberangkatan;
        hitungTotal();
    }

    public Transaksi(String nama, Bus bus, int kursi, boolean makan, String id, String tanggalKeberangkatan) {
        this.daftarNamaPenumpang = new ArrayList<>();
        this.daftarNamaPenumpang.add(nama);
        this.busDipilih = bus;
        this.daftarKursi = new ArrayList<>();
        this.daftarKursi.add(kursi);
        this.paketMakan = makan;
        this.idTransaksi = id;
        this.tanggalKeberangkatan = tanggalKeberangkatan;
        hitungTotal();
    }

    private void hitungTotal() {
        int jumlahKursi = daftarKursi.size();
        double hargaMakanan = paketMakan ? 35000.0 * jumlahKursi : 0.0;
        double subTotal = (busDipilih.getHarga() * jumlahKursi) + hargaMakanan;
        this.pajak = subTotal * 0.11;
        this.total = subTotal + pajak;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getNamaPelanggan() {
        return daftarNamaPenumpang.isEmpty() ? "" : daftarNamaPenumpang.get(0);
    }

    public ArrayList<String> getDaftarNamaPenumpang() {
        return daftarNamaPenumpang;
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

    public ArrayList<Integer> getDaftarKursi() {
        return daftarKursi;
    }

    public int getJumlahKursi() {
        return daftarKursi.size();
    }

    public boolean isPaketMakan() {
        return paketMakan;
    }

    public double getPajak() {
        return pajak;
    }

    public double getTotal() {
        return total;
    }

    public String getRute() {
        String a = busDipilih.getAsalKeberangkatan().length() > 8 ? busDipilih.getAsalKeberangkatan().substring(0, 8) + "." : busDipilih.getAsalKeberangkatan();
        String b = busDipilih.getTujuan().length() > 8 ? busDipilih.getTujuan().substring(0, 8) + "." : busDipilih.getTujuan();
        return a + " to " + b;
    }
}