package com.hello.seoulnuri;

import java.util.ArrayList;

/**
 * Created by shineeseo on 2018. 8. 29..
 */

public class Position {

    //Properties of Position
    public String position;
    public String image;
    public ArrayList<String> courses = new ArrayList<String>();

    public Position(String position){
        this.position = position;
    }

    public String toString () {
        return position;
    }

}
