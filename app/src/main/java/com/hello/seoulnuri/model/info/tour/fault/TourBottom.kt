package com.hello.seoulnuri.model.info.tour.fault

/**
 * Created by VictoryWoo
 */
data class TourBottom (
        var accessibility : Accessibility
)

data class Accessibility(
        var visual : String,
        var hearing : String,
        var physical : String,
        var older : String
)

