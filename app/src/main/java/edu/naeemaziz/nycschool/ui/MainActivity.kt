package edu.naeemaziz.nycschool.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import edu.naeemaziz.nycschool.R
import edu.naeemaziz.nycschool.api.SchoolWebApi
import edu.naeemaziz.nycschool.model.SatScoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //school List https://data.cityofnewyork.us/resource/s3k6-pzi2.json
    //sat score https://data.cityofnewyork.us/resource/f9bf-2cp4.json?dbn=01M292
    //youtube TCCZb7eEYKU

    @Inject
    lateinit var apiRepository: SchoolWebApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*apiRepository.getSatScoreDetail("01M292").enqueue(object : Callback<List<SatScoreResponse>>{
            override fun onResponse(
                call: Call<List<SatScoreResponse>>,
                response: Response<List<SatScoreResponse>>
            ) {
                val i: Int = 0
            }

            override fun onFailure(call: Call<List<SatScoreResponse>>, t: Throwable) {
                val i: Int = 0
            }

        })*/
    }
}