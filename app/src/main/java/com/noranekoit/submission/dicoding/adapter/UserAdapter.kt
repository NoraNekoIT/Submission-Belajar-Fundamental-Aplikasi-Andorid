package com.noranekoit.submission.dicoding.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.noranekoit.submission.dicoding.R
import com.noranekoit.submission.dicoding.model.UserResponseItem
import java.util.*
import kotlin.collections.ArrayList

class UserAdapter(private val listUser: ArrayList<UserResponseItem>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    val initialDataList = ArrayList<UserResponseItem>().apply {
        addAll(listUser)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback?) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgAvatar: ImageView = itemView.findViewById(R.id.iv_user)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_username)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_row_user,
            parent,
            false
        )
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val avatar = listUser[position].avatar
        val username = listUser[position].username
        if (avatar != null) {
            holder.imgAvatar.setImageResource(avatar)
        }
        holder.tvUsername.text = username
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int = listUser.size
    interface OnItemClickCallback {
        fun onItemClicked(userResponseItem: UserResponseItem)
    }

    fun getFilter(): Filter {
        return userFilter
    }

    private val userFilter =object : Filter(){
        override fun performFiltering(cari: CharSequence?): FilterResults {
            val filteredList: ArrayList<UserResponseItem> = ArrayList()
            if (cari == null || cari.isEmpty()){
                initialDataList.let {
                    filteredList.addAll(it)
                }
            }else{
                val query = cari.toString().trim().lowercase()
                initialDataList.forEach {
                    if (it.username?.lowercase(Locale.ROOT)?.contains(query) == true){
                        filteredList.add(it)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @Suppress("UNCHECKED_CAST")
        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(cari: CharSequence?, result: FilterResults?) {
            if (result?.values is ArrayList<*>){
                val listResult = result.values
                        as ArrayList<UserResponseItem>
                listUser.clear()
                listUser.addAll(listResult)
                notifyDataSetChanged()
            }
        }


    }

}

