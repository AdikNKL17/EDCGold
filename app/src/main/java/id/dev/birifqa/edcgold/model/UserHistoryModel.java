package id.dev.birifqa.edcgold.model;

public class UserHistoryModel {
    private String id;
    private String title;
    private String status;
    private String date;

    public UserHistoryModel() {
    }

    public UserHistoryModel(String id, String title, String status, String date) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
