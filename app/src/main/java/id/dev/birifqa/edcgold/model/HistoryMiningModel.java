package id.dev.birifqa.edcgold.model;

public class HistoryMiningModel {
    private String id;
    private String user_id;
    private String sewa_mining_id;
    private String coin_balance;
    private String aging_result;
    private String days_to;
    private String created_at;
    private String updated_at;

    public HistoryMiningModel() {
    }

    public HistoryMiningModel(String id, String user_id, String sewa_mining_id, String coin_balance, String aging_result, String days_to, String created_at, String updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.sewa_mining_id = sewa_mining_id;
        this.coin_balance = coin_balance;
        this.aging_result = aging_result;
        this.days_to = days_to;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public String getSewa_mining_id() {
        return sewa_mining_id;
    }

    public void setSewa_mining_id(String sewa_mining_id) {
        this.sewa_mining_id = sewa_mining_id;
    }

    public String getCoin_balance() {
        return coin_balance;
    }

    public void setCoin_balance(String coin_balance) {
        this.coin_balance = coin_balance;
    }

    public String getAging_result() {
        return aging_result;
    }

    public void setAging_result(String aging_result) {
        this.aging_result = aging_result;
    }

    public String getDays_to() {
        return days_to;
    }

    public void setDays_to(String days_to) {
        this.days_to = days_to;
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

