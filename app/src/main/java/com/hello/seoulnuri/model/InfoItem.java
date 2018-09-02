package com.hello.seoulnuri.model;

public class InfoItem {

    int image; //image
    double star; //star point
    int cmt_cnt; //info
    String title; // title

    public InfoItem(int image, double star, int cmt_cnt, String title) {
        this.image = image;
        this.star = star;
        this.cmt_cnt = cmt_cnt;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public int getCmt_cnt() {
        return cmt_cnt;
    }

    public void setCmt_cnt(int cmt_cnt) {
        this.cmt_cnt = cmt_cnt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}