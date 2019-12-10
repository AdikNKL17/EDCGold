package id.dev.birifqa.edcgold.request;

public class RequestWithdrawProcess {
    private String status;
    private String bank_name;
    private String account_name;
    private String bank_number;
    private String transfer_amount;
    private String transfer_date;
    private String images;

    public RequestWithdrawProcess() {
    }

    public RequestWithdrawProcess(String status, String bank_name, String account_name, String bank_number, String transfer_amount, String transfer_date, String images) {
        this.status = status;
        this.bank_name = bank_name;
        this.account_name = account_name;
        this.bank_number = bank_number;
        this.transfer_amount = transfer_amount;
        this.transfer_date = transfer_date;
        this.images = images;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getBank_number() {
        return bank_number;
    }

    public void setBank_number(String bank_number) {
        this.bank_number = bank_number;
    }

    public String getTransfer_amount() {
        return transfer_amount;
    }

    public void setTransfer_amount(String transfer_amount) {
        this.transfer_amount = transfer_amount;
    }

    public String getTransfer_date() {
        return transfer_date;
    }

    public void setTransfer_date(String transfer_date) {
        this.transfer_date = transfer_date;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
