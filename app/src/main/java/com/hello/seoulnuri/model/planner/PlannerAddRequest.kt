package com.hello.seoulnuri.model.planner

import java.util.*

data class PlannerAddRequest (
        var plan_date: String,
        var tour_idx: ArrayList<Int>
)