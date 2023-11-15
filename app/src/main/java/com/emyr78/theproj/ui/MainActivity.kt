package com.emyr78.theproj.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.emyr78.theproj.R
import com.emyr78.theproj.api.GitHubApi
import com.emyr78.theproj.constants.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var gitHubApi: GitHubApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(Constants.TAG, gitHubApi.getTopRepositories()[0].toString())
    }
}