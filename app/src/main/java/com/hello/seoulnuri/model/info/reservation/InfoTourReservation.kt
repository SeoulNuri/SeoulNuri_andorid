package com.hello.seoulnuri.model.info.reservation

import com.hello.seoulnuri.base.BaseModel
import java.util.*

/**
 * Created by VictoryWoo
 */
data class InfoTourReservation (
        var data : ArrayList<InfoTourReservationData>
) : BaseModel()

