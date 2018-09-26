package com.hello.seoulnuri.model.info.tour.use

import com.hello.seoulnuri.model.info.tour.TourCommonData

/**
 * Created by VictoryWoo
 */
data class InfoTourUseResponseData (
        var tour_common : TourCommonData,
        var tour_bottom : UseTourBottomData
)

