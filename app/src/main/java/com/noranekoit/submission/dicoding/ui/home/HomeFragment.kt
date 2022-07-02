package com.noranekoit.submission.dicoding.ui.home

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.noranekoit.submission.dicoding.adapter.UserAdapter
import com.noranekoit.submission.dicoding.databinding.FragmentHomeBinding
import com.noranekoit.submission.dicoding.model.UserResponseItem
import com.noranekoit.submission.dicoding.ui.detail.DetailActivity
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets


class HomeFragment : Fragment() {

    private val listUser: ArrayList<UserResponseItem> = ArrayList()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var rvUser: RecyclerView
    private val binding get() = _binding!!
    private var adapter: UserAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUser = binding.rvUser
        rvUser.setHasFixedSize(true)
        getGithubList()
        showRecycler()
        binding.searchBar.setOnQueryTextListener(
            object:SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter?.getFilter()?.filter(newText)
                    return false
                }

            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getGithubList() {
        val myJson = loadJsonFromAsset()

        try {

            val jsonArray = JSONArray(myJson)

            for (i in 0 until jsonArray.length()) {
                val user = UserResponseItem()
                val jsonObjectUser = jsonArray.getJSONObject(i)

                val avatarResource = resources.getIdentifier(
                    jsonObjectUser.getString("avatar"),
                    null,
                    requireActivity().packageName
                )
                user.apply {
                    username = jsonObjectUser.getString("username")
                    name = jsonObjectUser.getString("name")
                    avatar = avatarResource
                    company = jsonObjectUser.getString("company")
                    location = jsonObjectUser.getString("location")
                    repository = jsonObjectUser.getInt("repository")
                    follower = jsonObjectUser.getInt("follower")
                    following = jsonObjectUser.getInt("following")
                }

                listUser.add(user)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun loadJsonFromAsset(): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = requireActivity().assets.open("githubuser.json")
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, StandardCharsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json
    }

    private fun showRecycler() {
        if (requireActivity().applicationContext.resources
                .configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        ) {
            rvUser.layoutManager = GridLayoutManager(requireActivity(), 2)
        } else {
            rvUser.layoutManager = LinearLayoutManager(requireActivity())
        }
        adapter = UserAdapter(listUser)
        rvUser.adapter = adapter
        adapter?.setOnItemClickCallback(
            object :UserAdapter.OnItemClickCallback{
                override fun onItemClicked(userResponseItem: UserResponseItem) {
                    showSelectedUser(userResponseItem)
                }
            }
        )
    }

    private fun showSelectedUser(user: UserResponseItem) {
        Toast.makeText(requireActivity(), "Kamu memilih github dengan username " +
                user.username, Toast.LENGTH_SHORT).show()

        val moveWithData = Intent(this.requireActivity(), DetailActivity::class.java)
        moveWithData.putExtra(DetailActivity.EXTRA_DATA,user)
        startActivity(moveWithData)
    }

}