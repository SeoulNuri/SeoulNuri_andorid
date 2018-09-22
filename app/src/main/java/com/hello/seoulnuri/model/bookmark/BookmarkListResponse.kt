package com.hello.seoulnuri.model.bookmark

import com.hello.seoulnuri.base.BaseModel
import java.util.*

/**
 * Created by VictoryWoo
 */
data class BookmarkListResponse (
        var data : ArrayList<BookmarkListData>
) : BaseModel()

