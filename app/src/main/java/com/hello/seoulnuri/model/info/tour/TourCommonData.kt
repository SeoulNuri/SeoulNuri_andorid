package com.hello.seoulnuri.model.info.tour

/**
 * Created by VictoryWoo
 */
data class TourCommonData(
        var tour_idx: Int,
        var tour_booked: Int,
        var tour_name: String,
        var tour_image : String,
        var tour_star: Int,
        var tour_star_count: Int,
        var tour_addr : String
)
