package id.dev.birifqa.edcgold.model.admin;

public class AdminReportTransferModel {
    private String transaction_code;
    private String id;

    public AdminReportTransferModel() {
    }

    public AdminReportTransferModel(String transaction_code, String id) {
        this.transaction_code = transaction_code;
        this.id = id;
    }

    public String getTransaction_code() {
        return transaction_code;
    }

    public void setTransaction_code(String transaction_code) {
        this.transaction_code = transaction_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
