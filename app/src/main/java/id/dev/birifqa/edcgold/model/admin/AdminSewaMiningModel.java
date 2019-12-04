package id.dev.birifqa.edcgold.model.admin;

public class AdminSewaMiningModel {
    private String nama_user;
    private String id_user;
    private String no_transaksi;
    private String tgl_transaksi;

    public AdminSewaMiningModel() {
    }

    public AdminSewaMiningModel(String nama_user, String id_user, String no_transaksi, String tgl_transaksi) {
        this.nama_user = nama_user;
        this.id_user = id_user;
        this.no_transaksi = no_transaksi;
        this.tgl_transaksi = tgl_transaksi;
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

    public String getNo_transaksi() {
        return no_transaksi;
    }

    public void setNo_transaksi(String no_transaksi) {
        this.no_transaksi = no_transaksi;
    }

    public String getTgl_transaksi() {
        return tgl_transaksi;
    }

    public void setTgl_transaksi(String tgl_transaksi) {
        this.tgl_transaksi = tgl_transaksi;
    }
}
