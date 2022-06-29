package com.noranekoit.submission.dicoding.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.noranekoit.submission.dicoding.R
import com.noranekoit.submission.dicoding.databinding.ActivityDetailBinding
import com.noranekoit.submission.dicoding.model.UserResponseItem


class DetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayDetail()
    }


    private fun displayDetail(){
        val data = intent.getParcelableExtra<UserResponseItem>(EXTRA_DATA) as UserResponseItem
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)
        val collapsingToolbarLayout = binding.collapsingToolbar
        collapsingToolbarLayout.title = data.name
        data.avatar?.let { binding.ivItemAvatar.setImageResource(it) }
        binding.layoutDetail.apply {
            tvUsername.text = data.username
            tvCompany.text = getString(R.string.company,data.company)
            tvLocation.text = getString(R.string.location,data.location)
            tvRepository.text =getString(R.string.jumlah_repository,data.repository)
            tvFollower.text = getString(R.string.follower,data.follower)
            tvFollowing.text =getString(R.string.following,data.following)
            fabShare.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, "Github dengan username : {${data.username}} yuk kepoin ")
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share Via"))
            }
        }

    }
    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}