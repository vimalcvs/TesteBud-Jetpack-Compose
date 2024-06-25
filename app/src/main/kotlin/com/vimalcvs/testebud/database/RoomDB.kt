package com.vimalcvs.testebud.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vimalcvs.testebud.model.ModelCategory
import com.vimalcvs.testebud.model.ModelMeal
import com.vimalcvs.testebud.model.ModelNotification

@Database(entities = [ModelMeal::class, ModelCategory::class, ModelNotification::class], version = 6, exportSchema = false)
abstract class RoomDB : RoomDatabase() {

    abstract fun mealDao(): RoomDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getDatabase(context: Context): RoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDB::class.java,
                    "meal_databases"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
