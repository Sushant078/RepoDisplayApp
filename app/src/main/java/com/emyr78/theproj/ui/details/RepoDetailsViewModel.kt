package com.emyr78.theproj.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.emyr78.theproj.models.ContributorsItem
import com.emyr78.theproj.repo.AppRepository
import com.emyr78.theproj.ui.details.state.RepoContributorsViewState
import com.emyr78.theproj.ui.details.state.RepoContributorsViewStateLoaded
import com.emyr78.theproj.ui.details.state.RepoContributorsViewStateLoading
import com.emyr78.theproj.ui.details.state.RepoInfoViewState
import com.emyr78.theproj.ui.details.state.RepoInfoViewStateLoaded
import com.emyr78.theproj.ui.details.state.RepoInfoViewStateLoading
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

/* HiltViewModel doesn't support AssistedInjection yet -> https://github.com/google/dagger/issues/2287 */
class RepoDetailsViewModel @AssistedInject constructor(
    @Assisted("repoName") private val repoName: String,
    @Assisted("repoOwner") private val repoOwner: String,
    private val appRepository: AppRepository
) : ViewModel() {
    //TODO: Migrate to Flows
    private var _repoInfoViewState: MutableLiveData<RepoInfoViewState> = MutableLiveData(RepoInfoViewStateLoading)
    var repoInfoStateUpdate: LiveData<RepoInfoViewState> = _repoInfoViewState

    private var _contributorsListViewState: MutableLiveData<RepoContributorsViewState> = MutableLiveData(RepoContributorsViewStateLoading)
    var contributorsListInfoStateUpdate: LiveData<RepoContributorsViewState> = _contributorsListViewState

    fun getRepositoryInfo(){
        viewModelScope.launch {
            val repoInfo = appRepository.getRepoInfo(repoOwner,repoName)
            _repoInfoViewState.value = RepoInfoViewStateLoaded(
                repoInfo.name,
                repoInfo.description,
                repoInfo.createdDate,
                repoInfo.updatedDate
            )
        }
    }

    fun getContributorsInfo(){
        viewModelScope.launch {
            val contributorsList = appRepository.getContributors(repoOwner,repoName)
            _contributorsListViewState.value = RepoContributorsViewStateLoaded(
                contributors = contributorsList.map {
                    ContributorsItem(
                        it.id,
                        it.login,
                        it.avatarUrl
                    )
                }
            )
        }
    }

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
}

@AssistedFactory
interface RepoDetailsViewModelFactory {
    fun create(
        @Assisted("repoName") repoName: String,
        @Assisted("repoOwner") repoOwner: String
    ): RepoDetailsViewModel
}