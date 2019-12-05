package id.dev.birifqa.edcgold.model.admin;

public class AdminTransferTopupModel {
    private String id;
    private String transaction_code;
    private String userid;
    private String name;
    private String status;
    private String date;

    public AdminTransferTopupModel() {
    }

    public AdminTransferTopupModel(String id, String transaction_code, String userid, String name, String status, String date) {
        this.id = id;
        this.transaction_code = transaction_code;
        this.userid = userid;
        this.name = name;
        this.status = status;
        this.date = date;
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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
