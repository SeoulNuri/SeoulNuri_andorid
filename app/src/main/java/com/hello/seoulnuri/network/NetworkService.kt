package com.hello.seoulnuri.network

import com.hello.seoulnuri.base.BaseModel
import com.hello.seoulnuri.model.bookmark.BookmarkListResponse
import com.hello.seoulnuri.model.course.*
import com.hello.seoulnuri.model.info.tour.InfoTourResponse
import com.hello.seoulnuri.model.info.reservation.InfoTourReservation
import com.hello.seoulnuri.model.info.tour.bookmark.TourIndex
import com.hello.seoulnuri.model.info.tour.fault.InfoTourFaultResponse
import com.hello.seoulnuri.model.info.tour.introduce.InfoTourIntroduce
import com.hello.seoulnuri.model.info.tour.use.InfoTourUseReponse
import com.hello.seoulnuri.model.login.LoginCategoryRequest
import com.hello.seoulnuri.model.login.LoginUserData
import com.hello.seoulnuri.model.login.LoginUserResponse
import com.hello.seoulnuri.model.main.MainTourResponse
import com.hello.seoulnuri.model.map.DirectionResults
import com.hello.seoulnuri.model.mypage.MypageBookmarkCourseResponse
import com.hello.seoulnuri.model.mypage.MypageBookmarkTourResponse
import com.hello.seoulnuri.model.planner.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*


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
    fun getCourseStar(@Header("token") token : String) : Call<CourseStarResponse>

    // 9. 코스별 댓글 불러오기
    @GET("api/course/comment")
    fun getCourseCmt(
            @Header("token") token : String,
            @Query("course_idx") course_idx : Int
    ) : Call<CourseCmtResponse>

    //코스 상세정보 가져오기
    @GET("api/course/detail")
    fun getCourseDetail(
            @Header("token") token : String,
            @Query ("course_theme") course_theme : Int
    ) : Call<CourseDetailResponse>

    //코스별 댓글 추가하기
    @POST("api/course/comment")
    fun postCourseCmt(
            @Header("token") token : String,
            @Body courseCmtRequest: CourseCmtRequest
    ) : Call<BaseModel>

    @POST("api/course/star")
    fun postCourseStarData()

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


    //플래너 리스트 보여주기
    @GET("api/planner/list")
    fun getPlanner(
            @Header("token") token: String
    ) : Call<PlannerGetResponse>

    // 13. 인포 들어갔을 때
    @GET("api/info/tour")
    fun getInfoTour(
            @Header("token") token: String,
            @Query("handi_type") handi_type : String,
            @Query("filter") filter : String
    ) : Call<InfoTourResponse>


    // 14. 인포에서 숙박정보
    @GET("api/info/lodge")
    fun getInfoReservation(
            @Header("token") token: String
    ) : Call<InfoTourReservation>

    // 15. 인포 관광 정보 소개 탭
    @GET("api/info/tour/detail-introduction")
    fun getInfoTourIntroduce(
            @Header("token") token: String,
            @Query("tour_idx") tour_idx : Int
    ) : Call<InfoTourIntroduce>


    // 16. 인포 관광 정보 이용방법 탭
    @GET("api/info/tour/detail-method")
    fun getInfoTourUseMethod(
            @Header("token") token: String,
            @Query("tour_idx") tour_idx: Int
    ) : Call<InfoTourUseReponse>

    // 17. 인포 관광 정보 무장애 정보 탭
    @GET("api/info/tour/detail-barrier-free")
    fun getInfoTourFault(
            @Header("token") token : String,
            @Query("tour_idx") tour_idx: Int
    ) : Call<InfoTourFaultResponse>

    //코스 즐겨찾기 등록
    @POST("api/course/bookmark")
    fun registCourseBookmark(
            @Header("token") token : String,
            @Body course_idx: Int
    ): Call<BaseModel>

    //마이페이지에서 코스 즐겨찾기 리스트 가져오기
    @GET("/api/mypage/bookmark/course")
    fun getCourseBookmarks(
            @Header("token") token : String
    ) : Call<CourseBookmarkResponse>

    // 18. 마이페이지에서 코스 즐겨찾기 보여주기
    @GET("api/mypage/bookmark/tour")
    fun getMypageBookmarkTour(
            @Header("token") token : String
    ) : Call<MypageBookmarkTourResponse>

    // 18. 마이페이지에서 관광 즐겨찾기 보여주기
    @GET("api/mypage/bookmark/course")
    fun getMypageBookmarkCourse(
            @Header("token") token : String
    ) : Call<MypageBookmarkCourseResponse>

    //19. 코스 별점 수정
    @POST("api/course/star")
    fun postCourseStar(
            @Header("token") token : String,
            @Body courseStarModi : CourseStarModify
    ) :Call<BaseModel>


    //19. 코스 별점 수정
    @POST("api/course/star")
    fun postCourseStar(
            @Header("token") token : String,
            @Body courseStarModi : CourseStarModify
    ) :Call<BaseModel>


    // 19. 투어 북마크 등록
    @POST("api/tour/bookmark")
    fun postBookmark(
            @Header("token") token: String,
            @Body tour_idx : TourIndex
    ) : Call<BaseModel>

}