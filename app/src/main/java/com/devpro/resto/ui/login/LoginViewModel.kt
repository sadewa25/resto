package com.devpro.resto.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.devpro.resto.data.source.AppRepository
import com.devpro.resto.data.source.remote.response.ValuesItems
import com.devpro.resto.utils.common.Resource
import kotlinx.coroutines.Dispatchers

class LoginViewModel(private val repository: AppRepository?) : ViewModel() {

    fun getUsers(datas: ValuesItems) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = repository?.loginUsers(
                        datas
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}