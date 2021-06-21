package com.example.jobedin.ui.presentation.parcelables

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PostParcel(
    var likes: Int? = 0,
    val postText: String? = "nan",
    val postVideo: String? = null,
    val profilePic: String? = null,
    val subDis1: String? = null,
    val tags: String? = null,
    val time: String? = null,
    val listOfAllLiked: HashMap<String, Boolean>? = hashMapOf(),
    val userName: String? = null,
    val postImage: String? = null,
    var uniqueKey: String? = null,
    var isLiked: Boolean,
   var postOwnerUid:String
) : Parcelable
