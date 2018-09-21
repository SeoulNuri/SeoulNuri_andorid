package com.hello.seoulnuri.network

import com.hello.seoulnuri.base.BaseModel
import com.hello.seoulnuri.model.bookmark.BookmarkListResponse
import com.hello.seoulnuri.model.course.CourseCmtResponse
import com.hello.seoulnuri.model.login.LoginCategoryRequest
import com.hello.seoulnuri.model.login.LoginUserData
import com.hello.seoulnuri.model.login.LoginUserResponse
import com.hello.seoulnuri.model.main.MainTourResponse
import com.hello.seoulnuri.model.search.SearchResponse
import retrofit2.Call
import retrofit2.http.*
import com.hello.seoulnuri.model.course.CourseStarResponse


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

    // 4. 메인에서 Search - 0
    @GET("api/main/search/keyword/{word}")
    fun getMainSearchData(
            @Header("token") token : String,
            @Path("word") word : String
    ) : Call<BookmarkListResponse>

    // 5. 마이 페이지에서 장애 유형 변경 - 0
    @POST("api/mypage")
    fun changeType(
            @Header("token") token : String,
            @Body changedType: LoginCategoryRequest
    ) : Call<BaseModel>

    // 6. 즐겨찾기 리스트 불러오기 - 0
    @GET("api/mypage/bookmark/tour")
    fun getBookmarkList(
            @Header("token") token: String
    ) : Call<BookmarkListResponse>

    //코스 메인 유형별 별점 불러오기
    @GET("api/course")
    fun getCourseStar() : Call<CourseStarResponse>

    //코스별 댓글 불러오기
    @GET("api/course/comment")
    fun getCourseCmt(
            @Query("course_idx") course_idx : Int
    ) : Call<CourseCmtResponse>

    @POST("api/course/comment")
    fun postCourseCmt() //토큰을 보내야 하는데 어떻게 해야하는걸까..ㅜ
}