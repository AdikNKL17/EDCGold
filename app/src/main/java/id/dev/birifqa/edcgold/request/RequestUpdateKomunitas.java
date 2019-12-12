package id.dev.birifqa.edcgold.request;

public class RequestUpdateKomunitas {
    private String name;
    private String ketua;
    private String alamat;

    public RequestUpdateKomunitas() {
    }

    public RequestUpdateKomunitas(String name, String ketua, String alamat) {
        this.name = name;
        this.ketua = ketua;
        this.alamat = alamat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKetua() {
        return ketua;
    }

    public void setKetua(String ketua) {
        this.ketua = ketua;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
