package com.emyr78.theproj.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emyr78.theproj.R
import com.emyr78.theproj.databinding.ActivityMainBinding
import com.emyr78.theproj.ui.details.RepoDetailsFragment
import com.emyr78.theproj.ui.home.HomeFragment
import com.emyr78.theproj.ui.screen.ActivityDrivenScreenNavigator
import com.emyr78.theproj.ui.screen.DetailsScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var screenNavigator: ActivityDrivenScreenNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frag_container, HomeFragment())
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        screenNavigator.handleGoToScreen = { screen ->
            when (screen) {
                is DetailsScreen -> supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.enter_from_right,
                        R.anim.exit_to_left,
                        R.anim.enter_from_left,
                        R.anim.exit_to_right
                    )
                    .replace(
                        R.id.frag_container,
                        RepoDetailsFragment.newInstance(screen.repoOwner, screen.repoName)
                    )
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}