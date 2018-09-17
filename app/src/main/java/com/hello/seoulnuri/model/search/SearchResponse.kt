package com.hello.seoulnuri.model.search

import com.hello.seoulnuri.base.BaseModel

/**
 * Created by VictoryWoo
 */
data class SearchResponse(
        var data : ArrayList<SearchTourData>
) : BaseModel()