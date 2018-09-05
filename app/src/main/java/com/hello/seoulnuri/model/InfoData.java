package com.hello.seoulnuri.model;


import java.util.ArrayList;

public class InfoData {

    public String text;
    public String title;
    public int image;

    public ArrayList<String> introInfo = new ArrayList<String>();

    public InfoData(int image, String text){ // 소개 페이지
        this.text = text;
        this.image = image;
    }

    public InfoData(String title, String text){ // 이용안내
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public InfoData(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
