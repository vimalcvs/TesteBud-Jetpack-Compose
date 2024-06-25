package com.vimalcvs.testebud.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_category")
data class ModelCategory(
    @PrimaryKey
    val idCategory: String,
    val strCategory: String,
    val strCategoryDescription: String,
    val strCategoryThumb: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idCategory)
        parcel.writeString(strCategory)
        parcel.writeString(strCategoryDescription)
        parcel.writeString(strCategoryThumb)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelCategory> {
        override fun createFromParcel(parcel: Parcel): ModelCategory {
            return ModelCategory(parcel)
        }

        override fun newArray(size: Int): Array<ModelCategory?> {
            return arrayOfNulls(size)
        }
    }
}