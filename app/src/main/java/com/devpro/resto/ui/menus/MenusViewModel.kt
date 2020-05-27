package com.devpro.resto.ui.menus

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.devpro.resto.data.source.AppRepository
import com.devpro.resto.data.source.remote.response.ResponseJSON
import com.devpro.resto.data.source.remote.response.ValuesItems
import com.devpro.resto.utils.common.Event
import com.devpro.resto.utils.common.Resource
import kotlinx.coroutines.Dispatchers

class MenusViewModel(private val repository: AppRepository?) : ViewModel() {

    private val _dataMenu =
        MutableLiveData<List<ValuesItems>>().apply { value = emptyList() }
    val dataMenu: LiveData<List<ValuesItems>> = _dataMenu

    fun setDataMenu(data: ResponseJSON?) {
        _dataMenu.apply {
            value = data?.values as List<ValuesItems>?
        }
    }

    fun getMenuByCategory(datas: ValuesItems) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = repository?.getMenuByCategory(
                        datas
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private val _onItemClick = MutableLiveData<Event<ValuesItems>>()
    val onItemClick: LiveData<Event<ValuesItems>> = _onItemClick

    fun onItemClick(data: ValuesItems) {
        _onItemClick.value = Event(data)
    }

}