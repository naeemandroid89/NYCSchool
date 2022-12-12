package edu.naeemaziz.nycschool.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import edu.naeemaziz.nycschool.adapter.SchoolListAdapter
import edu.naeemaziz.nycschool.api.SchoolWebApi
import edu.naeemaziz.nycschool.databinding.FragmentSchoolListBinding
import edu.naeemaziz.nycschool.model.SchoolListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import javax.inject.Inject

@AndroidEntryPoint
class SchoolListFragment : Fragment() {

    @Inject
    lateinit var webApi: SchoolWebApi

    @Inject
    lateinit var schoolAdapter : SchoolListAdapter

    private lateinit var binding: FragmentSchoolListBinding

    val TAG ="SchoolListFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSchoolListBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            prgBarSchool.visibility = View.VISIBLE
            webApi.getSchoolList().enqueue(object : Callback<List<SchoolListResponse>> {

                override fun onResponse(call: Call<List<SchoolListResponse>>, response: Response<List<SchoolListResponse>>){
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            prgBarSchool.visibility=View.GONE
                            response.body()?.let { itBody ->
                                if(!itBody.isEmpty()){
                                    schoolAdapter.differ.submitList(itBody)

                                    rlSchoolList.apply {
                                        layoutManager = LinearLayoutManager(requireContext())
                                        adapter = schoolAdapter
                                    }
                                    schoolAdapter.setOnItemClickListener {
                                        var address: String
                                        address = it.primaryAddressLine1 + ", " + it.city + ", " + it.stateCode + ", " + it.zip
                                        val direction = SchoolListFragmentDirections.actionSchoolListFragmentToSatScoreFragment(it.dbn, address)
                                        findNavController().navigate(direction)
                                    }
                                }
                            }
                        }
                        in 300..399 -> {
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                        in 400..499 -> {
                            Log.d("Response Code", " Client error responses : ${response.code()}")
                        }
                        in 500..599 -> {
                            Log.d("Response Code", " Server error responses : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<List<SchoolListResponse>>, t: Throwable) {

                    prgBarSchool.visibility = View.GONE
                    Log.d(TAG,t.message.toString())

                }
            })
        }
    }
}