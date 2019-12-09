package id.dev.birifqa.edcgold.request;

public class RequestUpdateTopup {
    private String label;
    private String nominal;

    public RequestUpdateTopup() {
    }

    public RequestUpdateTopup(String label, String nominal) {
        this.label = label;
        this.nominal = nominal;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }
}
