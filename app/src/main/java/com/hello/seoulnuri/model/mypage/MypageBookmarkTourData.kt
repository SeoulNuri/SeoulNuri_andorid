package com.hello.seoulnuri.model.mypage

import java.util.*

data class MypageBookmarkTourData(
        var tour_star : Double,
        var tour_star_count : Int,
        var tour_name : String,
        var tour_image : String,
        var tour_idx : Int
)