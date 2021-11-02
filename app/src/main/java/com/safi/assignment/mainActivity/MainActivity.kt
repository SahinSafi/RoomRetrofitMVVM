package com.safi.assignment.mainActivity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.safi.assignment.databinding.ActivityMainBinding
import com.safi.assignment.profile.UserProfileActivity
import com.safi.assignment.utils.ProgressDialog

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var progressDialog: ProgressDialog
    private lateinit var adapter: ShowAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MainViewModel()
        progressDialog = ProgressDialog(this)
        adapter = ShowAdapter(this)
        viewModel.search("any")

        initView()
        liveDataListener()
    }

    private fun initView() {
        binding.profileText.setOnClickListener {
            startActivity(Intent(applicationContext, UserProfileActivity::class.java))
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.searchViewID.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            @SuppressLint("SetTextI18n")
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.search(query)
                    binding.searchViewID.clearFocus()
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }


    private fun liveDataListener() {
        viewModel.onProgress.observe(this, {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        viewModel.onSuccess.observe(this, {
            if (it.isNullOrEmpty()) {
                binding.errorText.visibility = View.VISIBLE
                adapter.clearData()
            } else {
                binding.errorText.visibility = View.GONE
                adapter.addData(it)
            }

        })

        viewModel.onFailed.observe(this, {
            binding.errorText.visibility = View.VISIBLE
            adapter.clearData()
        })
    }

}