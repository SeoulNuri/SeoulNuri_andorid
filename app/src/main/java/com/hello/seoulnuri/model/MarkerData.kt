package com.hello.seoulnuri.model

/**
 * Created by VictoryWoo
 */
data class MarkerData(
        var latitude : Double,
        var longitude : Double,
        var title : String,
        var snippet : String,
        var iconResID : Int,
        var tour_idx: Int
)
