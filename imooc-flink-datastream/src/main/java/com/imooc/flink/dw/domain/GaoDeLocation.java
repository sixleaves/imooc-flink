package com.imooc.flink.dw.domain;

public class GaoDeLocation {
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infoCode) {
        this.infocode = infoCode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public GaoDeLocation() {
    }


    public GaoDeLocation(Integer status, String info, String infoCode, String province, String city, String adcode, String rectangle) {
        this.status = status;
        this.info = info;
        this.infocode = infoCode;
        this.province = province;
        this.city = city;
        this.adcode = adcode;
        this.rectangle = rectangle;
    }

    public Integer status;
    public String info;
    public String infocode;
    public String province;
    public String city;
    public String adcode;
    public String rectangle;

    public String getRectangle() {
        return rectangle;
    }

    public void setRectangle(String rectangle) {
        this.rectangle = rectangle;
    }
}
