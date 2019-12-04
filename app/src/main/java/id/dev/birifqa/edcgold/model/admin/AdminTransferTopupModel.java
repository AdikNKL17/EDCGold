package id.dev.birifqa.edcgold.model.admin;

public class AdminTransferTopupModel {
    private String nama_user;
    private String id_user;
    private String status_proses;
    private String tgl_topup;

    public AdminTransferTopupModel() {
    }

    public AdminTransferTopupModel(String nama_user, String id_user, String status_proses, String tgl_topup) {
        this.nama_user = nama_user;
        this.id_user = id_user;
        this.status_proses = status_proses;
        this.tgl_topup = tgl_topup;
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

    public String getStatus_proses() {
        return status_proses;
    }

    public void setStatus_proses(String status_proses) {
        this.status_proses = status_proses;
    }

    public String getTgl_topup() {
        return tgl_topup;
    }

    public void setTgl_topup(String tgl_topup) {
        this.tgl_topup = tgl_topup;
    }
}
