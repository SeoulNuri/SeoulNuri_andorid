package com.hello.seoulnuri.model.mypage

import java.util.*

data class MypageBookmarkCourseData(
        var bookmark_course : ArrayList<CourseData>
)

data class CourseData(
        var tour_idx : Int,
        var handi_type : Int,
        var tour_name : String,
        var tour_addr : String,
        var tour_info : String,
        var tour_image : String,
        var tour_star : Double,
        var tour_star_count : Int
)