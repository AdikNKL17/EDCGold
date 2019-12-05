package id.dev.birifqa.edcgold.model.admin;

public class AdminReportKoinModel {
    private String status;
    private String coin;
    private String date;

    public AdminReportKoinModel() {
    }

    public AdminReportKoinModel(String status, String coin, String date) {
        this.status = status;
        this.coin = coin;
        this.date = date;
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
}
