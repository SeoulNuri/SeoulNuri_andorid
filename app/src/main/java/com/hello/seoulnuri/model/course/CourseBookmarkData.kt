package com.hello.seoulnuri.model.course

import com.hello.seoulnuri.base.BaseModel

/**
 * Created by shineeseo on 2018. 9. 26..
 */
data class CourseBookmarkData(
        var course_star : Double,
        var course_star_count : Double,
        var course_name : String,
        var course_image : String,
        var course_idx : Int

)