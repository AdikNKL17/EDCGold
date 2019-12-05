package id.dev.birifqa.edcgold.request;

public class RequestChangePassword {

    private String verification;
    private String password;
    private String confirmed;

    public RequestChangePassword() {
    }

    public RequestChangePassword(String verification, String password, String confirmed) {
        this.verification = verification;
        this.password = password;
        this.confirmed = confirmed;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }
}
