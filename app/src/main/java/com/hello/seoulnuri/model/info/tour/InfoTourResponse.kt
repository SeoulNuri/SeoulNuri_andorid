package com.hello.seoulnuri.model.info.tour

import com.hello.seoulnuri.base.BaseModel
import java.util.*

/**
 * Created by VictoryWoo
 */
data class InfoTourResponse (
        var data : ArrayList<InfoTourResponseData>
) : BaseModel()

