package com.app.stority.homeSpace.observer

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.app.stority.helper.Logger
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

    var backGroundColor = "-1"
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
            else -> repo.fetchSubCategoryDataList(false, apiCall.value)
        }
    }

    fun init(entryId: String): Boolean {
        Logger.e(Thread.currentThread(), "value")
        return if (apiCall.value == entryId) {
            true
        } else {
            Logger.e(Thread.currentThread(), "init")
            apiCall.value = entryId
            false
        }
    }

    fun deleteSubCategoryData(data: SubCategoryTable?) {
        repo.deleteSubCategoryData(data = data)
    }

    fun deleteSubCategoryListData(list: List<SubCategoryTable?>) {
        repo.deleteSubCategoryDataList(list = list)
    }

    fun updateColor(list: List<SubCategoryTable?>, color: String) {
        repo.updateBackGroundColor(list = list, color = color)
    }

    fun insertSubCategory(entryId: String, data: SubCategoryTable?) {

        repo.insertSubCategoryData(entryId, data)

    }

    fun updateSubCategory(data: SubCategoryTable?) {
        repo.updateCategory(data)
    }


}