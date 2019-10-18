package com.app.stority.homeSpace.repo


import androidx.lifecycle.LiveData
import com.app.stority.helper.AppExecutors
import com.app.stority.homeSpace.data.HomeSpaceDao
import com.app.stority.homeSpace.data.HomeSpaceTable
import com.app.stority.homeSpace.data.HomeSpaceTableResponse
import com.app.stority.remoteUtils.ApiResponse
import com.app.stority.remoteUtils.NetworkBoundResource
import com.app.stority.remoteUtils.Resource
import com.app.stority.remoteUtils.WebService
import com.app.stority.homeSpace.data.SubCategoryTable
import com.app.stority.homeSpace.data.SubCategoryTableResponse

import javax.inject.Inject


class SubCategoryRepository @Inject constructor(
    private val executor: AppExecutors,
    private val webService: WebService,
    private val dao: HomeSpaceDao
) {

    fun fetchSubCategoryDataList(shouldFetch: Boolean): LiveData<Resource<List<SubCategoryTable>>> {

        return object : NetworkBoundResource<List<SubCategoryTable>, SubCategoryTableResponse>(executor) {

            override fun shouldFetch(data: List<SubCategoryTable>?) = shouldFetch

            override fun uploadTag() = null

            override fun createCall(): LiveData<ApiResponse<SubCategoryTableResponse>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun saveCallResult(item: SubCategoryTableResponse) {
            }

            override fun loadFromDb() = dao.fetchAllSubCategoryData()

        }.asLiveData()
    }

    fun fetchHomeSpaceData(id: String, shouldFetch: Boolean): LiveData<Resource<HomeSpaceTable>>? {

        return object : NetworkBoundResource<HomeSpaceTable, HomeSpaceTableResponse>(executor) {

            override fun shouldFetch(data: HomeSpaceTable?) = shouldFetch

            override fun uploadTag() = null

            override fun createCall(): LiveData<ApiResponse<HomeSpaceTableResponse>> {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun saveCallResult(item: HomeSpaceTableResponse) {
            }

            override fun loadFromDb() = dao.fetchHomeSpaceData(id = id)

        }.asLiveData()
    }

    fun insertSubCategoryData(data: SubCategoryTable) =
        executor.diskIO().execute { dao.insertSubCategoryData(data = data) }

    fun deleteSubCategoryAllData() = executor.diskIO().execute { dao.deleteHomeSpaceAllData() }

    fun deleteSubCategoryData(data: SubCategoryTable?) =
        executor.diskIO().execute { dao.deleteSubCategoryData(id = data?.subCategoryId) }

    fun deleteSubCategoryDataList(list: List<SubCategoryTable?>) {
        list.forEach {
            executor.diskIO().execute {
                dao.deleteSubCategoryData(id = it?.subCategoryId)
            }
        }
    }
}


