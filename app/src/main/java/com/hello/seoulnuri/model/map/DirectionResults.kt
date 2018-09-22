package com.hello.seoulnuri.model.map

import com.google.gson.annotations.SerializedName


/**
 * Created by VictoryWoo
 */
data class DirectionResults(
        @SerializedName("routes")
        val routes: List<Route>? = null
)

data class Route(
        @SerializedName("overview_polyline")
        val overviewPolyLine: OverviewPolyLine? = null,

        val legs: List<Legs>? = null
)

data class Legs(
        val steps: List<Steps>? = null

)


data class Steps(
        val start_location: Location? = null,
        val end_location: Location? = null,
        val polyline: OverviewPolyLine? = null
)

data class OverviewPolyLine(

        @SerializedName("points")
        var points: String? = null
)

data class Location(
        val lat: Double = 0.toDouble(),
        val lng: Double = 0.toDouble()
)