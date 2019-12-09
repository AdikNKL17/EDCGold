package id.dev.birifqa.edcgold.model.admin;

public class AdminUserModel {
    private String id;
    private String userId;
    private String name;
    private String lastname;
    private String email;
    private String avatar;
    private String gender;
    private String bod;
    private String phone;
    private String countries_id;
    private String regions_id;
    private String regencies_id;
    private String districts_id;
    private String postcode;
    private String address;
    private String reason_close;
    private String status_active;
    private String status_topup;
    private String type_member;
    private String created_at;
    private String updated_at;


    public AdminUserModel() {
    }

    public AdminUserModel(String id, String userId, String name, String lastname, String email, String avatar, String gender, String bod, String phone, String countries_id, String regions_id, String regencies_id, String districts_id, String postcode, String address, String reason_close, String status_active, String status_topup, String type_member, String created_at, String updated_at) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.avatar = avatar;
        this.gender = gender;
        this.bod = bod;
        this.phone = phone;
        this.countries_id = countries_id;
        this.regions_id = regions_id;
        this.regencies_id = regencies_id;
        this.districts_id = districts_id;
        this.postcode = postcode;
        this.address = address;
        this.reason_close = reason_close;
        this.status_active = status_active;
        this.status_topup = status_topup;
        this.type_member = type_member;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBod() {
        return bod;
    }

    public void setBod(String bod) {
        this.bod = bod;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountries_id() {
        return countries_id;
    }

    public void setCountries_id(String countries_id) {
        this.countries_id = countries_id;
    }

    public String getRegions_id() {
        return regions_id;
    }

    public void setRegions_id(String regions_id) {
        this.regions_id = regions_id;
    }

    public String getRegencies_id() {
        return regencies_id;
    }

    public void setRegencies_id(String regencies_id) {
        this.regencies_id = regencies_id;
    }

    public String getDistricts_id() {
        return districts_id;
    }

    public void setDistricts_id(String districts_id) {
        this.districts_id = districts_id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReason_close() {
        return reason_close;
    }

    public void setReason_close(String reason_close) {
        this.reason_close = reason_close;
    }

    public String getStatus_active() {
        return status_active;
    }

    public void setStatus_active(String status_active) {
        this.status_active = status_active;
    }

    public String getStatus_topup() {
        return status_topup;
    }

    public void setStatus_topup(String status_topup) {
        this.status_topup = status_topup;
    }

    public String getType_member() {
        return type_member;
    }

    public void setType_member(String type_member) {
        this.type_member = type_member;
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
