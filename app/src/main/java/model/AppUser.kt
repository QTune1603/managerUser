package com.example.bt1.model

import android.os.Parcel
import android.os.Parcelable

data class AppUser(
    var userName: String,
    var password: String,
    var fullName: String,
    var email: String,
    var tel: String,     // Trường tel
    var id: Int,         // Trường id
    var dateOfBirth: String,
    var gender: String   // Trường gender
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",  // tel
        parcel.readInt(),           // id
        parcel.readString() ?: "",  // dateOfBirth
        parcel.readString() ?: ""   // gender
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userName)
        parcel.writeString(password)
        parcel.writeString(fullName)
        parcel.writeString(email)
        parcel.writeString(tel)       // Ghi trường tel
        parcel.writeInt(id)           // Ghi trường id
        parcel.writeString(dateOfBirth) // Ghi trường dateOfBirth
        parcel.writeString(gender)    // Ghi trường gender
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AppUser> {
        override fun createFromParcel(parcel: Parcel): AppUser {
            return AppUser(parcel)
        }

        override fun newArray(size: Int): Array<AppUser?> {
            return arrayOfNulls(size)
        }
    }
}
