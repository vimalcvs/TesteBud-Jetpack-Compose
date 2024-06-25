package com.vimalcvs.testebud.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vimalcvs.testebud.model.ModelCategory
import com.vimalcvs.testebud.model.ModelMeal
import com.vimalcvs.testebud.model.ModelNotification
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {

    // Favorite

    @Query("SELECT * FROM table_meal")
    fun getAllFavorite(): LiveData<List<ModelMeal>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(modelMeal: ModelMeal)

    @Delete
    suspend fun deleteFavorite(modelMeal: ModelMeal)

    @Query("SELECT EXISTS (SELECT 1 FROM table_meal WHERE idMeal = :id)")
    suspend fun isFavorite(id: Int): Boolean

    // Slider

    @Query("SELECT * FROM table_meal")
    suspend fun getAllSlider(): List<ModelMeal>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSlider(meals: List<ModelMeal>)

    @Query("DELETE FROM table_meal")
    suspend fun deleteAllSlider()

    // Category

    @Query("SELECT * FROM table_category")
    suspend fun getAllCategories(): List<ModelCategory>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<ModelCategory>)

    @Query("DELETE FROM table_category")
    suspend fun deleteAllCategory()





    // Notification
    @Query("DELETE FROM notifications_table")
    suspend fun deleteAllNotification()

    @Delete
    suspend fun deleteNotification(notification: ModelNotification)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: ModelNotification)

    @Query("SELECT * FROM notifications_table")
    fun getAllNotifications(): Flow<List<ModelNotification>>

}
