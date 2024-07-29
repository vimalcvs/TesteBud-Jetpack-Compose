package com.vimalcvs.testebud.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.vimalcvs.testebud.database.Repository
import com.vimalcvs.testebud.model.ModelCategory
import com.vimalcvs.testebud.model.ModelMeal
import com.vimalcvs.testebud.model.ModelNotification
import com.vimalcvs.testebud.network.ApiService
import com.vimalcvs.testebud.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class ViewModelRoom(application: Application) : AndroidViewModel(application) {

    private val apiService: ApiService by lazy { RetrofitClient.createAPI(application) }
    private val repository: Repository = Repository.getInstance(application)!!

    val allNotifications: Flow<List<ModelNotification>> = repository.allNotifications()
    val isEmptyNotification: Flow<Boolean> = allNotifications.map { it.isEmpty() }


    suspend fun insertNotification(notification: ModelNotification) {
        repository.insertNotification(notification)
    }

    fun deleteAllNotification() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllNotification()
        }
    }


    val allFavorite: LiveData<List<ModelMeal>> = repository.allFavorite()
    val isEmptyFavorite: LiveData<Boolean> = allFavorite.map { it.isEmpty() }


    private val _categories = MutableLiveData<List<ModelCategory>>()
    val categories: LiveData<List<ModelCategory>> = _categories


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isNoNetwork = MutableLiveData<Boolean>()
    val isNoNetwork: LiveData<Boolean> = _isNoNetwork

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty

    init {
        fetchCategories()
    }

    fun fetchCategories() {
        _isLoading.value = true
        _isNoNetwork.value = false
        _isEmpty.value = false

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getCategories()
                if (response.isSuccessful) {
                    if (response.body()?.categories.isNullOrEmpty()) {
                        _isEmpty.postValue(true)
                    } else {
                        _categories.postValue(response.body()?.categories)
                    }
                } else {
                    _isNoNetwork.postValue(true)
                }
            } catch (e: Exception) {
                _isNoNetwork.postValue(true)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

}

sealed class RoomState {
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}