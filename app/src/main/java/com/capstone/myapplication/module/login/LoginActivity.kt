package com.capstone.myapplication.module.login

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.myapplication.R
import com.capstone.myapplication.base.BaseActivity
import com.capstone.myapplication.databinding.LoginActivityBinding
import com.capstone.myapplication.module.register.RegisterActivity
import com.capstone.myapplication.shared.database.UserPreferences
import com.capstone.myapplication.shared.database.ViewModelDatastoreFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.storyapp.dicoding.shared.view.showToast


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : BaseActivity<LoginActivityBinding>(R.layout.login_activity)
{
    private lateinit var viewModel: LoginViewModel
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val googleSignInAResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(it.data)

        try {
            val account: GoogleSignInAccount = task.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            redirectToHomepage()
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViewModel()
        initializeOnClicks()
        initializeGoogleLogin()
        binding.email = ""
        binding.password = ""
    }

    override fun onStart() {
        super.onStart()

        val account = GoogleSignIn.getLastSignedInAccount(this)
        // TODO: check if acc actually already registered or not if not go to register
        //          with google email
        if(account != null)
            redirectToHomepage()
    }

    private fun initializeGoogleLogin() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
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
//                viewModel.authenticate(
//                    binding.email!!,
//                    binding.password!!
//                )
            }

            signInButton.setOnClickListener {
                val signInIntent = mGoogleSignInClient.signInIntent
                googleSignInAResult.launch(signInIntent)
            }

            registerBtn.setOnClickListener {
                redirectToRegister()
            }
        }
    }

     private fun redirectToHomepage() {
         showToast("Sign In Berhasil, redirect ke homepage")
//        startActivity(
//            Intent(this, HomepageActivity::class.java)
//        )
//        finish()
    }

    private fun redirectToRegister() {
        startActivity(
            Intent(this, RegisterActivity::class.java)
        )
    }
}