package id.dev.birifqa.edcgold.request;

public class RequestChangeRate {
    private String sale_rate;
    private String buy_rate;

    public RequestChangeRate() {
    }

    public RequestChangeRate(String sale_rate, String buy_rate) {
        this.sale_rate = sale_rate;
        this.buy_rate = buy_rate;
    }

    public String getSale_rate() {
        return sale_rate;
    }

    public void setSale_rate(String sale_rate) {
        this.sale_rate = sale_rate;
    }

    public String getBuy_rate() {
        return buy_rate;
    }

    public void setBuy_rate(String buy_rate) {
        this.buy_rate = buy_rate;
    }
}
