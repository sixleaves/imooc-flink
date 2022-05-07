package com.imooc.flink.dw.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Access {

    public Access(String deviceType, String uid, Product product, String os, String ip, Integer nu, String channel, Long time, String event, String net, String device, String version) {
        this.deviceType = deviceType;
        this.uid = uid;
        this.product = product;
        this.os = os;
        this.setIp(ip);
        this.nu = nu;
        Channel = channel;
        this.time = time;
        this.event = event;
        this.net = net;
        this.device = device;
        this.version = version;
    }

    public Access() {
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;

    }

    public Integer getNu() {
        return nu;
    }

    public void setNu(Integer nu) {
        this.nu = nu;
    }

    public String getChannel() {
        return Channel;
    }

    public void setChannel(String channel) {
        Channel = channel;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Access{" +
                "deviceType='" + deviceType + '\'' +
                ", uid='" + uid + '\'' +
                ", product=" + product +
                ", os='" + os + '\'' +
                ", ip='" + ip + '\'' +
                ", nu=" + nu +
                ", Channel='" + Channel + '\'' +
                ", time=" + time +
                ", event='" + event + '\'' +
                ", net='" + net + '\'' +
                ", device='" + device + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    public String deviceType;
    public String uid;
    public Product product ;
    public String os;
    public String ip;
    public Integer nu;
    public String Channel;
    public Long time;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String event;
    public String net;
    public String device;
    public String version;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        if (null == province) province = "其他";
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (null == city) city = "其他";
        this.city = city;
    }

    public String province;
    public String city;
}
