package com.hello.seoulnuri.model.planner

import com.hello.seoulnuri.base.BaseModel

/**
 * Created by VictoryWoo
 */
data class PlannerImageResponse (
        var data : PlannerImageData
) : BaseModel()

data class PlannerImageData(
        var tour_idx : Int,
        var tour_planner_img : String
)