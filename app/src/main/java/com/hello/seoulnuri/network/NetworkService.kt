package com.hello.seoulnuri.network

import com.hello.seoulnuri.model.course.CourseStarResponse
import com.hello.seoulnuri.model.login.LoginUserData
import com.hello.seoulnuri.model.login.LoginUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by VictoryWoo
 */
interface NetworkService {
    // 통신에 사용할 함수를 정의

    // 1. 회원가입
    @POST("api/user/signup")
    fun signUp(
            @Body loginUserData: LoginUserData
    ) : Call<LoginUserResponse>

    @GET("api/course")
    fun getCourseStar(
    ) : Call<CourseStarResponse>
}