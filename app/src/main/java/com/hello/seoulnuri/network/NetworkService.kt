package com.hello.seoulnuri.network

import com.hello.seoulnuri.base.BaseModel
import com.hello.seoulnuri.model.login.LoginCategoryRequest
import com.hello.seoulnuri.model.login.LoginUserData
import com.hello.seoulnuri.model.login.LoginUserResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.GET
import com.hello.seoulnuri.model.course.CourseStarResponse

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


    // 2. 로그인 카테고리 선택
    @POST("api/user")
    fun selectCategory(
            @Header("token") token : String,
            @Body loginCategoryRequest: LoginCategoryRequest
    ) : Call<BaseModel>

    @GET("api/course")
    fun getCourseStar(
    ) : Call<CourseStarResponse>

}