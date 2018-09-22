package com.hello.seoulnuri.model.main

import java.util.*

/**
 * Created by VictoryWoo
 */
data class MainRecoTourData(
        var reco_tour : ArrayList<TourData>,
        var rand_tour : TourData
)

data class TourData(
        var tour_idx : Int,
        var handi_type : Int,
        var tour_name : String,
        var tour_addr : String,
        var tour_info : String,
        var tour_star : Double,
        var tour_star_count : Int
)
