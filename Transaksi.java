import java.io.Serializable;

public class Transaksi implements Serializable {
    private Pelanggan pelanggan;
    private LayananServis layanan;
    private int jumlah;

    public Transaksi(Pelanggan pelanggan, LayananServis layanan, int jumlah) {
        this.pelanggan = pelanggan;
        this.layanan = layanan;
        this.jumlah = jumlah;
    }

    public Pelanggan getPelanggan() {
        return pelanggan;
    }

    public LayananServis getLayanan() {
        return layanan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void tampilkanInfo() {
        System.out.println(pelanggan.getId() + " | " + layanan.getKode() + " | " + jumlah);
    }
}
