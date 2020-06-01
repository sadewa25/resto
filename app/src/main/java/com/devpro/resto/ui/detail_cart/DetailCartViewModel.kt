package com.devpro.resto.ui.detail_cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.devpro.resto.data.source.AppRepository
import com.devpro.resto.data.source.remote.response.DetailItems
import com.devpro.resto.data.source.remote.response.ValuesItems
import com.devpro.resto.utils.common.Event
import com.devpro.resto.utils.common.Resource
import kotlinx.coroutines.Dispatchers

class DetailCartViewModel(private val repository: AppRepository?) : ViewModel() {

    fun getViewCartByOrder(datas: ValuesItems) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = repository?.getViewCartByOrder(
                        datas
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun insertOrder(datas: ValuesItems) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = repository?.insertOrder(
                        datas
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private val _dataCart =
        MutableLiveData<List<DetailItems>>().apply { value = emptyList() }
    val dataCart: LiveData<List<DetailItems>> = _dataCart

    fun setDataCart(data: List<DetailItems?>?) {
        _dataCart.apply {
            value = data as List<DetailItems>?
        }
    }

    private val _onItemOrder = MutableLiveData<Event<Unit>>()
    val onItemOrder: LiveData<Event<Unit>> = _onItemOrder

    fun onItemOrder() {
        _onItemOrder.value = Event(Unit)
    }

    fun updateTable(datas: ValuesItems) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = repository?.getUpdateTables(
                        datas
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}