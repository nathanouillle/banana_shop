package com.example.onlinepurchase.activity.data

import android.os.Parcel
import android.os.Parcelable

enum class Category(val id:Int):Parcelable {

    Fruits(1), Vegetables(2), Meats(3), Morning(4), Drinks(5);

    constructor(parcel: Parcel) : this(parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return values()[parcel.readInt()]
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}