package id.dev.birifqa.edcgold.request;

public class RequestChangePhone {
    private String old_phone;
    private String new_phone;
    private String confirmed;

    public RequestChangePhone() {
    }

    public RequestChangePhone(String old_phone, String new_phone, String confirmed) {
        this.old_phone = old_phone;
        this.new_phone = new_phone;
        this.confirmed = confirmed;
    }

    public String getOld_phone() {
        return old_phone;
    }

    public void setOld_phone(String old_phone) {
        this.old_phone = old_phone;
    }

    public String getNew_phone() {
        return new_phone;
    }

    public void setNew_phone(String new_phone) {
        this.new_phone = new_phone;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }
}
