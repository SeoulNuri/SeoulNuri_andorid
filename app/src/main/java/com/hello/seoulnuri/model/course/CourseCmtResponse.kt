package com.hello.seoulnuri.model.course

import com.hello.seoulnuri.base.BaseModel
import java.util.*

/**
 * Created by shineeseo on 2018. 9. 21..
 */
data class CourseCmtResponse (
        var data : ArrayList<CourseCmtData>
) : BaseModel()

