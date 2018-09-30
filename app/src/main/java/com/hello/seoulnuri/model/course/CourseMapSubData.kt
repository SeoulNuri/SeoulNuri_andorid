package com.hello.seoulnuri.model.course

import java.io.Serializable

/**
 * Created by shineeseo on 2018. 9. 27..
 */
data class CourseMapSubData (
        var tour_idx : Int,
        var lat : Double,
        var lang : Double,
        var img : String,
        var addr : String,
        var title : String
) :Serializable