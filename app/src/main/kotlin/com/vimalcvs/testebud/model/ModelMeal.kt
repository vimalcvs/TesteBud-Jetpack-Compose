package com.vimalcvs.testebud.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "table_meal")
@Parcelize
data class ModelMeal(
    @PrimaryKey
    val idMeal: Int,
    val strMeal: String,
    val strMealThumb: String
) : Parcelable