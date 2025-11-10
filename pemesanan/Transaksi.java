package pemesanan;

public class Transaksi {
    // Inisialisasi namaPelanggan, busDipilih, nomorKursi, paketMakan, totalHarga,
    // pajak, idTransaksi
    // Kurang lebih sama seperti pada Bus.java
    private String namaPelanggan;
    private Bus busDipilih;
    private int nomorKursi;
    private boolean paketMakan;
    private double totalHarga;
    private double pajak;
    private String idTransaksi;

    public Transaksi(String nama, Bus bus, int kursi, boolean makan, String id) {
        this.namaPelanggan = nama;
        this.busDipilih = bus;
        this.nomorKursi = kursi;
        this.paketMakan = makan;
        this.idTransaksi = id;
        hitungTotal();
    }

    // Function untuk menghitung total transaksi dan harga sudah termasuk pajak dan
    // juga user mau menambah paket makan
    private void hitungTotal() {
        double hargaMakanan = paketMakan ? 35000 : 0;
        double subTotal = busDipilih.getHarga() + hargaMakanan;
        this.pajak = subTotal * 0.11;
        this.totalHarga = subTotal + pajak;
    }

    // Getter
    public String getIdTransaksi() {
        return idTransaksi;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public Bus getBusDipilih() {
        return busDipilih;
    }

    public int getNomorKursi() {
        return nomorKursi;
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

    // Membuat rute
    public String getRute() {
        String a = busDipilih.getAsalKeberangkatan().length() > 8 ? busDipilih.getAsalKeberangkatan().substring(0, 8) + "." : busDipilih.getAsalKeberangkatan();
        String b = busDipilih.getTujuan().length() > 8 ? busDipilih.getTujuan().substring(0, 8) + "." : busDipilih.getTujuan();
        return a + " to " + b;
    }
}