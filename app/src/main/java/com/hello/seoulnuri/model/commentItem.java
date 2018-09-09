package com.hello.seoulnuri;

import java.util.Date;

public class commentItem {

    String nickname;
    String say;
    Date date;

    public commentItem(String nickname, String say, Date date) {
        this.nickname = nickname;
        this.say = say;
        this.date = date;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSay() {
        return say;
    }

    public void setSay(String say) {
        this.say = say;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
