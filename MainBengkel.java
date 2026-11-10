
public class MainBengkel {
    public static void main(String[] args) {

        SistemBengkel bengkel =new SistemBengkel("Bengkel Auto Prima");

        bengkel.tambahPelanggan("ACT-01", "Mira");
        bengkel.tambahPelanggan("ACT-02", "Nanda");

        bengkel.tambahLayananServis("OBJ-A", "Turun Mesin Premium", 10);
        bengkel.tambahLayananServis("OBJ-B", "Ganti Oli Reguler", 2);
        bengkel.tambahLayananServis("OBJ-C", "Cuci Mobil Ekonomis", 50);

        System.out.println("--- MEMULAI PEMBACAAN FILE TRANSAKSI ---");

        try {
            bengkel.jalankanTransaksiDariFile("input_transaksi.txt");

        } catch (Exception e) {
            System.out.println("[SISTEM FATAL ERROR] File gagal dibaca/diproses: "
                + e.getMessage());
        }

        bengkel.simpanLaporanAkhir();
    }
}
