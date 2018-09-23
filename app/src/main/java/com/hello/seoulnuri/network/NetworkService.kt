package com.hello.seoulnuri.network

import com.hello.seoulnuri.base.BaseModel
import com.hello.seoulnuri.model.bookmark.BookmarkListResponse
import com.hello.seoulnuri.model.course.CourseCmtResponse
import com.hello.seoulnuri.model.course.CourseStarResponse
import com.hello.seoulnuri.model.info.InfoTourResponse
import com.hello.seoulnuri.model.login.LoginCategoryRequest
import com.hello.seoulnuri.model.login.LoginUserData
import com.hello.seoulnuri.model.login.LoginUserResponse
import com.hello.seoulnuri.model.main.MainTourResponse
import com.hello.seoulnuri.model.map.DirectionResults
import com.hello.seoulnuri.model.planner.*
import com.hello.seoulnuri.model.search.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*
import java.util.*


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
    @GET("api/main/search/keyword")
    fun getMainSearchData(
            @Header("token") token : String,
            @Query("word") word : String
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

    // 7. google map directions
    @GET("/maps/api/directions/json")
    fun getJson(
            @Query("origin") origin : String,
            @Query("destination") destination : String,
            @Query("waypoints") waypoints : String
    ) : Callback<DirectionResults>

    // 8. 코스 메인 유형별 별점 불러오기
    @GET("api/course")
    fun getCourseStar() : Call<CourseStarResponse>

    // 9. 코스별 댓글 불러오기
    @GET("api/course/comment")
    fun getCourseCmt(
            @Query("course_idx") course_idx : Int
    ) : Call<CourseCmtResponse>

    @POST("api/course/comment")
    fun postCourseCmt() //토큰을 보내야 하는데 어떻게 해야하는걸까..ㅜ


    // 10.플래너 삭제
    @DELETE("api/planner/cancel")
    fun deletePlanner(
            @Header("token") token: String,
            @Body plannerDeleteRequest: PlannerDeleteRequest
    ) : Call<PlannerDeleteResponse>

    // 11. 플래너 검색
    @GET("api/planner/search/keyword")
    fun plannerSearch(
            @Header("token") token: String,
            @Body plannerSearchRequest: PlannerSearchRequest
    ) : Call<PlannerSearchResponse>

    // 12. 플래너 추가
    @POST("api/planner/add")
    fun addPlanner(
            @Header("token") token: String,
            @Body plannerAddRequest: PlannerAddRequest
    ) : Call<PlannerAddResponse>


    // 13. 인포 들어갔을 때
    @GET("api/info/tour")
    fun getInfoTour(
            @Header("token") token: String,
            @Query("handi_type") handi_type : String,
            @Query("filter") filter : String
    ) : Call<InfoTourResponse>
}