package com.vimalcvs.testebud.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vimalcvs.testebud.model.ModelCategory
import com.vimalcvs.testebud.model.ModelDetail
import com.vimalcvs.testebud.model.ModelMeal
import com.vimalcvs.testebud.network.ApiService
import com.vimalcvs.testebud.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelMain(application: Application) : AndroidViewModel(application) {

    private val apiService: ApiService by lazy { RetrofitClient.createAPI(application) }

    private val _data = MutableLiveData<List<ModelMeal>>()
    val data: LiveData<List<ModelMeal>> = _data

    private val _detail = MutableLiveData<List<ModelDetail>>()
    val detail: LiveData<List<ModelDetail>> = _detail

    private val _search = MutableLiveData<List<ModelMeal>>()
    val search: LiveData<List<ModelMeal>> = _search

    private val _categories = MutableLiveData<List<ModelCategory>>()
    val categories: LiveData<List<ModelCategory>> = _categories

    private val _breakfast = MutableLiveData<List<ModelMeal>>()
    val breakfast: LiveData<List<ModelMeal>> = _breakfast

    private val _pasta = MutableLiveData<List<ModelMeal>>()
    val pasta: LiveData<List<ModelMeal>> = _pasta

    private val _lamb = MutableLiveData<List<ModelMeal>>()
    val lamb: LiveData<List<ModelMeal>> = _lamb

    private val _pork = MutableLiveData<List<ModelMeal>>()
    val pork: LiveData<List<ModelMeal>> = _pork

    private val _side = MutableLiveData<List<ModelMeal>>()
    val side: LiveData<List<ModelMeal>> = _side

    private val _vegetarian = MutableLiveData<List<ModelMeal>>()
    val vegetarian: LiveData<List<ModelMeal>> = _vegetarian

    private val _slider = MutableLiveData<List<ModelMeal>>()
    val slider: LiveData<List<ModelMeal>> = _slider


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isNoNetwork = MutableLiveData<Boolean>()
    val isNoNetwork: LiveData<Boolean> = _isNoNetwork

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> = _isEmpty


    fun fetchData(mealType: String) {
        _isLoading.value = true
        _isNoNetwork.value = false
        _isEmpty.value = false
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getRecipe(mealType)
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (meals.isNullOrEmpty()) {
                        _isEmpty.postValue(true)
                    } else {
                        _data.postValue(response.body()?.meals)
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

    fun fetchDetail(detail: String?) {
        _isLoading.value = true
        _isNoNetwork.value = false
        _isEmpty.value = false

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getDetail(detail!!)
                if (response.isSuccessful) {
                    if (response.body()?.meals.isNullOrEmpty()) {
                        _isEmpty.postValue(true)
                    } else {
                        _detail.postValue(response.body()?.meals)
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

    fun fetchSearch(search: String) {
        _isLoading.value = true
        _isNoNetwork.value = false
        _isEmpty.value = false

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getSearch(search)
                if (response.isSuccessful) {
                    if (response.body()?.meals.isNullOrEmpty()) {
                        _isEmpty.postValue(true)
                    } else {
                        _search.postValue(response.body()?.meals)
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


    init {
        fetchBreakfast()
        fetchPasta()
        fetchLamb()
        fetchPork()
        fetchSide()
        fetchVegetarian()
        fetchSlider()
    }

    private fun fetchMeals(mealType: String, liveData: MutableLiveData<List<ModelMeal>>) {
        _isLoading.value = true
        _isNoNetwork.value = false
        _isEmpty.value = false
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.getRecipe(mealType)
                if (response.isSuccessful) {
                    val meals = response.body()?.meals
                    if (meals.isNullOrEmpty()) {
                        _isEmpty.postValue(true)
                    } else {
                        liveData.postValue(meals)
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

    fun fetchBreakfast() {
        fetchMeals("Breakfast", _breakfast)
    }

    fun fetchPasta() {
        fetchMeals("Pasta", _pasta)
    }

    fun fetchLamb() {
        fetchMeals("Lamb", _lamb)
    }

    fun fetchPork() {
        fetchMeals("Pork", _pork)
    }

    fun fetchSide() {
        fetchMeals("Side", _side)
    }

    fun fetchVegetarian() {
        fetchMeals("Vegetarian", _vegetarian)
    }

    fun fetchSlider() {
        fetchMeals("Starter", _slider)
    }



















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