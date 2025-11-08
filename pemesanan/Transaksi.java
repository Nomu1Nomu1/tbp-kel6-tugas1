package pemesanan;

public class Transaksi {
    // Inisialisasi namaPelanggan, busDipilih, nomorKursi, paketMakan, totalHarga, pajak, idTransaksi
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

    // Function untuk menghitung total transaksi dan harga sudah termasuk pajak dan juga user mau menambah paket makan
    private void hitungTotal() {

    }

    // Getter
    public String getIdTransaksi() { return idTransaksi; }
    

    // Membuat rute
    public String getRute() {
        
    }
}