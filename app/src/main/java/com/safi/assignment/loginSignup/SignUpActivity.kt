package com.safi.assignment.loginSignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.safi.assignment.mainActivity.MainActivity
import com.safi.assignment.databinding.ActivitySignUpBinding
import com.safi.assignment.roomDB.tables.User
import com.safi.assignment.utils.DataPreference
import com.safi.assignment.utils.ProgressDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: LoginSignupViewModel
    private lateinit var progressDialog: ProgressDialog
    private lateinit var dataPreference: DataPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = LoginSignupViewModel(this.application, this)
        progressDialog = ProgressDialog(this)
        dataPreference = DataPreference(this)

        initView()
        liveDataListener()
    }


    private fun initView() {

        binding.txvLogin.setOnClickListener {
            onBackPressed()
        }

        binding.nameInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    binding.nameInput.isErrorEnabled = true
                    binding.nameInput.error = "Field can not be empty"
                } else {
                    binding.nameInput.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

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

        binding.phoneInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.isNullOrBlank()) {
                    binding.phoneInput.isErrorEnabled = true
                    binding.phoneInput.error = "Field can not be empty"
                } else {
                    binding.phoneInput.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.btnSignup.setOnClickListener {  // input validation and register user
            val name = binding.nameInput.editText?.text.toString()
            val username = binding.username.editText?.text.toString().trim()
            val password = binding.password.editText?.text.toString()
            val phone = binding.phoneInput.editText?.text.toString()

            if (name.isEmpty() || binding.nameInput.isErrorEnabled) {
                binding.nameInput.isErrorEnabled = true
                binding.nameInput.error = "Field can not be empty"
                binding.nameInput.requestFocus()
                return@setOnClickListener
            }

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

            if (phone.isEmpty() || binding.phoneInput.isErrorEnabled) {
                binding.phoneInput.isErrorEnabled = true
                binding.phoneInput.error = "Field can not be empty"
                binding.phoneInput.requestFocus()
                return@setOnClickListener
            }

            val user = User(0, name, username, password, phone)
            viewModel.registerUser(user, findViewById(android.R.id.content))

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

        viewModel.isRegistrationSuccess.observe(this, {
            if (it) {
                CoroutineScope(Dispatchers.Main).launch {
                    dataPreference.setUsername(binding.username.editText?.text.toString().trim())
                    dataPreference.setPassword(binding.password.editText?.text.toString())
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }
            }
        })

    }

}