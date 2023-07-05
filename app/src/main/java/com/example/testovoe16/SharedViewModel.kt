package com.example.testovoe16

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    private val _selectedCrypts: MutableLiveData<List<Crypta>> = MutableLiveData()
    val selectedCrypts: LiveData<List<Crypta>> = _selectedCrypts

    fun getMatches(): LiveData<List<Crypta>> {
        return _selectedCrypts
    }

    fun addCrypto(crypta: Crypta) {
        val currentList = _selectedCrypts.value?.toMutableList() ?: mutableListOf()
        currentList.add(crypta)
        _selectedCrypts.value = currentList
    }

    fun removeCrypto(crypta: Crypta) {
        val currentList = _selectedCrypts.value?.toMutableList() ?: return
        currentList.remove(crypta)
        _selectedCrypts.value = currentList
    }
}
