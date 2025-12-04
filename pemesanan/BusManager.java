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
        daftarBus.add(new Bus("Prima Jaya", "Ekonomi", "Jakarta", "Yogyakarta", 100000, "2026-01-01", 40));
        daftarBus.add(new Bus("Sinar Jaya", "Eksekutif", "Jakarta", "Bandung", 120000, "2026-01-07", 34));
        daftarBus.add(new Bus("Rosalia Indah", "Eksekutif", "Semarang", "Surabaya", 200000, "2026-01-08", 24));
    }

    public void mainMenu() {
        System.out.println("SiBuder OO");
        System.out.println("1. Lihat Daftar Bus Tersedia");
        System.out.println("2. Cari Rute");
        System.out.println("3. Pesan Tiket");
        System.out.println("4. Lihat Daftar Transaksi");
        System.out.println("5. Tambah Bus Baru");
        System.out.println("6. Keluar");
        System.out.print("Pilih menu (1-6): ");
    }

    public void showBus() {
        System.out.println("\nDaftar Bus Tersedia");
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

        LocalDate tanggalDipilih = null;
        while (true) {
            System.out.print("Masukkan tanggal keberangkatan (YYYY-MM-DD): ");
            String inputTanggal = scanner.nextLine();
            
            try {
                tanggalDipilih = LocalDate.parse(inputTanggal, DateTimeFormatter.ISO_LOCAL_DATE);
                
                LocalDate today = LocalDate.now();
                if (tanggalDipilih.isBefore(today.plusDays(1))) {
                    System.out.println("Tanggal keberangkatan harus minimal H-1 dari hari ini (" + today.plusDays(1) + ")!");
                    continue;
                }
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Format tanggal salah! Gunakan format YYYY-MM-DD.");
            }
        }

        ArrayList<Bus> busTersedia = new ArrayList<>();
        for (Bus b : daftarBus) {
            LocalDate tanggalBus = LocalDate.parse(b.getTanggalKeberangkatan());
            if (tanggalBus.equals(tanggalDipilih) && b.getKursiTersedia() > 0) {
                busTersedia.add(b);
            }
        }

        if (busTersedia.isEmpty()) {
            System.out.println("Maaf, tidak ada bus tersedia pada tanggal " + tanggalDipilih + ".");
            return;
        }

        System.out.println("\nDaftar Bus Tersedia pada tanggal " + tanggalDipilih + ":");
        System.out.println(" - ".repeat(50));
        System.out.printf("%-3s %-15s %-12s %-10s %-12s %-12s %-10s %-10s%n",
                "No", "Nama Bus", "Jenis", "Asal", "Tujuan", "Harga", "Tanggal", "Sisa Kursi");
        System.out.println(" - ".repeat(50));
        for (int i = 0; i < busTersedia.size(); i++) {
            System.out.printf("%-3d %s%n", (i + 1), busTersedia.get(i));
        }
        System.out.println(" - ".repeat(50));

        System.out.print("Pilih nomor bus (1-" + busTersedia.size() + "): ");
        int pilihan = scanner.nextInt() - 1;
        scanner.nextLine();

        if (pilihan < 0 || pilihan >= busTersedia.size()) {
            System.out.println("Pilihan bus tidak valid!");
            return;
        }

        Bus bus = busTersedia.get(pilihan);

        System.out.println("\nDetail Bus Dipilih:");
        System.out.println("Nama Bus       : " + bus.getNamaBus());
        System.out.println("Jenis          : " + bus.getJenisBus());
        System.out.println("Rute           : " + bus.getAsalKeberangkatan() + " to " + bus.getTujuan());
        System.out.println("Harga Tiket    : Rp" + String.format("%,.0f", bus.getHarga()));
        System.out.println("Tanggal        : " + bus.getTanggalKeberangkatan());
        System.out.println("Kapasitas      : " + bus.getKursiTersedia() + " kursi tersedia dari " + bus.getKapasitas());

        int jumlahKursi = 0;
        while (true) {
            System.out.print("\nMau pesan berapa kursi? (1-" + bus.getKursiTersedia() + "): ");
            try {
                jumlahKursi = Integer.parseInt(scanner.nextLine().trim());
                if (jumlahKursi < 1 || jumlahKursi > bus.getKursiTersedia()) {
                    System.out.println("Jumlah kursi tidak valid! Maksimal " + bus.getKursiTersedia() + " kursi tersedia.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka! Silakan coba lagi.");
            }
        }

        ArrayList<Integer> daftarKursiDipilih = new ArrayList<>();
        ArrayList<String> daftarNamaPenumpang = new ArrayList<>();
        
        for (int i = 1; i <= jumlahKursi; i++) {
            System.out.println("\n--- Penumpang ke-" + i + " ---");
            
            System.out.print("Nama penumpang: ");
            String namaPenumpang = scanner.nextLine();
            daftarNamaPenumpang.add(namaPenumpang);
            
            int kursi = -1;
            while (true) {
                System.out.print("Nomor kursi (1-" + bus.getKapasitas() + "): ");
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
                
                if (daftarKursiDipilih.contains(kursi)) {
                    System.out.println("Kursi sudah dipilih sebelumnya! Pilih kursi lain.");
                    continue;
                }
                
                daftarKursiDipilih.add(kursi);
                break;
            }
        }

        System.out.print("\nTambah paket makan? (y/n): ");
        String makanInput = scanner.nextLine().toLowerCase();
        boolean paketMakan = makanInput.equals("y") || makanInput.equals("ya");

        double hargaMakan = paketMakan ? 35000.0 * jumlahKursi : 0.0;
        double subtotal = (bus.getHarga() * jumlahKursi) + hargaMakan;
        double pajak = subtotal * 0.11;
        double total = subtotal + pajak;

        System.out.println("\n--- KONFIRMASI PEMESANAN ---");
        System.out.println("Bus            : " + bus.getNamaBus() + " (" + bus.getJenisBus() + ")");
        System.out.println("Rute           : " + bus.getAsalKeberangkatan() + " to " + bus.getTujuan());
        System.out.println("Jumlah Kursi   : " + jumlahKursi);
        System.out.println("\nDaftar Penumpang:");
        for (int i = 0; i < jumlahKursi; i++) {
            System.out.println((i + 1) + ". " + daftarNamaPenumpang.get(i) + " - Kursi " + daftarKursiDipilih.get(i));
        }
        System.out.println("\nPaket Makan    : " + (paketMakan ? "Ya (Rp35.000 x " + jumlahKursi + ")" : "Tidak"));
        System.out.printf("Harga Tiket    : Rp%,.0f x %d = Rp%,.0f%n", bus.getHarga(), jumlahKursi, bus.getHarga() * jumlahKursi);
        System.out.printf("Paket Makan    : Rp%,.0f%n", hargaMakan);
        System.out.printf("Pajak (11%%)    : Rp%,.0f%n", pajak);
        System.out.println("=".repeat(40));
        System.out.printf("TOTAL BAYAR    : Rp%,.0f%n", total);

        System.out.print("\nKonfirmasi? (y/n): ");
        String konfirm = scanner.nextLine().toLowerCase();

        if (konfirm.equals("y") || konfirm.equals("ya")) {
            for (int kursi : daftarKursiDipilih) {
                bus.pesanKursi(kursi);
            }
            
            String id = "TKT" + idCounter++;
            Transaksi t = new Transaksi(daftarNamaPenumpang, bus, daftarKursiDipilih, paketMakan, id, bus.getTanggalKeberangkatan());
            daftarTransaksi.add(t);
            showNota(t);
            System.out.println("\nPemesanan berhasil!");
        } else {
            System.out.println("Pemesanan dibatalkan.");
        }
    }

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

        String asal = pilihKota("Kota asal: ", dapatkanKotaAsal(busAktif));
        if (asal == null) return;

        String tujuan = pilihKota("Kota tujuan: ", dapatkanKotaTujuan(busAktif, asal));
        if (tujuan == null) return;

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

        System.out.print("Pilih nomor bus untuk booking (0 = batal): ");
        int pilih;
        try {
            pilih = Integer.parseInt(scanner.nextLine().trim());
            if (pilih == 0) return;
            if (pilih < 1 || pilih > hasil.size()) throw new Exception();
        } catch (Exception e) {
            System.out.println("Pilihan tidak valid!");
            return;
        }

        Bus busTerpilih = hasil.get(pilih - 1);
        bookingDariCariRute(busTerpilih);
    }

    private void bookingDariCariRute(Bus bus) {
        System.out.println("\n--- BOOKING TIKET ---");
        System.out.println("Bus: " + bus.getNamaBus() + " | " + bus.getAsalKeberangkatan() + " to " + bus.getTujuan());
        System.out.println("Kapasitas: " + bus.getKursiTersedia() + " kursi tersedia dari " + bus.getKapasitas());

        int jumlahKursi = 0;
        while (true) {
            System.out.print("\nMau pesan berapa kursi? (1-" + bus.getKursiTersedia() + "): ");
            try {
                jumlahKursi = Integer.parseInt(scanner.nextLine().trim());
                if (jumlahKursi < 1 || jumlahKursi > bus.getKursiTersedia()) {
                    System.out.println("Jumlah kursi tidak valid! Maksimal " + bus.getKursiTersedia() + " kursi tersedia.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka! Silakan coba lagi.");
            }
        }

        ArrayList<Integer> daftarKursiDipilih = new ArrayList<>();
        ArrayList<String> daftarNamaPenumpang = new ArrayList<>();
        
        for (int i = 1; i <= jumlahKursi; i++) {
            System.out.println("\n--- Penumpang ke-" + i + " ---");
            
            System.out.print("Nama penumpang: ");
            String namaPenumpang = scanner.nextLine();
            daftarNamaPenumpang.add(namaPenumpang);
            
            int kursi = -1;
            while (true) {
                System.out.print("Nomor kursi (1-" + bus.getKapasitas() + "): ");
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
                
                if (daftarKursiDipilih.contains(kursi)) {
                    System.out.println("Kursi sudah dipilih sebelumnya! Pilih kursi lain.");
                    continue;
                }
                
                daftarKursiDipilih.add(kursi);
                break;
            }
        }

        System.out.print("\nTambah paket makan? (y/n): ");
        String makanInput = scanner.nextLine().toLowerCase();
        boolean paketMakan = makanInput.equals("y") || makanInput.equals("ya");

        double hargaMakan = paketMakan ? 35000.0 * jumlahKursi : 0.0;
        double subtotal = (bus.getHarga() * jumlahKursi) + hargaMakan;
        double pajak = subtotal * 0.11;
        double total = subtotal + pajak;

        System.out.println("\n--- KONFIRMASI PEMESANAN ---");
        System.out.println("Bus            : " + bus.getNamaBus() + " (" + bus.getJenisBus() + ")");
        System.out.println("Rute           : " + bus.getAsalKeberangkatan() + " to " + bus.getTujuan());
        System.out.println("Tanggal        : " + bus.getTanggalKeberangkatan());
        System.out.println("Jumlah Kursi   : " + jumlahKursi);
        System.out.println("\nDaftar Penumpang:");
        for (int i = 0; i < jumlahKursi; i++) {
            System.out.println((i + 1) + ". " + daftarNamaPenumpang.get(i) + " - Kursi " + daftarKursiDipilih.get(i));
        }
        System.out.println("\nPaket Makan    : " + (paketMakan ? "Ya (Rp35.000 x " + jumlahKursi + ")" : "Tidak"));
        System.out.printf("Harga Tiket    : Rp%,.0f x %d = Rp%,.0f%n", bus.getHarga(), jumlahKursi, bus.getHarga() * jumlahKursi);
        System.out.printf("Paket Makan    : Rp%,.0f%n", hargaMakan);
        System.out.printf("Pajak (11%%)    : Rp%,.0f%n", pajak);
        System.out.println("=".repeat(40));
        System.out.printf("TOTAL BAYAR    : Rp%,.0f%n", total);

        System.out.print("\nKonfirmasi? (y/n): ");
        String konfirm = scanner.nextLine().toLowerCase();

        if (konfirm.equals("y") || konfirm.equals("ya")) {
            for (int kursi : daftarKursiDipilih) {
                bus.pesanKursi(kursi);
            }
            
            String id = "TKT" + idCounter++;
            Transaksi t = new Transaksi(daftarNamaPenumpang, bus, daftarKursiDipilih, paketMakan, id, bus.getTanggalKeberangkatan());
            daftarTransaksi.add(t);
            showNota(t);
            System.out.println("\nPemesanan berhasil!");
        } else {
            System.out.println("Pemesanan dibatalkan.");
        }
    }

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
                System.out.println("- " + cocok.get(0));
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

    public void showNota(Transaksi t) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("            NOTA PEMESANAN TIKET BUS");
        System.out.println("=".repeat(50));
        System.out.println("ID Transaksi   : " + t.getIdTransaksi());
        System.out.println("Bus            : " + t.getBusDipilih().getNamaBus());
        System.out.println("Jenis          : " + t.getBusDipilih().getJenisBus());
        System.out.println("Rute           : " + t.getBusDipilih().getAsalKeberangkatan() + " to "
                + t.getBusDipilih().getTujuan());
        System.out.println("Tanggal        : " + t.getBusDipilih().getTanggalKeberangkatan());
        System.out.println("Jumlah Kursi   : " + t.getJumlahKursi());
        System.out.println("\nDaftar Penumpang:");
        ArrayList<String> namaList = t.getDaftarNamaPenumpang();
        ArrayList<Integer> kursiList = t.getDaftarKursi();
        for (int i = 0; i < namaList.size(); i++) {
            System.out.println((i + 1) + ". " + namaList.get(i) + " - Kursi " + kursiList.get(i));
        }
        System.out.println("\nPaket Makan    : " + (t.isPaketMakan() ? "Ya" : "Tidak"));
        System.out.println("-".repeat(50));
        System.out.printf("Harga Dasar    : Rp%,.0f x %d = Rp%,.0f%n", 
                t.getBusDipilih().getHarga(), t.getJumlahKursi(), t.getBusDipilih().getHarga() * t.getJumlahKursi());
        System.out.printf("Paket Makan    : Rp%,.0f%n", t.isPaketMakan() ? 35000.0 * t.getJumlahKursi() : 0.0);
        System.out.printf("Subtotal       : Rp%,.0f%n",
                (t.getBusDipilih().getHarga() * t.getJumlahKursi()) + (t.isPaketMakan() ? 35000.0 * t.getJumlahKursi() : 0.0));
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

        System.out.println("\n" + "-".repeat(100));
        System.out.println("DAFTAR TRANSAKSI");
        System.out.println("-".repeat(100));
        System.out.printf("%-10s %-20s %-12s %-20s %-15s %-12s%n",
                "ID", "Penumpang", "Bus", "Rute", "Kursi", "Total");
        System.out.println("-".repeat(100));

        for (Transaksi t : daftarTransaksi) {
            String namaPenumpang = t.getJumlahKursi() == 1 
                ? t.getDaftarNamaPenumpang().get(0) 
                : t.getDaftarNamaPenumpang().get(0) + " +(" + (t.getJumlahKursi() - 1) + ")";
            
            String kursiInfo = t.getJumlahKursi() == 1 
                ? String.valueOf(t.getDaftarKursi().get(0)) 
                : t.getJumlahKursi() + " kursi";
            
            System.out.printf("%-10s %-20s %-12s %-20s %-15s Rp%,-10.0f%n",
                    t.getIdTransaksi(),
                    namaPenumpang.length() > 18 ? namaPenumpang.substring(0, 18) + ".." : namaPenumpang,
                    t.getBusDipilih().getNamaBus().length() > 10
                            ? t.getBusDipilih().getNamaBus().substring(0, 10) + ".."
                            : t.getBusDipilih().getNamaBus(),
                    t.getRute(), kursiInfo, t.getTotal());
        }
        System.out.println("-".repeat(100));
        System.out.println("Total transaksi: " + daftarTransaksi.size());
    }

    public void tambahBus() {
        System.out.println("\n--- TAMBAH BUS BARU ---");
        System.out.print("Nama Bus: ");
        String namaBus = scanner.nextLine();

        String jenisBus = "";
        while (true) {
            System.out.print("Jenis Bus (Ekonomi/Sleeper/Eksekutif): ");
            String input = scanner.nextLine().toLowerCase();
            
            if (input.equals("ekonomi") || input.equals("sleeper") || input.equals("eksekutif")) {
                jenisBus = input.substring(0, 1).toUpperCase() + input.substring(1);
                break;
            } else {
                System.out.println("Jenis bus tidak valid! Pilih: Ekonomi, Sleeper, atau Eksekutif.");
            }
        }

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
                System.out.println("Input harus angka! Silakan coba lagi.");
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
                System.out.println("Format tanggal salah! Gunakan YYYY-MM-DD.");
            }
        }

        int kapasitas = 0;
        while (true) {
            System.out.print("Kapasitas bus: ");
            try {
                kapasitas = Integer.parseInt(scanner.nextLine().trim());
                if (kapasitas > 0) break;
            } catch (NumberFormatException e) {
                System.out.println("Input harus angka! Silakan coba lagi.");
            }
        }

        Bus busBaru = new Bus(namaBus, jenisBus, asal, tujuan, harga, tanggal, kapasitas);
        daftarBus.add(busBaru);

        System.out.println("Bus baru berhasil ditambahkan!");
    }

    public Scanner getScanner() {
        return scanner;
    }

    public boolean isExit(int pilihan) {
        return pilihan == 6;
    }
}