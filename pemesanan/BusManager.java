package pemesanan;

import java.util.ArrayList;
import java.util.Scanner;

public class BusManager {
    // Inisialisasi daftarBus menggunakan ArrayList, daftarTransksi menggunakan
    // ArrayList, Scanner, dan idCounter
    // Sama seperti yang di Bus.java, cuman nggak dimasukin di dalam '()'
    private ArrayList<Bus> daftarBus;
    private ArrayList<Transaksi> dafTransaksi;
    private Scanner scanner;
    private int idCounter;

    public BusManager() {
        this.daftarBus = new ArrayList<>();
        this.dafTransaksi = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.idCounter = 1001;
        inisialisasiDataBus();
    }

    // Inisialisasi data bus seperti nama bus, jenis bus, asal keberangkatan, Tujuan
    // keberangkatan, harga, tanggal keberangkatan, dan kapasitas penumpang kemudian ditambahkan ke dalam ArrayList
    private void inisialisasiDataBus() {

    }

    // Membuat menu utama untuk CLI
    public void mainMenu() {

    }

    // Melihat daftar bus
    public void showBus() {

    }

    // Memesan Tiket
    public void pesanTiket() {

    }

    // Show nota
    public void showNota(Transaksi t) {

    }

    // Melihat daftar transaksi
    public void showDaftarTrx() {
        
    }

    // Scanner 
    public Scanner getScanner() {
        return scanner;
    }

    public boolean isExit(int pilihan) {
        return pilihan == 4;
    }
}
