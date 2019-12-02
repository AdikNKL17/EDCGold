package id.dev.birifqa.edcgold.model;

public class AdminReportTopupModel {
    private String id_transaksi;

    public AdminReportTopupModel() {
    }

    public AdminReportTopupModel(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }
}
