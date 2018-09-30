package com.hello.seoulnuri.model.main

import java.util.*

/**
 * Created by VictoryWoo
 */
data class MainRecoTourData(
        var reco_tour : ArrayList<TourData2>,
        var rand_tour : TourData
)

data class TourData(
        var tour_idx : Int,
        var tour_name : String,
        var tour_addr : String,
        var tour_info : String,
        var tour_card_img : String,
        var tour_star : Double,
        var tour_star_count : Int
)

data class TourData2(
        var tour_idx : Int,
        var tour_name : String,
        var tour_addr : String,
        var tour_info : String,
        var tour_card_img : String,
        var tour_star : Double,
        var tour_star_count : Int
)