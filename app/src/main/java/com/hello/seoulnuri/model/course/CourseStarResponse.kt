package com.hello.seoulnuri.model.course

import com.hello.seoulnuri.base.BaseModel

/**
 * Created by shineeseo on 2018. 9. 12..
 */
data class CourseStarResponse (
    var data : Map<String, CourseStarData>?
) : BaseModel()