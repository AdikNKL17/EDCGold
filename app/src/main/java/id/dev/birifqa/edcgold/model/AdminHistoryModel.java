package id.dev.birifqa.edcgold.model;

public class AdminHistoryModel {
    private String status;
    private String date;

    public AdminHistoryModel() {
    }

    public AdminHistoryModel(String status, String date) {
        this.status = status;
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
