package com.hello.seoulnuri.model.search.filter

/**
 * Created by VictoryWoo
 */
data class FilterData (
        var filter_type : String,
        var filter_detail : String,
        var filter_status : Boolean,
        var filter_value : Int
)