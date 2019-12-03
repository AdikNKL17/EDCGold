package id.dev.birifqa.edcgold.model;

public class AdminUserMiningHistoryModel {
    private String status;
    private String coin;
    private String date;
    private String proses;

    public AdminUserMiningHistoryModel() {
    }

    public AdminUserMiningHistoryModel(String status, String coin, String date, String proses) {
        this.status = status;
        this.coin = coin;
        this.date = date;
        this.proses = proses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProses() {
        return proses;
    }

    public void setProses(String proses) {
        this.proses = proses;
    }
}
