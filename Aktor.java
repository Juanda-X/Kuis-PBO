import java.io.Serializable;

public abstract class Aktor implements Serializable {
    private String id;
    private String nama;

    public Aktor(String id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public abstract void tampilkanInfo();
}
