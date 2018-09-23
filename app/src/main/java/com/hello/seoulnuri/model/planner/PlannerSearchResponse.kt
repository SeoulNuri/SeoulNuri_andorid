package com.hello.seoulnuri.model.planner

import java.util.*

data class PlannerSearchResponse (
        var message: String,
        var data: ArrayList<PlannerSearchData>
)