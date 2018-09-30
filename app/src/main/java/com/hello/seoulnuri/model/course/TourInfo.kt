package com.hello.seoulnuri.model.course

/**
 * Created by shineeseo on 2018. 9. 22..
 */
data class TourInfo (
        var tour_idx : Int,
        var tour_name : String,
        var tour_addr : String,
        var tour_info : String,
        var tour_info_detail : String,
        var tour_info_img : String,
        var tour_planner_img : String,
        var tour_latitude : Double,
        var tour_longitude : Double
)