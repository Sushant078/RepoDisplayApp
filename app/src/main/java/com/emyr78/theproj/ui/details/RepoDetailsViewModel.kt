package com.emyr78.theproj.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.emyr78.theproj.constants.handleApiResponse
import com.emyr78.theproj.models.ContributorsItem
import com.emyr78.theproj.repo.AppRepository
import com.emyr78.theproj.ui.details.state.RepoContributorsViewState
import com.emyr78.theproj.ui.details.state.RepoContributorsViewStateError
import com.emyr78.theproj.ui.details.state.RepoContributorsViewStateLoaded
import com.emyr78.theproj.ui.details.state.RepoContributorsViewStateLoading
import com.emyr78.theproj.ui.details.state.RepoInfoViewState
import com.emyr78.theproj.ui.details.state.RepoInfoViewStateError
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
            appRepository.getRepoInfo(repoOwner, repoName).collect { apiResponse ->
                handleApiResponse(
                    apiResponse,
                    apiCallSuccess = { repoInfo ->
                        _repoInfoViewState.value = RepoInfoViewStateLoaded(
                            repoInfo.name,
                            repoInfo.description,
                            repoInfo.createdDate,
                            repoInfo.updatedDate
                        )
                    },
                    apiCallError = { error ->
                        _repoInfoViewState.value = RepoInfoViewStateError(error)
                    },
                    apiCallLoading = {
                        _repoInfoViewState.value = RepoInfoViewStateLoading
                    }
                )
            }
        }
    }

    fun getContributorsInfo(){
        viewModelScope.launch {
            appRepository.getContributors(repoOwner, repoName).collect { apiResponse ->
                handleApiResponse(
                    apiResponse,
                    apiCallSuccess = { contributorsList ->
                        _contributorsListViewState.value = RepoContributorsViewStateLoaded(
                            contributors = contributorsList.map {
                                ContributorsItem(
                                    it.id,
                                    it.login,
                                    it.avatarUrl
                                )
                            }
                        )
                    },
                    apiCallError = { error ->
                        _contributorsListViewState.value = RepoContributorsViewStateError(error)
                    },
                    apiCallLoading = {
                        _contributorsListViewState.value = RepoContributorsViewStateLoading
                    }
                )
            }
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