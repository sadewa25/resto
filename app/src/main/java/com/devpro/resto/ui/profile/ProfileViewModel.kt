package com.devpro.resto.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devpro.resto.data.source.AppRepository
import com.devpro.resto.utils.common.Event

class ProfileViewModel(private val repository: AppRepository?) : ViewModel()  {
    private val _openSignout = MutableLiveData<Event<Unit>>()
    val openSignout: LiveData<Event<Unit>> = _openSignout

    fun openSignout() {
        _openSignout.value = Event(Unit)
    }
}