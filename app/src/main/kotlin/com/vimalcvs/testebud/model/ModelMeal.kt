package com.vimalcvs.testebud.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_meal")
open class ModelMeal(
    @PrimaryKey
    open val idMeal: Int,
    open val strMeal: String,
    open val strMealThumb: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idMeal)
        parcel.writeString(strMeal)
        parcel.writeString(strMealThumb)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelMeal> {
        override fun createFromParcel(parcel: Parcel): ModelMeal {
            return ModelMeal(parcel)
        }

        override fun newArray(size: Int): Array<ModelMeal?> {
            return arrayOfNulls(size)
        }
    }
}
