package id.dev.birifqa.edcgold.model;

public class UserAktifitasModel {
    private String id;
    private String buyer_id;
    private String seller_id;
    private String transaction_code;
    private String method;
    private String nominal;
    private String balance_point;
    private String balance_coin;
    private String amount_point;
    private String amount_coin;
    private String status;
    private String description;
    private String created_at;
    private String updated_at;
    private String type_transfer;

    public UserAktifitasModel() {
    }

    public UserAktifitasModel(String id, String buyer_id, String seller_id, String transaction_code, String method, String nominal, String balance_point, String balance_coin, String amount_point, String amount_coin, String status, String description, String created_at, String updated_at, String type_transfer) {
        this.id = id;
        this.buyer_id = buyer_id;
        this.seller_id = seller_id;
        this.transaction_code = transaction_code;
        this.method = method;
        this.nominal = nominal;
        this.balance_point = balance_point;
        this.balance_coin = balance_coin;
        this.amount_point = amount_point;
        this.amount_coin = amount_coin;
        this.status = status;
        this.description = description;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.type_transfer = type_transfer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getTransaction_code() {
        return transaction_code;
    }

    public void setTransaction_code(String transaction_code) {
        this.transaction_code = transaction_code;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getBalance_point() {
        return balance_point;
    }

    public void setBalance_point(String balance_point) {
        this.balance_point = balance_point;
    }

    public String getBalance_coin() {
        return balance_coin;
    }

    public void setBalance_coin(String balance_coin) {
        this.balance_coin = balance_coin;
    }

    public String getAmount_point() {
        return amount_point;
    }

    public void setAmount_point(String amount_point) {
        this.amount_point = amount_point;
    }

    public String getAmount_coin() {
        return amount_coin;
    }

    public void setAmount_coin(String amount_coin) {
        this.amount_coin = amount_coin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getType_transfer() {
        return type_transfer;
    }

    public void setType_transfer(String type_transfer) {
        this.type_transfer = type_transfer;
    }
}
