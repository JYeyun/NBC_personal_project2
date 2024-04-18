package com.example.nbc_personal_project_2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val img:Int,
    val price:String,
    val user:String,
    val address:String,
    val temperature:String,
    val title:String,
    val description:String,
    val like:Int,
    val chat:Int
) :Parcelable