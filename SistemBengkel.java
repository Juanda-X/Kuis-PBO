import java.util.HashMap;
import java.util.ArrayList;
import java.io.Serializable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class SistemBengkel implements Serializable {
    private String namaBengkel;

    private HashMap<String, Pelanggan> daftarPelanggan;
    private HashMap<String, LayananServis> daftarLayanan;
    private ArrayList<Transaksi> daftarTransaksi;

    public SistemBengkel(String namaBengkel) {
        this.namaBengkel = namaBengkel;

        daftarPelanggan = new HashMap<>();
        daftarLayanan = new HashMap<>();
        daftarTransaksi = new ArrayList<>();
    }

    public void tambahPelanggan(String id, String nama) {
        Pelanggan pelanggan = new Pelanggan(id, nama);
        daftarPelanggan.put(id, pelanggan);
    }

    public void tambahLayananServis(String kode, String nama, int kuota) {
        LayananServis layanan = new LayananServis(kode, nama, kuota);
        daftarLayanan.put(kode, layanan);
    }

    private void prosesTransaksi(String idPelanggan, String kodeLayanan, int jumlah) throws BengkelException {
        Pelanggan pelanggan = daftarPelanggan.get(idPelanggan);

        if (pelanggan == null) {
            throw new BengkelException("Pelanggan " + idPelanggan + " tidak ditemukan.");
        }

        LayananServis layanan = daftarLayanan.get(kodeLayanan);

        if (layanan == null) {
            throw new BengkelException("Layanan " + kodeLayanan + " tidak ditemukan.");
        }

        if (jumlah <= 0) {
            throw new BengkelException("Jumlah transaksi harus lebih dari 0.");
        }

        if (layanan.getKuota() < jumlah) {
            throw new BengkelException("Kuota " + kodeLayanan + " tidak mencukupi.");
        }

        layanan.kurangiKuota(jumlah);

        Transaksi transaksi = new Transaksi(pelanggan, layanan, jumlah);
        daftarTransaksi.add(transaksi);

        System.out.println("[Berhasil] " + idPelanggan + " menggunakan " + kodeLayanan + " sebanyak " + jumlah);
    }

    public void jalankanTransaksiDariFile(String namaFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(namaFile))) {
            String baris;
            while ((baris = reader.readLine()) != null) {
                if (baris.trim().isEmpty()) {
                    continue;
                }

                try {
                    String[] data = baris.split(";");

                    String id = "";
                    String kode = "";
                    int jumlah = 0;

                    for (String bagian : data) {
                        if (bagian.trim().isEmpty()) {
                            continue;
                        }

                        String[] pasangan = bagian.split(":", 2);

                        if (pasangan.length != 2) {
                            throw new BengkelException("Format data tidak valid!");
                        }

                        String atribut = pasangan[0].trim();
                        String nilai = pasangan[1].trim();

                        switch (atribut) {
                            case "ID":
                                id = nilai;
                                break;

                            case "KD":
                                kode = nilai;
                                break;

                            case "JML":
                                jumlah = Integer.parseInt(nilai);
                                break;

                            default:
                                throw new BengkelException("Atribut tidak dikenal: " + atribut);
                        }
                    }

                    if (id.isEmpty() || kode.isEmpty()) {
                        throw new BengkelException("Data transaksi tidak lengkap!");
                    }

                    prosesTransaksi(id, kode, jumlah);

                } catch (BengkelException | NumberFormatException e) {
                    System.out.println("[TRANSAKSI DITOLAK] " + e.getMessage());
                }
            }
        }
    }

    public void simpanLaporanAkhir() {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("sistem_state.ser"))) {
            output.writeObject(this);
            System.out.println("\n[BERHASIL] Data sistem disimpan ke sistem_state.ser");
        } catch (IOException e) {
            System.out.println("[ERROR SERIALIZATION] " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("laporan.txt"))) {
            writer.write("DAFTAR AKTOR AKTIF");
            writer.newLine();

            for (Pelanggan p : daftarPelanggan.values()) {
                writer.write("ID: " + p.getId() + " | Nama: " + p.getNama());
                writer.newLine();
            }

            System.out.println("[BERHASIL] Laporan dibuat: laporan.txt");
        } catch (IOException e) {
            System.out.println("[ERROR LAPORAN] " + e.getMessage());
        }
    }
}