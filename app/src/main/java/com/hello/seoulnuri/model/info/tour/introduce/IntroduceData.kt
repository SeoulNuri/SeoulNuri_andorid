package com.hello.seoulnuri.model.info.tour.introduce

import com.hello.seoulnuri.model.info.tour.TourCommonData

/**
 * Created by VictoryWoo
 */
data class IntroduceData(
        var tour_common: TourCommonData,
        var tour_bottom: TourBottomData
)


data class TourBottomData(
        var tour_image : String,
        var tour_info_detail : String
)
