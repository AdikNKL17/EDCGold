package id.dev.birifqa.edcgold.model.admin;

public class AdminReportTopupModel {
    private String id;
    private String transaction_code;
    private String name;
    private String userId;

    public AdminReportTopupModel() {
    }

    public AdminReportTopupModel(String id, String transaction_code, String name, String userId) {
        this.id = id;
        this.transaction_code = transaction_code;
        this.name = name;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransaction_code() {
        return transaction_code;
    }

    public void setTransaction_code(String transaction_code) {
        this.transaction_code = transaction_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
