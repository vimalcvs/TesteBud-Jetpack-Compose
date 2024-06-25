package com.vimalcvs.testebud.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.vimalcvs.testebud.model.ModelCategory
import com.vimalcvs.testebud.model.ModelMeal
import com.vimalcvs.testebud.model.ModelNotification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Repository(context: Context) {

    private val roomDao: RoomDao
    private val favoriteLiveData: LiveData<List<ModelMeal>>
    private var sliderList: List<ModelMeal> = emptyList()
    private var categoryList: List<ModelCategory> = emptyList()
    private val notificationLiveData: Flow<List<ModelNotification>>

    init {
        val database = RoomDB.getDatabase(context)
        roomDao = database.mealDao()
        favoriteLiveData = roomDao.getAllFavorite()
        notificationLiveData = roomDao.getAllNotifications()

        CoroutineScope(Dispatchers.IO).launch {
            sliderList = roomDao.getAllSlider()
            categoryList = roomDao.getAllCategories()
        }
    }

    fun allNotifications(): Flow<List<ModelNotification>> {
        return notificationLiveData
    }

    suspend fun insertNotification(notification: ModelNotification) {
        roomDao.insertNotification(notification)
    }

    fun deleteNotification(model: ModelNotification) {
        CoroutineScope(Dispatchers.IO).launch {
            model.let { roomDao.deleteNotification(it) }
        }
    }
    suspend fun deleteAllSlider() {
        roomDao.deleteAllSlider()
    }

    suspend fun deleteAllNotification() {
        roomDao.deleteAllNotification()
    }


    fun allFavorite(): LiveData<List<ModelMeal>> {
        return favoriteLiveData
    }

    fun deleteFavorite(model: ModelMeal) {
        CoroutineScope(Dispatchers.IO).launch {
            model.let { roomDao.deleteFavorite(it) }
        }
    }

    fun insertFavorite(model: ModelMeal?) {
        CoroutineScope(Dispatchers.IO).launch {
            model?.let { roomDao.insertFavorite(it) }
        }
    }

    suspend fun isFavorite(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            roomDao.isFavorite(id)
        }
    }

    fun allSlider(): List<ModelMeal> {
        return sliderList
    }

    fun insertSlider(model: List<ModelMeal>) {
        CoroutineScope(Dispatchers.IO).launch {
            roomDao.insertAllSlider(model)
        }
    }

    fun allCategory(): List<ModelCategory> {
        return categoryList
    }

    fun insertCategory(model: List<ModelCategory>) {
        CoroutineScope(Dispatchers.IO).launch {
            roomDao.insertAllCategories(model)
        }
    }

    companion object {
        private var repository: Repository? = null
        fun getInstance(context: Context): Repository? {
            if (repository == null) {
                repository = Repository(context)
            }
            return repository
        }
    }
}
