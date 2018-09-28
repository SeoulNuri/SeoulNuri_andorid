package com.hello.seoulnuri.model.info.tour.fault

/**
 * Created by VictoryWoo
 */
data class TourBottom (
        var accessibility : Accessibility,
        var common : CommonData,
        var visual : VisualData,
        var hearing : HearingData,
        var physical : PhysicalData,
        var older : OlderData
)

data class Accessibility(
        var visual : String,
        var hearing : String,
        var physical : String,
        var older : String
)

