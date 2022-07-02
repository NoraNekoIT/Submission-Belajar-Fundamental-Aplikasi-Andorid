package com.noranekoit.submission.dicoding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.noranekoit.submission.dicoding.databinding.ItemRowUserBinding
import com.noranekoit.submission.dicoding.model.UserResponseItem
import com.noranekoit.submission.dicoding.util.UserDiffCallback
import java.util.*
import kotlin.collections.ArrayList

class UserAdapter(private val listUser: ArrayList<UserResponseItem>) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    val initialDataList = ArrayList<UserResponseItem>().apply {
        addAll(listUser)
    }

    private fun setData(newListData: List<UserResponseItem>?){
        if (newListData == null) return
        val diffUtil = UserDiffCallback(listUser,newListData)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        listUser.clear()
        listUser.addAll(newListData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback?) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(var binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val avatar = listUser[position].avatar
        val username = listUser[position].username
        if (avatar != null) {
            holder.binding.ivUser.setImageResource(avatar)
        }
        holder.binding.tvUsername.text = username
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

        override fun publishResults(cari: CharSequence?, result: FilterResults?) {
            if (result?.values is ArrayList<*>){
                val listResult = result.values
                        as ArrayList<UserResponseItem>
                setData(listResult)
            }
        }

    }
}