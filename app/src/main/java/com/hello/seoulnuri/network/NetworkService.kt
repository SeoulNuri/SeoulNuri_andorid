package com.hello.seoulnuri.network

import com.hello.seoulnuri.base.BaseModel
import com.hello.seoulnuri.model.login.LoginCategoryRequest
import com.hello.seoulnuri.model.login.LoginUserData
import com.hello.seoulnuri.model.login.LoginUserResponse
import com.hello.seoulnuri.model.main.MainTourResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by VictoryWoo
 */
interface NetworkService {
    // 통신에 사용할 함수를 정의

    // 1. 회원가입 - 0
    @POST("api/user/signup")
    fun signUp(@Body loginUserData: LoginUserData) : Call<LoginUserResponse>



    // 2. 로그인 카테고리 선택 - 0
    @POST("api/user")
    fun selectCategory(
            @Header("token") token : String,
            @Body loginCategoryRequest: LoginCategoryRequest
    ) : Call<BaseModel>


    // 3. 메인에서 정보 받아오기 - 0 , 근데, 서버에서 값을 뿌려 줄 때 조금 이상한 것 같음. 값이 없음
    @GET("api/main")
    fun getMainInfo(
            @Header("token") token : String
    ) : Call<MainTourResponse>

    // 4. 메인에서 Search
    @GET("api/main/search/keyword")
    fun getMainSearchData(
            @Header("token") token : String
    )

    // 5. 마이 페이지에서 장애 유형 변경
    @POST("api/mypage")
    fun changeType(
            @Header("token") token : String,
            @Body changedType: LoginCategoryRequest
    ) : Call<BaseModel>
}