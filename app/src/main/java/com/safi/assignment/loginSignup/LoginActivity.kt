package com.safi.assignment.loginSignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.safi.assignment.mainActivity.MainActivity
import com.safi.assignment.databinding.ActivityLoginBinding
import com.safi.assignment.utils.DataPreference
import com.safi.assignment.utils.ProgressDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginSignupViewModel
    private lateinit var progressDialog: ProgressDialog
    private lateinit var dataPreference: DataPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = LoginSignupViewModel(this.application, this)
        progressDialog = ProgressDialog(this)
        dataPreference = DataPreference(this)

        initView()
        liveDataListener()
    }

    private fun initView() {

        binding.txvSignup.setOnClickListener {
            startActivity(Intent(applicationContext, SignUpActivity::class.java))
        }

        binding.username.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    binding.username.isErrorEnabled = true
                    binding.username.error = "Field can not be empty"
                } else {
                    binding.username.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.password.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    binding.password.isErrorEnabled = true
                    binding.password.error = "Field can not be empty"
                } else {
                    binding.password.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.btnLogin.setOnClickListener {
            val username = binding.username.editText?.text.toString()
            val password = binding.password.editText?.text.toString()

            if (username.isEmpty() || binding.username.isErrorEnabled) {
                binding.username.isErrorEnabled = true
                binding.username.error = "Field can not be empty"
                binding.username.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty() || binding.password.isErrorEnabled) {
                binding.password.isErrorEnabled = true
                binding.password.error = "Field can not be empty"
                binding.password.requestFocus()
                return@setOnClickListener
            }

            viewModel.loginUser(password, username, findViewById(android.R.id.content))

        }

    }


    private fun liveDataListener() {

        viewModel.onProgress.observe(this, {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        viewModel.onLoginSuccess.observe(this, {
            CoroutineScope(Dispatchers.Main).launch {
                dataPreference.setUsername(it.username)
                dataPreference.setPassword(it.password)
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        })
    }


}