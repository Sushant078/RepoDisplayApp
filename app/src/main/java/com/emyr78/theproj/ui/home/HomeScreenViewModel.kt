package com.emyr78.theproj.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emyr78.theproj.constants.handleApiResponse
import com.emyr78.theproj.models.RepoItem
import com.emyr78.theproj.repo.AppRepository
import com.emyr78.theproj.ui.home.state.HomeScreenState
import com.emyr78.theproj.ui.screen.ActivityDrivenScreenNavigator
import com.emyr78.theproj.ui.screen.DetailsScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val screenNavigator: ActivityDrivenScreenNavigator
) : ViewModel() {
    /*TODO: Should migrate to Flows? LiveData enforces that the value only be updated on main thread
       via setValue and postValue,can migrate to flows but will have to enforce the same mechanism
       to avoid touching value from any other thread since UI relies on the corresponding value*/
    private val _viewState: MutableLiveData<HomeScreenState> =
        MutableLiveData(HomeScreenState.HomeScreenStateLoading)
    val viewState: LiveData<HomeScreenState> = _viewState

    init {
        viewModelScope.launch {
            appRepository.getTopRepos().collect { apiResponse ->
                handleApiResponse(
                    apiResponse,
                    apiCallSuccess = { itemsList ->
                        _viewState.value = itemsList.items.let {
                            HomeScreenState.HomeScreenStateLoaded(
                                repos = it.map { repoApiModel ->
                                    RepoItem(
                                        repoApiModel.owner.login,
                                        repoApiModel.name,
                                        repoApiModel.description,
                                        repoApiModel.stargazersCount,
                                        repoApiModel.forksCount
                                    )
                                }
                            )
                        }
                    },
                    apiCallError = { error ->
                        _viewState.value = HomeScreenState.HomeScreenStateError(error)
                    },
                    apiCallLoading = {
                        _viewState.value = HomeScreenState.HomeScreenStateLoading
                    }
                )
            }
        }
    }

    fun onRepoSelected(repoOwner: String, repoName: String) {
        screenNavigator.goToScreen(DetailsScreen(repoOwner, repoName))
    }
}