package com.hello.seoulnuri.model.course

import java.io.Serializable
import java.util.*;
/**
 * Created by shineeseo on 2018. 9. 27..
 */
data class CourseMapData (
     var course_idx : Int,
     var detailData : ArrayList<CourseMapSubData>

) : Serializable