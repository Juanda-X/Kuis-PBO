import java.io.Serializable;

public class LayananServis implements Serializable {
    private String kode;
    private String nama;
    private int kuota;

    public LayananServis(String kode, String nama, int kuota) {
        this.kode = kode;
        this.nama = nama;
        this.kuota = kuota;
    }

    public String getKode() {
        return kode;
    }

    public String getNama() {
        return nama;
    }

    public int getKuota() {
        return kuota;
    }

    public void kurangiKuota(int jumlah) {
        kuota -= jumlah;
    }

    public void tampilkanInfo() {
        System.out.println(kode + " | " + nama + " |Kuota: " + kuota);
    }
}
