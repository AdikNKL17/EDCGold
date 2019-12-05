package id.dev.birifqa.edcgold.request;

public class RequestTopupProcess {
    private String status;

    public RequestTopupProcess() {
    }

    public RequestTopupProcess(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
