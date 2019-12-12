package id.dev.birifqa.edcgold.request;

public class RequestPostTentang {
    private String tentang;

    public RequestPostTentang() {
    }

    public RequestPostTentang(String tentang) {
        this.tentang = tentang;
    }

    public String getTentang() {
        return tentang;
    }

    public void setTentang(String tentang) {
        this.tentang = tentang;
    }
}
