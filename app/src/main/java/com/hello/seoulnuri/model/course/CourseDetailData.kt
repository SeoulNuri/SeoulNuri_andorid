package com.hello.seoulnuri.model.course

import java.util.*;
/**
 * Created by shineeseo on 2018. 9. 22..
 */
data class CourseDetailData (
        var course_idx : Int,
        var course_name : String,
        var course_schedule : ArrayList<TourInfo>,
        var course_theme : Int
)