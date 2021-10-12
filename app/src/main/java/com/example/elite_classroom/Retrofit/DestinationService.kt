package com.example.elite_classroom.Retrofit

import com.example.elite_classroom.Models.Recycler_Models.Class_Fixtures
import com.example.elite_classroom.Models.Retrofit_Models.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface DestinationService {


    @POST("users/login")
    fun login_Google_User(@Body Google_Logins: Google_Logins): Call<Auth_Responses>

    @GET("getClasses/classes/{google_token}")
    fun get_Classes(@Path("google_token") google_token: String): Call<Get_Classes_Response>

    @Multipart
    @POST("storage/upload")
    fun uploadFile(@Part file: MultipartBody.Part?): Call<Upload_Response?>?

    @GET("weekly-calender/getCalender-thisWeek/{google_token}")
    fun current_calender(@Path("google_token") google_token: String): Call<ArrayList<Class_Fixtures>>

    @GET("weekly-calender/getCalender-nextWeek/{google_token}")
    fun next_calender(@Path("google_token") google_token: String): Call<ArrayList<Class_Fixtures>>

    @POST("work/create/submit")
    fun submit_assignment(@Body Submit_Assignment : Submit_Assignment) : Call<Submission_Response>

    @POST("calender/scheduleClass")
    fun schedule_Class(@Body Schedule_Class_Request : Schedule_Class_Request) :Call<Schedule_Class_Response>

    @GET("work/read/work/{workid}/{uid}")
    fun get_student_submission(@Path("workid") workid : Int , @Path("uid") uid : String) : Call<ArrayList<Student_Submission_Response>>

    @DELETE("work/delete/{sid}")
    fun delete_submission(@Path("sid") sid : Int) : Call<Delete_Submission_Response>

    @PUT("calender/rescheduleClass")
    fun  reschedule_Class(@Body  Reschedule_Class : Reschedule_Class) : Call<Reschedule_Response>

    @DELETE("calender/cancelClass")
    fun cancel_Class(@Body Cancel_Class_Request : Cancel_Class_Request) : Call<Reschedule_Response>

    @GET("work/read/work/{workid}")
    fun get_student_submissions(@Path("workid") workid: Int) : Call<ArrayList<Student_Submissions_Record>>

}