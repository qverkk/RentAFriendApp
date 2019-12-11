package com.qverkk.touristrentafriend.ui.dashboard.ui.currentUser

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.qverkk.touristrentafriend.ui.login.helpers.InformationAsyncTask
import com.qverkk.touristrentafriend.ui.login.helpers.UserAsyncTask
import java.math.BigDecimal

class CurrentUserViewModel : ViewModel() {

    private val _fullName = MutableLiveData<String>()
    val fullName: LiveData<String> = _fullName

    private val _birthDate = MutableLiveData<String>()
    val birthDate: LiveData<String> = _birthDate

    private val _country = MutableLiveData<String>()
    val country: LiveData<String> = _country

    private val _city = MutableLiveData<String>()
    val city: LiveData<String> = _city

    private val _price = MutableLiveData<BigDecimal>()
    val price: LiveData<BigDecimal> = _price

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    private val _imageSource = MutableLiveData<Uri>()
    val imageSource: LiveData<Uri> = _imageSource

    init {
        fillAllUserInfo()
    }

    fun changeImageUri(uri: Uri) {
        _imageSource.value = uri
    }

    private fun fillAllUserInfo() {
        val user = UserAsyncTask().execute().get()
        val information = InformationAsyncTask().execute().get()
        _fullName.value = "${user.firstName} ${user.lastName}"
        _birthDate.value = user.birthDate
        _country.value = information.country
        _city.value = information.cityName
        _price.value = information.price
        _description.value = information.description
    }
}