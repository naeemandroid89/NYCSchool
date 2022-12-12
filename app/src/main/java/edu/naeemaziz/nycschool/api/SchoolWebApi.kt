package edu.naeemaziz.nycschool.api

import edu.naeemaziz.nycschool.model.SatScoreResponse
import edu.naeemaziz.nycschool.model.SchoolListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SchoolWebApi {

    @GET("s3k6-pzi2.json")
    fun getSchoolList(): Call<List<SchoolListResponse>>

    @GET("f9bf-2cp4.json")
    fun getSatScoreDetail(@Query("dbn") dbn: String): Call<List<SatScoreResponse>>

}