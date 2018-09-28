package com.hello.seoulnuri.model.mypage

import java.util.*

data class MypageInfoData(
        var user_idx : Int,
        var kakao_idx : String,
        var user_age : Int,
        var user_birth : String,
        var user_nickname : String,
        var user_token : String,
        var handi_type : String
)