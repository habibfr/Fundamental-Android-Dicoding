package com.habibfr.githubusersapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _selectedItem = MutableLiveData<String>()
    val selectedItem: LiveData<String> get() = _selectedItem

    fun selectItem(item: String) {
        _selectedItem.value = item
    }
}