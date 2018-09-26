package com.hello.seoulnuri.model.planner

data class PlannerGetData (
        var date_year: String,
        var date_month: String,
        var date_day: String,
        var plan_idx: Int,
        var tour_name: String
)