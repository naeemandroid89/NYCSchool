package edu.naeemaziz.nycschool.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import edu.naeemaziz.nycschool.api.SchoolWebApi
import edu.naeemaziz.nycschool.databinding.FragmentSatScoreBinding
import edu.naeemaziz.nycschool.model.SatScoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class SatScoreFragment : Fragment() {

    private lateinit var binding: FragmentSatScoreBinding

    // need to get dbn id come from another fragment
    private val args: SatScoreFragmentArgs by navArgs()

    @Inject
    lateinit var webApi: SchoolWebApi

    val TAG = "SatScoreFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSatScoreBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dbnID : String = args.dbn
        val address: String = args.address
        binding.apply {
            prgBarSatScore.visibility = View.VISIBLE
            webApi.getSatScoreDetail(dbnID).enqueue(object : Callback<List<SatScoreResponse>> {
                override fun onResponse(
                    call: Call<List<SatScoreResponse>>,
                    response: Response<List<SatScoreResponse>>
                ) {
                    when (response.code()) {
                        in 200..299 -> {
                            prgBarSatScore.visibility = View.GONE
                            Log.d("Response Code", " Successful messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                if (!itBody.isEmpty()) {
                                    schoolName.text = itBody.get(0).schoolName
                                    addressschool.text = address
                                    studentnum.text = itBody.get(0).numOfSatTestTakers
                                    reading.text = itBody.get(0).satCriticalReadingAvgScore
                                    writing.text = itBody.get(0).satWritingAvgScore
                                    math.text = itBody.get(0).satMathAvgScore

                                }
                                else{
                                    schoolName.text = "DBN not matched"
                                    addressschool.text = "NYC"
                                    studentnum.text = "0"
                                    reading.text = "0"
                                    writing.text = "0"
                                    math.text = "0"
                                }

                            }
                        }
                        in 300..399 -> {
                            prgBarSatScore.visibility = View.GONE
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                        in 400..499 -> {
                            prgBarSatScore.visibility = View.GONE
                            Log.d("Response Code", " Client error responses : ${response.code()}")
                        }
                        in 500..599 -> {
                            prgBarSatScore.visibility = View.GONE
                            Log.d("Response Code", " Server error responses : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<List<SatScoreResponse>>, t: Throwable) {
                    prgBarSatScore.visibility = View.GONE
                    Log.d(TAG, t.message.toString())
                }


            })

            closeButton.setOnClickListener {
                val direction =
                    SatScoreFragmentDirections.actionSatScoreFragmentToSchoolListFragment()
                findNavController().navigate(direction)
            }
        }

    }

}