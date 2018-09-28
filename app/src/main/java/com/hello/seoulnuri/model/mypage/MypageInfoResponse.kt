package com.hello.seoulnuri.model.mypage

import com.hello.seoulnuri.base.BaseModel
import com.hello.seoulnuri.model.info.tour.InfoTourResponseData
import java.util.*

data class MypageInfoResponse (
        var data : ArrayList<MypageInfoData>
) : BaseModel()