package com.safi.assignment.profile

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.safi.assignment.databinding.ActivityUserProfileBinding
import com.safi.assignment.loginSignup.LoginActivity
import com.safi.assignment.utils.ProgressDialog

class UserProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        viewModel = ProfileViewModel(this.application, this, this)

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        liveDataListener()
        viewModel.getUserData()
    }

    @SuppressLint("SetTextI18n")
    private fun liveDataListener() {

        viewModel.onProgress.observe(this, {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        viewModel.onProfileDataGet.observe(this, {
            binding.nameText.text = "Name: "+it.name
            binding.usernameText.text = "Username: "+it.username
            binding.phoneText.text = "Phone: "+it.phone
        })
    }
}