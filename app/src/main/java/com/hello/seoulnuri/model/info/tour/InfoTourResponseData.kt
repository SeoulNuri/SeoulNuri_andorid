package com.hello.seoulnuri.model.info.tour

/**
 * Created by VictoryWoo
 */
data class InfoTourResponseData(
        var tour_idx: Int,
        var tour_name: String,
        var tour_card_img: String,
        var tour_star: Double,
        var tour_star_count: Int,
        var tour_booked: Int

)

