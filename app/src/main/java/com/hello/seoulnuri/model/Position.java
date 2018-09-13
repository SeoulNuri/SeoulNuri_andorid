package com.hello.seoulnuri.model;

import java.util.ArrayList;

/**
 * Created by shineeseo on 2018. 8. 29..
 */

public class Position {

    //Properties of Position
    public String course_info_item;
    public String course_info_image;
    public ArrayList<String> course_info = new ArrayList<String>();

    public Position(String course_info_item){
        this.course_info_item = course_info_item;
    }

    public String toString () {
        return course_info_item;
    }

}
