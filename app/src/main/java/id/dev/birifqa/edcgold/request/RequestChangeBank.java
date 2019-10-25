package id.dev.birifqa.edcgold.request;

public class RequestChangeBank {
    private String bank_name;
    private String bank_number;
    private String account_name;

    public RequestChangeBank() {
    }

    public RequestChangeBank(String bank_name, String bank_number, String account_name) {
        this.bank_name = bank_name;
        this.bank_number = bank_number;
        this.account_name = account_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
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
}
