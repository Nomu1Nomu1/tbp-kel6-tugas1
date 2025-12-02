package pemesanan;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class BusManager {
    private ArrayList<Bus> daftarBus;
    private ArrayList<Transaksi> daftarTransaksi;
    private Scanner scanner;
    private int idCounter;

    public BusManager() {
        this.daftarBus = new ArrayList<>();
        this.daftarTransaksi = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        this.idCounter = 1001;
        inisialisasiDataBus();
    }

    private void inisialisasiDataBus() {
        daftarBus.add(new Bus("Sumber Rahayu", "Ekonomi", "Surabaya", "Yogyakarta", 100000, "2025-12-24", 40));
        daftarBus.add(new Bus("Gunung Harta", "Sleeper", "Jakarta", "Bali", 450000, "2025-12-25", 16));
        daftarBus.add(new Bus("Rosalia Indah", "Eksekutif", "Bandung", "Semarang", 180000, "2025-12-01", 30));
        daftarBus.add(new Bus("Prima Jaya", "Ekonomi", "Jakarta", "Yogyakarta", 100000, "2026-01-01", 40));
        daftarBus.add(new Bus("Sinar Jaya", "Eksekutif", "Jakarta", "Bandung", 120000, "2026-01-07", 34));
        daftarBus.add(new Bus("Rosalia Indah", "Eksekutif", "Semarang", "Surabaya", 200000, "2026-01-08", 24));
    }

    public void mainMenu() {
        System.out.println("SiBuder OO");
        System.out.println("1. Lihat Daftar Bus Tersedia");
        System.out.println("2. Pesan Tiket");
        System.out.println("3. Lihat Daftar Transaksi");
        System.out.println("4. Keluar");
        System.out.print("Pilih menu (1-4): ");
    }

    public void showBus() {
        System.out.println("Daftar Bus Tersedia");
        System.out.println(" - ".repeat(50));
        System.out.printf("%-3s %-15s %-12s %-10s %-12s %-12s %-10s %-10s%n", "No", "Nama Bus", "Jenis", "Asal",
                "Tujuan", "Harga", "Tanggal", "Sisa Kursi");
        System.out.println(" - ".repeat(50));

        for (int i = 0; i < daftarBus.size(); i++) {
            System.out.printf("%-3d %s%n", (i + 1), daftarBus.get(i));
        }
        System.out.println(" - ".repeat(50));
    }

    public void pesanTiket() {
        System.out.println("\n--- PROSES PEMESANAN TIKET ---");
        System.out.print("Masukkan nama pelanggan: ");
        String nama = scanner.nextLine();

        showBus();
        System.out.print("Pilih nomor bus (1-" + daftarBus.size() + "): ");
        int pilihan = scanner.nextInt() - 1;
        scanner.nextLine();

        if (pilihan < 0 || pilihan >= daftarBus.size()) {
            System.out.println("Pilihan bus tidak valid!");
            return;
        }

        Bus bus = daftarBus.get(pilihan);
        System.out.println("Masdukkan tanggal keberangkatan (YYYY-MM-DD): ");
        String tanggalKeberangkatan = scanner.nextLine();
        LocalDate today = LocalDate.now();
        LocalDate tanggalBus = LocalDate.parse(bus.getTanggalKeberangkatan());
        if (!tanggalBus.isAfter(today)) {
            System.out.println("Maaf, tiket untuk bus ini sudah melewati tanggal keberangkatan!");
            return;
        }
        if (bus.getKursiTersedia() <= 0) {
            System.out.println("Maaf, bus " + bus.getNamaBus() + " sudah penuh!");
            return;
        }
        //h

        System.out.println("\nDetail Bus Dipilih:");
        System.out.println("Nama Bus       : " + bus.getNamaBus());
        System.out.println("Jenis          : " + bus.getJenisBus());
        System.out.println("Rute           : " + bus.getAsalKeberangkatan() + " to " + bus.getTujuan());
        System.out.println("Harga Tiket    : Rp" + String.format("%,.0f", bus.getHarga()));
        System.out.println("Tanggal        : " + bus.getTanggalKeberangkatan());
        System.out.println("Kapasitas      : " + bus.getKursiTersedia() + " kursi tersedia dari " + bus.getKapasitas());

        int kursi = -1;
        while (true) {
            System.out.print("\nNomor kursi (1-" + bus.getKapasitas() + "): ");
            try {
                kursi = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka! Silakan coba lagi.");
                continue;
            }

            if (!bus.isKursiAvailable(kursi)) {
                System.out.println("Kursi tidak tersedia atau nomor tidak valid! Pilih lagi.");
                continue; 
            }
            break; 
        }

        System.out.print("Tambah paket makan? (y/n): ");
        String makanInput = scanner.nextLine().toLowerCase();
        boolean paketMakan = makanInput.equals("y") || makanInput.equals("ya");

        double hargaMakan = paketMakan ? 35000.0 : 0.0;
        double subtotal = bus.getHarga() + hargaMakan;
        double pajak = subtotal * 0.11;
        double total = subtotal + pajak;

        System.out.println("\n--- KONFIRMASI PEMESANAN ---");
        System.out.println("Nama           : " + nama);
        System.out.println("Bus            : " + bus.getNamaBus() + " (" + bus.getJenisBus() + ")");
        System.out.println("Rute           : " + bus.getAsalKeberangkatan() + " to " + bus.getTujuan());
        System.out.println("Kursi          : " + kursi);
        System.out.println("Paket Makan    : " + (paketMakan ? "Ya (Rp35.000)" : "Tidak"));
        System.out.printf("Harga Tiket    : Rp%,.0f%n", bus.getHarga());
        System.out.printf("Paket Makan    : Rp%,.0f%n", hargaMakan);
        System.out.printf("Pajak (11%%)    : Rp%,.0f%n", pajak);
        System.out.println("=".repeat(40));
        System.out.printf("TOTAL BAYAR    : Rp%,.0f%n", total);

        System.out.print("\nKonfirmasi? (y/n): ");
        String konfirm = scanner.nextLine().toLowerCase();

        if (konfirm.equals("y") || konfirm.equals("ya")) {
            bus.pesanKursi(kursi);
            String id = "TKT" + idCounter++;
            Transaksi t = new Transaksi(nama, bus, kursi, paketMakan, id, bus.getTanggalKeberangkatan());
            daftarTransaksi.add(t);
            showNota(t);
            System.out.println("\nPemesanan berhasil!");
        } else {
            System.out.println("Pemesanan dibatalkan.");
        }
    }

    public void showNota(Transaksi t) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("            NOTA PEMESANAN TIKET BUS");
        System.out.println("=".repeat(50));
        System.out.println("ID Transaksi   : " + t.getIdTransaksi());
        System.out.println("Nama           : " + t.getNamaPelanggan());
        System.out.println("Bus            : " + t.getBusDipilih().getNamaBus());
        System.out.println("Jenis          : " + t.getBusDipilih().getJenisBus());
        System.out.println("Rute           : " + t.getBusDipilih().getAsalKeberangkatan() + " to "
                + t.getBusDipilih().getTujuan());
        System.out.println("Tanggal        : " + t.getBusDipilih().getTanggalKeberangkatan());
        System.out.println("Kursi          : " + t.getNomorKursi());
        System.out.println("Paket Makan    : " + (t.isPaketMakan() ? "Ya" : "Tidak"));
        System.out.println("-".repeat(50));
        System.out.printf("Harga Dasar    : Rp%,.0f%n", t.getBusDipilih().getHarga());
        System.out.printf("Paket Makan    : Rp%,.0f%n", t.isPaketMakan() ? 35000.0 : 0.0);
        System.out.printf("Subtotal       : Rp%,.0f%n", t.getBusDipilih().getHarga() + (t.isPaketMakan() ? 35000.0 : 0.0));
        System.out.printf("Pajak (11%%)    : Rp%,.0f%n", t.getPajak());
        System.out.println("=".repeat(50));
        System.out.printf("TOTAL BAYAR    : Rp%,.0f%n", t.getTotal());
        System.out.println("=".repeat(50));
        System.out.println("Terima kasih atas pemesanannya!");
    }

    public void showDaftarTrx() {
        if (daftarTransaksi.isEmpty()) {
            System.out.println("\nBelum ada transaksi.");
            return;
        }

        System.out.println("\n" + "-".repeat(90));
        System.out.println("DAFTAR TRANSAKSI");
        System.out.println("-".repeat(90));
        System.out.printf("%-10s %-15s %-12s %-20s %-8s %-12s%n",
                "ID", "Nama", "Bus", "Rute", "Kursi", "Total");
        System.out.println("-".repeat(90));

        for (Transaksi t : daftarTransaksi) {
            System.out.printf("%-10s %-15s %-12s %-20s %-8d Rp%,-10.0f%n",
                    t.getIdTransaksi(),
                    t.getNamaPelanggan().length() > 13 ? t.getNamaPelanggan().substring(0, 13) + ".."
                            : t.getNamaPelanggan(),
                    t.getBusDipilih().getNamaBus().length() > 10
                            ? t.getBusDipilih().getNamaBus().substring(0, 10) + ".."
                            : t.getBusDipilih().getNamaBus(),
                    t.getRute(), t.getNomorKursi(), t.getTotal());
        }
        System.out.println("-".repeat(90));
        System.out.println("Total transaksi: " + daftarTransaksi.size());
    }

    public Scanner getScanner() {
        return scanner;
    }

    public boolean isExit(int pilihan) {
        return pilihan == 4;
    }
}
