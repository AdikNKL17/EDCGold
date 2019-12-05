package id.dev.birifqa.edcgold.request;

public class RequestRentalProcess {
    private String status;

    public RequestRentalProcess() {
    }

    public RequestRentalProcess(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
