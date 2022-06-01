package com.capstone.myapplication.module.login

import android.content.Context
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.myapplication.R
import com.capstone.myapplication.base.BaseActivity
import com.capstone.myapplication.databinding.LoginActivityBinding
import com.capstone.myapplication.shared.database.UserPreferences
import com.capstone.myapplication.shared.database.ViewModelDatastoreFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : BaseActivity<LoginActivityBinding>(R.layout.login_activity)
{
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
        initializeOnClicks()
        binding.email = ""
        binding.password = ""
    }

    private fun initializeViewModel() {
        val pref = UserPreferences.getInstance(dataStore)
        val viewModelFactory = ViewModelDatastoreFactory(pref)

        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java].apply {
            user.observe(this@LoginActivity) {
                if (it != null) {
                    useToken(it)
                    redirectToHomepage()
                }
            }
            isLoading.observe(this@LoginActivity) {
                if (it)
                    startLoading()
                else
                    stopLoading()
            }
        }
    }

    private fun initializeOnClicks() {
        binding.apply {
            loginBtn.setOnClickListener {
                viewModel.authenticate(
                    binding.email!!,
                    binding.password!!
                )
            }
        }
    }

     private fun redirectToHomepage() {
//        startActivity(
//            Intent(this, HomepageActivity::class.java)
//        )
        finish()
    }
}