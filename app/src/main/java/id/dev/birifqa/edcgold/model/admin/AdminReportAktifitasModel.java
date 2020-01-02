package id.dev.birifqa.edcgold.model.admin;

public class AdminReportAktifitasModel {
    private String id;
    private String transaction_code;
    private String status;

    public AdminReportAktifitasModel() {
    }

    public AdminReportAktifitasModel(String id, String transaction_code, String status) {
        this.id = id;
        this.transaction_code = transaction_code;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
