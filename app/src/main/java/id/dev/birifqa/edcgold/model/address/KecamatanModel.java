package id.dev.birifqa.edcgold.model.address;

public class KecamatanModel {
    private String id;
    private String regency_id;
    private String name;
    private String created_at;
    private String updated_at;

    public KecamatanModel() {
    }

    public KecamatanModel(String id, String regency_id, String name, String created_at, String updated_at) {
        this.id = id;
        this.regency_id = regency_id;
        this.name = name;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegency_id() {
        return regency_id;
    }

    public void setRegency_id(String regency_id) {
        this.regency_id = regency_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
