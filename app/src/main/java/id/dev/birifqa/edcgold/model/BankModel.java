package id.dev.birifqa.edcgold.model;

public class BankModel {
    private String id;
    private String user_id;
    private String bank_name;
    private String bank_number;
    private String account_name;
    private String created_at;
    private String updated_at;

    public BankModel() {
    }

    public BankModel(String id, String user_id, String bank_name, String bank_number, String account_name, String created_at, String updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.bank_name = bank_name;
        this.bank_number = bank_number;
        this.account_name = account_name;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBank_number() {
        return bank_number;
    }

    public void setBank_number(String bank_number) {
        this.bank_number = bank_number;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
