package com.devpro.resto.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.devpro.resto.data.source.AppRepository
import com.devpro.resto.data.source.remote.response.ValuesItems
import com.devpro.resto.utils.common.Event
import com.devpro.resto.utils.common.Resource
import kotlinx.coroutines.Dispatchers

class CartViewModel (private val repository: AppRepository?) : ViewModel() {

    fun insertCart(datas: ValuesItems) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = repository?.insertCart(
                        datas
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    private val _openCart = MutableLiveData<Event<Unit>>()
    val openCart: LiveData<Event<Unit>> = _openCart

    fun openCart() {
        _openCart.value = Event(Unit)
    }

}