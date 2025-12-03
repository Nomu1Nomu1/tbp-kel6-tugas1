import pemesanan.BusManager;
import java.util.Scanner;

public class PemesananTiketTransportasi {
    public static void main(String[] args) {
        BusManager manager = new BusManager();
        Scanner scanner = manager.getScanner();

        int pilihan;

        do {
            manager.mainMenu();
            pilihan = scanner.nextInt();
            scanner.nextLine();

            switch (pilihan) {
                case 1 -> manager.showBus();
                case 2 -> manager.pesanTiket();
                case 3 -> manager.showDaftarTrx();
                case 4 -> System.out.println("Terima kasih telah menggunakan layanan kami");
                case 5 -> manager.tambahBus(); //====perubahan====
                default -> System.out.println("Invalid input");
            }
        } while (!manager.isExit(pilihan));
    }    
}
