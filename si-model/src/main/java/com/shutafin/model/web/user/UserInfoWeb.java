package com.shutafin.model.web.user;


import com.shutafin.model.web.DataResponse;

public class UserInfoWeb implements DataResponse{

    private Long userId;

    private int cityId;
    private int genderId;

    private String facebookLink;
    private String profession;
    private String company;
    private String phoneNumber;

    public UserInfoWeb() {
    }

    public UserInfoWeb(Long userId, int cityId, int genderId, String facebookLink, String profession, String company, String phoneNumber) {
        this.userId = userId;
        this.cityId = cityId;
        this.genderId = genderId;
        this.facebookLink = facebookLink;
        this.profession = profession;
        this.company = company;
        this.phoneNumber = phoneNumber;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
