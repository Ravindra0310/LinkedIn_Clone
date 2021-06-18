package com.example.jobedin.ui.presentation.parcelables

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDetailParcel(
    val name: String,
    val image: String,
    val uid: String
) : Parcelable