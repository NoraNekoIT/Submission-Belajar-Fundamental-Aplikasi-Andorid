package com.noranekoit.submission.dicoding.util

import androidx.recyclerview.widget.DiffUtil
import com.noranekoit.submission.dicoding.model.UserResponseItem

class UserDiffCallback(private val oldList: List<UserResponseItem>,
                       private val newList: List<UserResponseItem>):
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].username == newList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newList[newItemPosition]
        val oldItem = oldList[oldItemPosition]
        return compare(oldItem,newItem)
    }

    private fun compare(oldItem: UserResponseItem, newItem: UserResponseItem): Boolean{
        return (oldItem.follower == newItem.follower)
                && (oldItem.following == newItem.following)
                && (oldItem.name == newItem.name)
                && (oldItem.company == newItem.company)
                && (oldItem.location == newItem.location)
                && (oldItem.avatar == newItem.avatar)
                && (oldItem.repository == newItem.repository)
                && (oldItem.username == newItem.username)
    }

}