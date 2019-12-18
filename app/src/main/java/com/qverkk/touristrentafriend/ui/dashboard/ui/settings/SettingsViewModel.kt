package com.qverkk.touristrentafriend.ui.dashboard.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Settings"
    }
    val text: LiveData<String> = _text
}