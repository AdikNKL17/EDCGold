package id.dev.birifqa.edcgold.model.admin;

public class AdminUserMiningModel {
    private String id;
    private String user_id;
    private String start_mining;
    private String end_mining;
    private String amount_day;
    private String status;
    private String created_at;
    private String updated_at;
    private String name;
    private String userid;
    private String remaining_time;


    public AdminUserMiningModel() {
    }

    public AdminUserMiningModel(String id, String user_id, String start_mining, String end_mining, String amount_day, String status, String created_at, String updated_at, String name, String userid, String remaining_time) {
        this.id = id;
        this.user_id = user_id;
        this.start_mining = start_mining;
        this.end_mining = end_mining;
        this.amount_day = amount_day;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.name = name;
        this.userid = userid;
        this.remaining_time = remaining_time;
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

    public String getStart_mining() {
        return start_mining;
    }

    public void setStart_mining(String start_mining) {
        this.start_mining = start_mining;
    }

    public String getEnd_mining() {
        return end_mining;
    }

    public void setEnd_mining(String end_mining) {
        this.end_mining = end_mining;
    }

    public String getAmount_day() {
        return amount_day;
    }

    public void setAmount_day(String amount_day) {
        this.amount_day = amount_day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRemaining_time() {
        return remaining_time;
    }

    public void setRemaining_time(String remaining_time) {
        this.remaining_time = remaining_time;
    }
}
