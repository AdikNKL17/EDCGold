package id.dev.birifqa.edcgold.model;

public class KabupatenModel {
    private String id;
    private String region_id;
    private String name;
    private String created_at;
    private String updated_at;

    public KabupatenModel() {
    }

    public KabupatenModel(String id, String region_id, String name, String created_at, String updated_at) {
        this.id = id;
        this.region_id = region_id;
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

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
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
