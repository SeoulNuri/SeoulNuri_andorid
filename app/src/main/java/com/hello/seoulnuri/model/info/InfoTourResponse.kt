package com.hello.seoulnuri.model.info

import com.google.gson.annotations.SerializedName
import com.hello.seoulnuri.base.BaseModel
import java.util.*

/**
 * Created by VictoryWoo
 */
data class InfoTourResponse (
        var data : ArrayList<InfoTourResponseData>
) : BaseModel()

