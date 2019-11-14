package id.dev.birifqa.edcgold.request;

public class RequestChangeUsername {
    private String name;

    public RequestChangeUsername() {
    }

    public RequestChangeUsername(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
