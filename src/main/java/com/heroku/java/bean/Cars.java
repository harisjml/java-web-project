package com.heroku.java.bean;

import java.sql.Date;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class Cars {
    public Integer carid;
    public String cartype;
    public String carname;
    public String condition;
    public double carprice;
    public byte[] carimagebyte;
    public MultipartFile carimage;
    public String imageSrc;
    public String carprice2dp;
    public Date datestart;
    public Date dateend;
    

    public Date getDatestart() {
        return this.datestart;
    }

    public void setDatestart(Date datestart) {
        this.datestart = datestart;
    }

    public Date getDateend() {
        return this.dateend;
    }

    public void setDateend(Date dateend) {
        this.dateend = dateend;
    }

    public Cars(Integer carid, String cartype, String carname, String condition, double carprice, MultipartFile carimage, String imageSrc,String carprice2dp) {
        this.carid = carid;
        this.cartype = cartype;
        this.carname = carname;
        this.condition = condition;
        this.carprice = carprice;
        this.carimage = carimage;
        this.imageSrc = imageSrc;
        this.carprice2dp = carprice2dp;
    }
    

    public String getCarprice2dp() {
        return this.carprice2dp;
    }

    public void setCarprice2dp(String carprice2dp) {
        this.carprice2dp = carprice2dp;
    }

    public Integer getCarid() {
        return this.carid;
    }

    public void setCarid(Integer carid) {
        this.carid = carid;
    }

    public String getCartype() {
        return this.cartype;
    }

    public void setCartype(String cartype) {
        this.cartype = cartype;
    }

    public String getCarname() {
        return this.carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getCarprice() {
        return this.carprice;
    }

    public void setCarprice(double carprice) {
        this.carprice = carprice;
    }

    public MultipartFile getCarimage() {
        return this.carimage;
    }

    public void setCarimage(MultipartFile carimage) {
        this.carimage = carimage;
    }


    public byte[] getCarimagebyte() {
        return this.carimagebyte;
    }

    public void setCarimagebyte(byte[] carimagebyte) {
        this.carimagebyte = carimagebyte;
    }

    //constructor to get imageSrc from file carimage
    // public Cars(int carid, String cartype, String carname, String condition, double carprice, String imageSrc) {
    //     this.carid = carid;
    //     this.cartype = cartype;
    //     this.carname = carname;
    //     this.condition = condition;
    //     this.carprice = carprice;
    //     this.imageSrc = imageSrc;
    // }

    public String getImageSrc() {
        return this.imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

}
