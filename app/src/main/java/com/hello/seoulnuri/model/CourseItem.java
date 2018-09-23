package com.hello.seoulnuri.model;

import java.io.Serializable;

/**
 * Created by shineeseo on 2018. 8. 21..
 */

public class CourseItem implements Serializable{
    int image;
    int icon;
    String content;
    double cour_star;
    double cour_star_count;

    public CourseItem(int image, int icon, String content, double cour_star, double cour_star_count) {
        this.image = image;
        this.icon = icon;
        this.content = content;
        this.cour_star = cour_star;
        this.cour_star_count = cour_star_count;
    }

    public CourseItem(int image, int icon, String content) {
        this.image = image;
        this.icon = icon;
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getCour_star() {
        return cour_star;
    }

    public void setCour_star(double cour_star) {
        this.cour_star = cour_star;
    }

    public double getCour_star_count() {
        return cour_star_count;
    }

    public void setCour_star_count(double cour_star_count) {
        this.cour_star_count = cour_star_count;
    }

    @Override
    public String toString() {
        return "CourseItem{" +
                "image=" + image +
                ", icon=" + icon +
                ", content='" + content + '\'' +
                ", cour_star=" + cour_star +
                ", cour_star_count=" + cour_star_count +
                '}';
    }
}
