package com.example.proptit_protify.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.proptit_protify.pojo.ApiService
import com.example.proptit_protify.pojo.MyData
import com.example.proptit_protify.pojo.RetrofitHelper
import kotlinx.coroutines.launch

class SearchViewModel(application: Application): AndroidViewModel(application){
    private val retrofitBuilder = RetrofitHelper.apiService
    private val _data = MutableLiveData<MyData>()
    init {
        viewModelScope.launch {
            val retrofitData = retrofitBuilder.getData("Hello")
            _data.value = retrofitData
        }
    }
    internal fun search(keyword: String){
        viewModelScope.launch {
            val retrofitData = retrofitBuilder.getData(keyword)
            _data.value = retrofitData
        }
    }
    val data: LiveData<MyData> = _data
}