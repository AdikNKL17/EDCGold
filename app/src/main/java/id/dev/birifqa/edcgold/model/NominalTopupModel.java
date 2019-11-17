package id.dev.birifqa.edcgold.model;

public class NominalTopupModel {
    private String id;
    private String label;
    private String nominal;
    private String created_at;
    private String updated_at;

    public NominalTopupModel() {
    }

    public NominalTopupModel(String id, String label, String nominal) {
        this.id = id;
        this.label = label;
        this.nominal = nominal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
