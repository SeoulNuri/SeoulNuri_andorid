package com.hello.seoulnuri.model.course

import com.hello.seoulnuri.base.BaseModel
import java.util.*

/**
 * Created by shineeseo on 2018. 9. 26..
 */
data class CourseBookmarkResponse (
        var data : ArrayList<CourseBookmarkData>
): BaseModel()
