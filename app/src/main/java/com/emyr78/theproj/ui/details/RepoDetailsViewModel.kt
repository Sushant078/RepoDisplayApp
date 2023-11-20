package com.emyr78.theproj.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emyr78.theproj.constants.Constants
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/* HiltViewModel doesn't support AssistedInjection yet -> https://github.com/google/dagger/issues/2287 */
class RepoDetailsViewModel @AssistedInject constructor(
    @Assisted("repoName") private val repoName: String,
    @Assisted("repoOwner") private val repoOwner: String
) : ViewModel() {
    companion object {
        fun provideFactory(
            assistedFactory: RepoDetailsViewModelFactory,
            repoName: String,
            repoOwner: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return assistedFactory.create(repoName, repoOwner) as T
            }
        }
    }

    fun getArgs() {
        Log.d(Constants.TAG, repoName)
        Log.d(Constants.TAG, repoOwner)
    }
}

@AssistedFactory
interface RepoDetailsViewModelFactory {
    fun create(
        @Assisted("repoName") repoName: String,
        @Assisted("repoOwner") repoOwner: String
    ): RepoDetailsViewModel
}