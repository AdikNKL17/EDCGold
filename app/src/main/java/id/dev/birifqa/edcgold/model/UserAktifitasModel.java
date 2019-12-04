package id.dev.birifqa.edcgold.model;

public class UserAktifitasModel {
    private String title;
    private String status;
    private String date;
    private String nominal;
    private String tipe;

    public UserAktifitasModel() {
    }

    public UserAktifitasModel(String title, String status, String date, String nominal, String tipe) {
        this.title = title;
        this.status = status;
        this.date = date;
        this.nominal = nominal;
        this.tipe = tipe;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }
}
