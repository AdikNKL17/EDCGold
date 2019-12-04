package id.dev.birifqa.edcgold.model.admin;

public class AdminListFaqModel {
    private String title;
    private String nama;
    private String date;

    public AdminListFaqModel() {
    }

    public AdminListFaqModel(String title, String nama, String date) {
        this.title = title;
        this.nama = nama;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
