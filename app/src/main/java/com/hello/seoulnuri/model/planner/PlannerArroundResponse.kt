package com.hello.seoulnuri.model.planner

import java.util.*

data class PlannerArroundResponse (
        var message: String,
        var data: ArrayList<PlannerArroundData>
)