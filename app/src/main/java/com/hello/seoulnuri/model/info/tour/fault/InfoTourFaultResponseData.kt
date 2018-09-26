package com.hello.seoulnuri.model.info.tour.fault

import com.hello.seoulnuri.model.info.tour.TourCommonData

/**
 * Created by VictoryWoo
 */
data class InfoTourFaultResponseData (
        var tour_common : TourCommonData,
        var tour_bottom : TourBottom,
        var common : CommonData,
        var visual : VisualData,
        var hearing : HearingData,
        var physical : PhysicalData,
        var older : OlderData
)

data class CommonData(
        var parking: String,
        var transportation :String,
        var road : String,
        var entrance : String,
        var toilet :String,
        var infodesk : String
)

data class VisualData(
        var block : String,
        var promotion:String,
        var dog:String,
        var audio:String,
        var guide : String,
        var plate:String
)

data class HearingData(
        var signlang:String,
        var subtitle:String
)
data class PhysicalData(
        var wheelchair:String,
        var elevator:String,
        var seat:String
)
data class OlderData(
        var wheelchair:String,
        var elevator:String
)
