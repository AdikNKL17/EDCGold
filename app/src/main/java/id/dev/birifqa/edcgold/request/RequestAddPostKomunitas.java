package id.dev.birifqa.edcgold.request;

public class RequestAddPostKomunitas {
    private String komunitas_id;
    private String title;
    private String content;
    private String images;

    public RequestAddPostKomunitas() {
    }

    public RequestAddPostKomunitas(String komunitas_id, String title, String content, String images) {
        this.komunitas_id = komunitas_id;
        this.title = title;
        this.content = content;
        this.images = images;
    }

    public String getKomunitas_id() {
        return komunitas_id;
    }

    public void setKomunitas_id(String komunitas_id) {
        this.komunitas_id = komunitas_id;
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
}
