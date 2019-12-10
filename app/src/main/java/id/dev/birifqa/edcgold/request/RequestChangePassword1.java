package id.dev.birifqa.edcgold.request;

public class RequestChangePassword1 {

    private String old_password;
    private String new_password;
    private String confirmed;

    public RequestChangePassword1() {
    }

    public RequestChangePassword1(String old_password, String new_password, String confirmed) {
        this.old_password = old_password;
        this.new_password = new_password;
        this.confirmed = confirmed;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }
}
