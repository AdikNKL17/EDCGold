package id.dev.birifqa.edcgold.request;

public class RequestChangeEmail {
    private String verification;
    private String old_email;
    private String new_email;
    private String confirmed;

    public RequestChangeEmail() {
    }

    public RequestChangeEmail(String verification, String old_email, String new_email, String confirmed) {
        this.verification = verification;
        this.old_email = old_email;
        this.new_email = new_email;
        this.confirmed = confirmed;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getOld_email() {
        return old_email;
    }

    public void setOld_email(String old_email) {
        this.old_email = old_email;
    }

    public String getNew_email() {
        return new_email;
    }

    public void setNew_email(String new_email) {
        this.new_email = new_email;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }
}
