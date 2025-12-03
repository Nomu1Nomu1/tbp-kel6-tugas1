package pemesanan;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        daftarBus.add(new Bus("Prima Jaya", "Ekonomi", "Jakarta", "Surakarta", 100000, "2026-01-01", 40));
        daftarBus.add(new Bus("Sinar Jaya", "Eksekutif", "Jakarta", "Surabaya", 120000, "2026-01-07", 34));
        daftarBus.add(new Bus("Rosalia Indah", "Eksekutif", "Semarang", "Bandung", 200000, "2026-01-08", 24));
    }

    // MENU UTAMA 
    public void mainMenu() {
        System.out.println("SiBuder OO");
        System.out.println("1. Lihat Daftar Bus");
        System.out.println("2. Pesan Tiket");
        System.out.println("3. Cari Rute");
        System.out.println("4. Lihat Daftar Transaksi");
        System.out.println("5. Tambah Bus Baru");
        System.out.println("6. Keluar");
        System.out.print("Pilih menu (1-6): ");
    }


    //  TAMPIL BUS 
    public void showBus() {
        System.out.println("\nDaftar Bus Tersedia");
        System.out.println(" - ".repeat(50));
        System.out.printf("%-3s %-15s %-12s %-10s %-12s %-12s %-10s %-10s%n",
                "No", "Nama Bus", "Jenis", "Asal", "Tujuan", "Harga", "Tanggal", "Sisa Kursi");
        System.out.println(" - ".repeat(50));

        for (int i = 0; i < daftarBus.size(); i++) {
            System.out.printf("%-3d %s%n", (i + 1), daftarBus.get(i));
        }
        System.out.println(" - ".repeat(50));
    }

    // ESAN TIKET LANGSUNG 
    public void pesanTiketLangsung() {
        System.out.println("\n--- PESAN TIKET LANGSUNG ---");
        showBus();

        System.out.print("Pilih nomor bus (0 = batal): ");
        int pilih;
        try {
            pilih = Integer.parseInt(scanner.nextLine().trim());
            if (pilih == 0) {
                System.out.println("Dibatalkan.");
                return;
            }
            if (pilih < 1 || pilih > daftarBus.size()) throw new Exception();
        } catch (Exception e) {
            System.out.println("Pilihan tidak valid!");
            return;
        }

        Bus busDipilih = daftarBus.get(pilih - 1);
        bookingTiketBus(busDipilih);
    }


    //  CARI RUTE 
    public void cariRute() {
        System.out.println("\n--- CARI RUTE BUS ---");

        LocalDate tomorrow = LocalDate.now().plusDays(1);
        ArrayList<Bus> busAktif = new ArrayList<>();

        for (Bus b : daftarBus) {
            try {
                LocalDate tgl = LocalDate.parse(b.getTanggalKeberangkatan());
                if (!tgl.isBefore(tomorrow) && b.getKursiTersedia() > 0) {
                    busAktif.add(b);
                }
            } catch (Exception ignored) {}
        }

        if (busAktif.isEmpty()) {
            System.out.println("Maaf, tidak ada bus tersedia.");
            return;
        }

        // pilih asal
        String asal = pilihKota("Kota asal: ", dapatkanKotaAsal(busAktif));
        if (asal == null) return;

        // pilih tujuan
        String tujuan = pilihKota("Kota tujuan: ", dapatkanKotaTujuan(busAktif, asal));
        if (tujuan == null) return;

        // filter rute
        ArrayList<Bus> hasil = new ArrayList<>();
        for (Bus b : busAktif) {
            if (b.getAsalKeberangkatan().equalsIgnoreCase(asal) &&
                b.getTujuan().equalsIgnoreCase(tujuan)) {
                hasil.add(b);
            }
        }

        if (hasil.isEmpty()) {
            System.out.println("Tidak ada bus untuk rute tersebut.");
            return;
        }

        System.out.println("\nDitemukan " + hasil.size() + " bus:");
        System.out.println("-".repeat(80));
        System.out.printf("%-3s %-15s %-12s Rp%-10s %-12s %-8s%n",
                "No", "Nama Bus", "Jenis", "Harga", "Tanggal", "Kursi");
        System.out.println("-".repeat(80));

        for (int i = 0; i < hasil.size(); i++) {
            Bus b = hasil.get(i);
            System.out.printf("%-3d %-15s %-12s Rp%,-10.0f %-12s %d/%d%n",
                    (i+1), b.getNamaBus(), b.getJenisBus(), b.getHarga(),
                    b.getTanggalKeberangkatan(), b.getKursiTersedia(), b.getKapasitas());
        }
        System.out.println("-".repeat(80));

        // pilih bus
        System.out.print("Pilih nomor bus (0 = batal): ");
        int pilih;
        try {
            pilih = Integer.parseInt(scanner.nextLine().trim());
            if (pilih == 0) return;
            if (pilih < 1 || pilih > hasil.size()) throw new Exception();
        } catch (Exception e) {
            System.out.println("Pilihan tidak valid!");
            return;
        }

        bookingTiketBus(hasil.get(pilih - 1));
    }


    // BOOKING TIKET 
    private void bookingTiketBus(Bus busTerpilih) {
        System.out.println("\nBus terpilih: " + busTerpilih.getNamaBus());
        System.out.println("Melanjutkan ke pemilihan kursi...");

        int kursi = -1;
        while (true) {
            System.out.print("\nNomor kursi (1-" + busTerpilih.getKapasitas() + "): ");
            try {
                kursi = Integer.parseInt(scanner.nextLine().trim());
                if (kursi < 1 || kursi > busTerpilih.getKapasitas()) {
                    System.out.println("Nomor kursi tidak valid!");
                    continue;
                }
                if (!busTerpilih.isKursiAvailable(kursi)) {
                    System.out.println("Kursi sudah dipesan!");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Harus angka!");
            }
        }

        System.out.print("Tambah paket makan? (y/n): ");
        boolean paketMakan = scanner.nextLine().trim().toLowerCase().startsWith("y");

        System.out.print("Masukkan nama pelanggan: ");
        String nama = scanner.nextLine();

        double subtotal = busTerpilih.getHarga() + (paketMakan ? 35000 : 0);
        double pajak = subtotal * 0.11;
        double total = subtotal + pajak;

        System.out.println("\n" + "=".repeat(45));
        System.out.println("         KONFIRMASI PEMESANAN");
        System.out.println("Nama       : " + nama);
        System.out.println("Bus        : " + busTerpilih.getNamaBus());
        System.out.println("Rute       : " + busTerpilih.getAsalKeberangkatan() + " → " + busTerpilih.getTujuan());
        System.out.println("Tanggal    : " + busTerpilih.getTanggalKeberangkatan());
        System.out.println("Kursi      : " + kursi);
        System.out.println("Paket Makan: " + (paketMakan ? "Ya" : "Tidak"));
        System.out.printf("Total      : Rp%,.0f%n", total);
        System.out.println("=".repeat(45));
        System.out.print("Konfirmasi? (y/n): ");

        if (!scanner.nextLine().trim().toLowerCase().startsWith("y")) {
            System.out.println("Pemesanan dibatalkan.");
            return;
        }

        busTerpilih.pesanKursi(kursi);

        String id = "TKT" + idCounter++;
        Transaksi t = new Transaksi(nama, busTerpilih, kursi, paketMakan, id, busTerpilih.getTanggalKeberangkatan());
        daftarTransaksi.add(t);

        showNota(t);
        System.out.println("\nPemesanan berhasil!");
    }


    //  BANTUAN CARI RUTE 
    private String pilihKota(String prompt, ArrayList<String> daftar) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty() || input.equalsIgnoreCase("0")) return null;

            ArrayList<String> cocok = new ArrayList<>();
            for (String k : daftar) {
                if (k.toLowerCase().contains(input.toLowerCase())) cocok.add(k);
            }

            if (cocok.isEmpty()) {
                System.out.println("Tidak ditemukan.");
                continue;
            }
            if (cocok.size() == 1) {
                System.out.println("→ " + cocok.get(0));
                return cocok.get(0);
            }

            System.out.println("Pilih kota:");
            for (int i = 0; i < cocok.size(); i++)
                System.out.println((i+1) + ". " + cocok.get(i));

            System.out.print("Nomor: ");
            try {
                int p = Integer.parseInt(scanner.nextLine());
                if (p > 0 && p <= cocok.size()) return cocok.get(p-1);
            } catch (Exception ignored) {}
        }
    }

    private ArrayList<String> dapatkanKotaAsal(ArrayList<Bus> list) {
        ArrayList<String> kota = new ArrayList<>();
        for (Bus b : list)
            if (!kota.contains(b.getAsalKeberangkatan()))
                kota.add(b.getAsalKeberangkatan());
        return kota;
    }

    private ArrayList<String> dapatkanKotaTujuan(ArrayList<Bus> list, String asal) {
        ArrayList<String> kota = new ArrayList<>();
        for (Bus b : list) {
            if (b.getAsalKeberangkatan().equalsIgnoreCase(asal) &&
                !kota.contains(b.getTujuan()))
                kota.add(b.getTujuan());
        }
        return kota;
    }

    // NOTA 
    public void showNota(Transaksi t) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("            NOTA PEMESANAN TIKET BUS");
        System.out.println("=".repeat(50));
        System.out.println("ID Transaksi   : " + t.getIdTransaksi());
        System.out.println("Nama           : " + t.getNamaPelanggan());
        System.out.println("Bus            : " + t.getBusDipilih().getNamaBus());
        System.out.println("Jenis          : " + t.getBusDipilih().getJenisBus());
        System.out.println("Rute           : " + t.getBusDipilih().getAsalKeberangkatan() + " to " + t.getBusDipilih().getTujuan());
        System.out.println("Tanggal        : " + t.getTanggalKeberangkatan());
        System.out.println("Kursi          : " + t.getNomorKursi());
        System.out.println("Paket Makan    : " + (t.isPaketMakan() ? "Ya" : "Tidak"));
        System.out.println("-".repeat(50));
        System.out.printf("Harga Dasar    : Rp%,.0f%n", t.getBusDipilih().getHarga());
        System.out.printf("Paket Makan    : Rp%,.0f%n", t.isPaketMakan() ? 35000.0 : 0.0);
        System.out.printf("Pajak (11%%)    : Rp%,.0f%n", t.getPajak());
        System.out.println("=".repeat(50));
        System.out.printf("TOTAL BAYAR    : Rp%,.0f%n", t.getTotal());
        System.out.println("=".repeat(50));
        System.out.println("Terima kasih!");
    }


    // DAFTAR TRANSAKSI 
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

        for (Transaksi t : daftarTransaksi) {
            System.out.printf("%-10s %-15s %-12s %-20s %-8d Rp%,-10.0f%n",
                    t.getIdTransaksi(),
                    t.getNamaPelanggan(),
                    t.getBusDipilih().getNamaBus(),
                    t.getRute(),
                    t.getNomorKursi(),
                    t.getTotal());
        }

        System.out.println("-".repeat(90));
        System.out.println("Total transaksi: " + daftarTransaksi.size());
    }


    //  TAMBAH BUS BARU 
    public void tambahBus() {
        System.out.println("\n--- TAMBAH BUS BARU ---");
        System.out.print("Nama Bus: ");
        String namaBus = scanner.nextLine();

        System.out.print("Jenis Bus (Ekonomi/Sleeper/Eksekutif): ");
        String jenisBus = scanner.nextLine();

        System.out.print("Asal Keberangkatan: ");
        String asal = scanner.nextLine();

        System.out.print("Tujuan Keberangkatan: ");
        String tujuan = scanner.nextLine();

        double harga = 0;
        while (true) {
            System.out.print("Harga tiket: ");
            try {
                harga = Double.parseDouble(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka!");
            }
        }

        String tanggal = "";
        while (true) {
            System.out.print("Tanggal keberangkatan (YYYY-MM-DD): ");
            tanggal = scanner.nextLine();
            try {
                LocalDate.parse(tanggal, DateTimeFormatter.ISO_LOCAL_DATE);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Format tanggal salah!");
            }
        }

        int kapasitas = 0;
        while (true) {
            System.out.print("Kapasitas bus: ");
            try {
                kapasitas = Integer.parseInt(scanner.nextLine().trim());
                if (kapasitas > 0) break;
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka!");
            }
        }

        daftarBus.add(new Bus(namaBus, jenisBus, asal, tujuan, harga, tanggal, kapasitas));
        System.out.println("Bus baru berhasil ditambahkan!");
    }


    public boolean isExit(int pilihan) {
        return pilihan == 6;
    }

    public Scanner getScanner() {
        return scanner;
    }
}