package com.noranekoit.submission.dicoding.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponseItem(
	var follower: Int?=null  ,
	var following: Int?=null,
	var name: String?="" ,
	var company: String?="" ,
	var location: String?="" ,
	var avatar: Int?=null ,
	var repository: Int?=null ,
	var username: String?=""
) : Parcelable