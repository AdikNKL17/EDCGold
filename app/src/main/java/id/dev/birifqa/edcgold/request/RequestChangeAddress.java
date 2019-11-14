package id.dev.birifqa.edcgold.request;

public class RequestChangeAddress {
    private String country;
    private String region;
    private String regency;
    private String district;
    private String address;
    private String postcode;

    public RequestChangeAddress() {
    }

    public RequestChangeAddress(String country, String region, String regency, String district, String address, String postcode) {
        this.country = country;
        this.region = region;
        this.regency = regency;
        this.district = district;
        this.address = address;
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegency() {
        return regency;
    }

    public void setRegency(String regency) {
        this.regency = regency;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
