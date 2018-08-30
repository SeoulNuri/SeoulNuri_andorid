package com.hello.seoulnuri.network

import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by VictoryWoo
 */
interface NetworkService {
    // 통신에 사용할 함수를 정의

    // 1. 회원가입
    @POST("api/user")
    fun signUp(

    )
}