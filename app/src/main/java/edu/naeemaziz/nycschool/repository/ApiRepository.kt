package edu.naeemaziz.nycschool.repository

import dagger.hilt.android.scopes.ActivityScoped
import edu.naeemaziz.nycschool.api.SchoolWebApi
import javax.inject.Inject

@ActivityScoped
class ApiRepository @Inject constructor(
    private val apiServices: SchoolWebApi,
) {
    fun getSatScoreDetail(dbn: String) = apiServices.getSatScoreDetail(dbn)
    fun getSchoolList() = apiServices.getSchoolList()
}