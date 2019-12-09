package id.dev.birifqa.edcgold.request;

public class RequestUpdateUser {
    private String status_active;
    private String type_member;
    private String reason_close;

    public RequestUpdateUser() {
    }

    public RequestUpdateUser(String status_active, String type_member, String reason_close) {
        this.status_active = status_active;
        this.type_member = type_member;
        this.reason_close = reason_close;
    }

    public String getStatus_active() {
        return status_active;
    }

    public void setStatus_active(String status_active) {
        this.status_active = status_active;
    }

    public String getType_member() {
        return type_member;
    }

    public void setType_member(String type_member) {
        this.type_member = type_member;
    }

    public String getReason_close() {
        return reason_close;
    }

    public void setReason_close(String reason_close) {
        this.reason_close = reason_close;
    }
}
