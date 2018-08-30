package com.hello.seoulnuri;

/**
 * Created by shineeseo on 2018. 8. 21..
 */

public class CourseItem {
    int image;
    int icon;
    String content;

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
}
