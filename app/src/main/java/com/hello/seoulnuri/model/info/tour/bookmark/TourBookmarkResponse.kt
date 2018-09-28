package com.hello.seoulnuri.model.info.tour.bookmark

import com.hello.seoulnuri.base.BaseModel

/**
 * Created by VictoryWoo
 */
data class TourBookmarkResponse (
        var data : IsBooked
) : BaseModel()


data class IsBooked(
        var isBooked : Int
)