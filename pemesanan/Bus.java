package pemesanan;

import java.util.ArrayList;

public class Bus {
    // Inisialisasi namaBus, jenisBus, asalKeberangkatan, tujuanKeberangkatan,
    // harga, tanggalKeberangkatan, kapasistas, kursiTerisi
    // lalu dimasukkan di function public Bus(), misal public Bus(String namaBus,
    // ...)
    private String namaBus;
    private String jenisBus;
    private String asalKeberangkatan;
    private String tujuanKeberangkatan;
    private double harga;
    private String tanggalKeberangkatan;
    private int kapasitas;
    private ArrayList<Integer> kursiTerisi;

    public Bus(String namaBus, String jenisBus, String asalKeberangkatan, String tujuanKeberangkatan, double harga,
            String tanggalKeberangkatan, int kapasistas) {
        this.namaBus = namaBus;
        this.jenisBus = jenisBus;
        this.asalKeberangkatan = asalKeberangkatan;
        this.tujuanKeberangkatan = tujuanKeberangkatan;
        this.harga = harga;
        this.tanggalKeberangkatan = tanggalKeberangkatan;
        this.kapasitas = kapasistas;
        this.kursiTerisi = new ArrayList<>();
    }
    // Getter
    public String getNamaBus() { return namaBus; }
    public String getJenisBus() { return jenisBus; }
    public String getAsalKeberangkatan() { return asalKeberangkatan; }
    public String getTujuan() { return tujuanKeberangkatan; }
    public double getHarga() { return harga; }
    public String getTanggalKeberangkatan() { return tanggalKeberangkatan; }

    // Getter buat yang dibawah ini biar gampang aja hehe
    // return nilai kursi yang tersedia
    public int getKursiTersedia() {

    }

    // Return kursi tersedia
    public boolean isKursiAvailable(int nomorKursi) {

    }

    // Pesan kursi
    public void pesanKursi(int nomorKursi) {

    }

    // Formatting yang tadi diatas
    @Override
    public String toString() {
        return String.format("%-15s %-12s %10s %12s Rp%, -10.0f %-10s %d/%d", null)
    }
}
