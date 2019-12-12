package id.dev.birifqa.edcgold.model;

public class KomunitasModel {
    private String id;
    private String slug;
    private String komunitas_id;
    private String komunitas_name;
    private String title;
    private String content;
    private String images;
    private String date;

    public KomunitasModel() {
    }

    public KomunitasModel(String id, String slug, String komunitas_id, String komunitas_name, String title, String content, String images, String date) {
        this.id = id;
        this.slug = slug;
        this.komunitas_id = komunitas_id;
        this.komunitas_name = komunitas_name;
        this.title = title;
        this.content = content;
        this.images = images;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getKomunitas_id() {
        return komunitas_id;
    }

    public void setKomunitas_id(String komunitas_id) {
        this.komunitas_id = komunitas_id;
    }

    public String getKomunitas_name() {
        return komunitas_name;
    }

    public void setKomunitas_name(String komunitas_name) {
        this.komunitas_name = komunitas_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
