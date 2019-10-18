package com.app.stority.homeSpace.observer

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.stority.remoteUtils.AbsentLiveData
import com.app.stority.remoteUtils.Resource
import com.app.stority.homeSpace.data.SubCategoryTable
import com.app.stority.homeSpace.repo.SubCategoryRepository
import javax.inject.Inject


class SubCategoryViewModel @Inject constructor(

    var repo: SubCategoryRepository
) : ViewModel(), Observable {
    private val callbacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }
    var apiCall = MutableLiveData<String>()

    override fun addOnPropertyChangedCallback(
        callback: Observable.OnPropertyChangedCallback
    ) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    var data = SubCategoryTable()

    var result: LiveData<Resource<List<SubCategoryTable>>> = Transformations.switchMap(apiCall) {
        data
        when (apiCall.value) {
            null -> AbsentLiveData.create()
            else -> repo.fetchSubCategoryDataList(false)
        }
    }

    fun init() {
        apiCall.value = "1"
    }

    fun deleteSubCategoryData(data: SubCategoryTable?) {
        repo.deleteSubCategoryData(data = data)
    }

    fun deleteSubCategoryListData(list: List<SubCategoryTable?>) {
        repo.deleteSubCategoryDataList(list = list)
    }


    fun insertSubCategory(data: SubCategoryTable) {
        repo.insertSubCategoryData(data)

    }


}