package id.dev.birifqa.edcgold.model;

public class AdminUserMiningModel {
    private String nama_user;
    private String id_user;
    private String mulai_mining;
    private String sisa_waktu;

    public AdminUserMiningModel() {
    }

    public AdminUserMiningModel(String nama_user, String id_user, String mulai_mining, String sisa_waktu) {
        this.nama_user = nama_user;
        this.id_user = id_user;
        this.mulai_mining = mulai_mining;
        this.sisa_waktu = sisa_waktu;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getMulai_mining() {
        return mulai_mining;
    }

    public void setMulai_mining(String mulai_mining) {
        this.mulai_mining = mulai_mining;
    }

    public String getSisa_waktu() {
        return sisa_waktu;
    }

    public void setSisa_waktu(String sisa_waktu) {
        this.sisa_waktu = sisa_waktu;
    }
}
