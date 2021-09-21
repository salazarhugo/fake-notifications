package com.brock.fakenotifications.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    val appName = MutableLiveData<String>()
}
