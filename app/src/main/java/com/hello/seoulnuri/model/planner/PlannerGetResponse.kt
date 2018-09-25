package com.hello.seoulnuri.model.planner

import java.util.*

data class PlannerGetResponse (
        var message: String,
        var data: ArrayList<PlannerGetData>
)