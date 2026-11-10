public class Pelanggan extends Aktor {

    public Pelanggan(String id, String nama) {
        super(id, nama);
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("ID: " + getId() +" | Nama: " + getNama());
    }
}
