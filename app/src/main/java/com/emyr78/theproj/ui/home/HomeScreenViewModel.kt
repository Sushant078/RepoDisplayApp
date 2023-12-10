package com.emyr78.theproj.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
): ViewModel(){
    //TODO: Migrate to Flows
    private val _viewState : MutableLiveData<HomeScreenState> = MutableLiveData(HomeScreenState.HomeScreenStateLoading)
    val viewState: LiveData<HomeScreenState> = _viewState

    init {
        viewModelScope.launch {
            val repos = appRepository.getTopRepos()
            _viewState.value = HomeScreenState.HomeScreenStateLoaded(
                repos = repos.map {
                    RepoItem(
                        it.owner.login,
                        it.name,
                        it.description,
                        it.stargazersCount,
                        it.forksCount
                    )
                }
            )
        }
    }

    fun onRepoSelected(repoOwner:String,repoName:String){
        screenNavigator.goToScreen(DetailsScreen(repoOwner,repoName))
    }
}