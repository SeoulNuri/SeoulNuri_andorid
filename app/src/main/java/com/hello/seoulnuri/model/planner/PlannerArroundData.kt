package com.hello.seoulnuri.model.planner

data class PlannerArroundData (
        var tour_idx: Int,
        var tour_name: String,
        var tour_latitude: Double,
        var tour_longitude: Double,
        var tour_addr: String,
        var tour_star: Double,
        var tour_star_count: Int
)