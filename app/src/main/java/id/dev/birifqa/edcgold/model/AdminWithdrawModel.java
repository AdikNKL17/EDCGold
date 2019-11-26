package id.dev.birifqa.edcgold.model;

public class AdminWithdrawModel {
    private String nama_user;
    private String id_user;
    private String status_proses;
    private String tgl_withdraw;

    public AdminWithdrawModel() {
    }

    public AdminWithdrawModel(String nama_user, String id_user, String status_proses, String tgl_withdraw) {
        this.nama_user = nama_user;
        this.id_user = id_user;
        this.status_proses = status_proses;
        this.tgl_withdraw = tgl_withdraw;
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

    public String getTgl_withdraw() {
        return tgl_withdraw;
    }

    public void setTgl_withdraw(String tgl_withdraw) {
        this.tgl_withdraw = tgl_withdraw;
    }
}
