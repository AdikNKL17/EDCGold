package id.dev.birifqa.edcgold.request;

public class RequestUpdateRental {
    private String nominal_rental;
    private String rental_days;
    private String bank_name;
    private String bank_number;
    private String account_name;

    public RequestUpdateRental() {
    }

    public RequestUpdateRental(String nominal_rental, String rental_days, String bank_name, String bank_number, String account_name) {
        this.nominal_rental = nominal_rental;
        this.rental_days = rental_days;
        this.bank_name = bank_name;
        this.bank_number = bank_number;
        this.account_name = account_name;
    }

    public String getNominal_rental() {
        return nominal_rental;
    }

    public void setNominal_rental(String nominal_rental) {
        this.nominal_rental = nominal_rental;
    }

    public String getRental_days() {
        return rental_days;
    }

    public void setRental_days(String rental_days) {
        this.rental_days = rental_days;
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
