package id.dev.birifqa.edcgold.model.admin;

public class AdminListKomunitasModel {
    private String nama_komunitas;
    private String id;
    private String nama_ketua;
    private String alamat;

    public AdminListKomunitasModel() {
    }

    public AdminListKomunitasModel(String nama_komunitas, String id, String nama_ketua, String alamat) {
        this.nama_komunitas = nama_komunitas;
        this.id = id;
        this.nama_ketua = nama_ketua;
        this.alamat = alamat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_komunitas() {
        return nama_komunitas;
    }

    public void setNama_komunitas(String nama_komunitas) {
        this.nama_komunitas = nama_komunitas;
    }

    public String getNama_ketua() {
        return nama_ketua;
    }

    public void setNama_ketua(String nama_ketua) {
        this.nama_ketua = nama_ketua;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
