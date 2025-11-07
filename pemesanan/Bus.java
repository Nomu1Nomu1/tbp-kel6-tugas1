package pemesanan;
import java.util.ArrayList;

public class Bus {
    // Inisialisasi namaBus, jenisBus, asalKeberangkatan, tujuanKeberangkatan, harga, tanggalKeberangkatan, kapasistas, kursiTerisi
    // lalu dimasukkan di function public Bus(), misal public Bus(String namaBus, ...)

    public Bus() {

    }
    // Getter

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
