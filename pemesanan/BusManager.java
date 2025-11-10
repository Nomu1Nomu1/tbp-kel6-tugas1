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
    // keberangkatan, harga, tanggal keberangkatan, dan kapasitas penumpang kemudian
    // ditambahkan ke dalam ArrayList
    private void inisialisasiDataBus() {
        daftarBus.add(new Bus("Sumber Rahayu", "Ekonomi", "Surabaya", "Yogyakarta", 100000, "2025-12-24", 40));
        daftarBus.add(new Bus("Gunung Harta", "Sleeper", "Jakarta", "Bali", 450000, "2025-12-25", 16));
        daftarBus.add(new Bus("Rosalia Indah", "Eksekutif", "Bandung", "Semarang", 180000, "2025-12-01", 30));
        daftarBus.add(new Bus("Prima Jaya", "Ekonomi", "Jakarta", "Yogyakarta", 100000, "2026-01-01", 40));
        daftarBus.add(new Bus("Sinar Jaya", "Eksekutif", "Jakarta", "Bandung", 120000, "2026-01-07", 34));
        daftarBus.add(new Bus("Rosalia Indah", "Eksekutif", "Semarang", "Surabaya", 200000, "2026-01-08", 24));
    }

    // Membuat menu utama untuk CLI
    public void mainMenu() {

    }

    // Melihat daftar bus
    public void showBus() {
        System.out.println("\n" + " - ".repeat(100));
        System.out.println("Daftar Bus Tersedia");
        System.out.println(" - ".repeat(100));
        System.out.printf("%-3s %-15s %-12s %-10s %-12s %-12s %-10s %-10s%n", "No", "Nama Bus", "Jenis", "Asal",
                "Tujuan", "Harga", "Tanggal", "Sisa Kursi");
        System.out.println(" - ".repeat(100));
        
        for(int i = 0; i < daftarBus.size(); i++) {
            System.out.printf("%-3d %s%n", (i+1), daftarBus.get(i));
        }                
        System.out.println(" - ".repeat(100));
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
