package com.devpro.resto.ui.detail_cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.devpro.resto.data.source.AppRepository
import com.devpro.resto.data.source.remote.response.ResponseJSON
import com.devpro.resto.data.source.remote.response.ValuesItems
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

    private val _dataCart =
        MutableLiveData<List<ValuesItems>>().apply { value = emptyList() }
    val dataCart: LiveData<List<ValuesItems>> = _dataCart

    fun setDataCart(data: ResponseJSON?) {
        _dataCart.apply {
            value = data?.values as List<ValuesItems>?
        }
    }

}