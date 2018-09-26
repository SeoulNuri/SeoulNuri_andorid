package com.hello.seoulnuri.model.mypage

import java.util.*

data class MypageBookmarkCourseData(
        var course_star : Double,
        var course_star_count : Int,
        var course_name : String,
        var course_image : String,
        var course_idx : Int
)