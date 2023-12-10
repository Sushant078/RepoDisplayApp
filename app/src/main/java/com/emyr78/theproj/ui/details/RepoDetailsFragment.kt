package com.emyr78.theproj.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.emyr78.theproj.constants.observe
import com.emyr78.theproj.databinding.FragmentRepoDetailsBinding
import com.emyr78.theproj.ui.details.adapters.ContributorsAdapter
import com.emyr78.theproj.ui.details.state.RepoContributorsViewState
import com.emyr78.theproj.ui.details.state.RepoContributorsViewStateLoaded
import com.emyr78.theproj.ui.details.state.RepoContributorsViewStateLoading
import com.emyr78.theproj.ui.details.state.RepoInfoViewState
import com.emyr78.theproj.ui.details.state.RepoInfoViewStateLoaded
import com.emyr78.theproj.ui.details.state.RepoInfoViewStateLoading
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RepoDetailsFragment : Fragment() {
    private lateinit var binding: FragmentRepoDetailsBinding

    @Inject
    lateinit var repoDetailsViewModelFactory: RepoDetailsViewModelFactory

    private val repoDetailsViewModel: RepoDetailsViewModel by viewModels {
        RepoDetailsViewModel.provideFactory(
            repoDetailsViewModelFactory,
            arguments?.getString("repo_name", "") ?: "",
            arguments?.getString("repo_owner", "") ?: ""
        ) // Can add additional handling when repoOwner or repoName is empty or invalid
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentRepoDetailsBinding.inflate(layoutInflater, container, false)
        initObservers()
        fetchRepo()
        return binding.root
    }

    private fun initObservers() {
        observe(
            repoDetailsViewModel.repoInfoStateUpdate,
            ::handleRepoInfoStates
        )

        observe(
            repoDetailsViewModel.contributorsListInfoStateUpdate,
            ::handleRepoContributorsViewStates
        )
    }

    private fun fetchRepo(){
        repoDetailsViewModel.getRepositoryInfo()
        repoDetailsViewModel.getContributorsInfo()
    }

    private fun handleRepoInfoStates(state: RepoInfoViewState) {
        when (state) {
            is RepoInfoViewStateLoading -> handleInfoLoadingState(binding)
            is RepoInfoViewStateLoaded -> handleInfoLoadedState(binding, state)
        }
    }

    private fun handleRepoContributorsViewStates(state: RepoContributorsViewState) {
        when (state) {
            is RepoContributorsViewStateLoading -> handleContributorsLoadingState(binding)
            is RepoContributorsViewStateLoaded -> handleContributorsLoadedState(binding, state)
        }
    }

    private fun handleContributorsLoadedState(
        binding: FragmentRepoDetailsBinding,
        state: RepoContributorsViewStateLoaded,
    ) {
        binding.contributorLoadingIndicator.visibility = View.GONE
        binding.contributorList.apply {
            visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(context)
            adapter = ContributorsAdapter(state.contributors)
        }
    }

    private fun handleContributorsLoadingState(binding: FragmentRepoDetailsBinding) {
        binding.contributorLoadingIndicator.visibility = View.VISIBLE
        binding.contributorList.visibility = View.GONE
    }

    private fun handleInfoLoadedState(
        binding: FragmentRepoDetailsBinding,
        state: RepoInfoViewStateLoaded,
    ) {
        binding.detailsLoadingIndicator.visibility = View.GONE
        binding.toggleInfoTextVisibility(View.VISIBLE)

        binding.repoName.text = state.repoName
        binding.repoDescription.text = state.repoDescription
        binding.creationDate.text = state.createdDate
        binding.updatedDate.text = state.updatedDate
    }

    private fun handleInfoLoadingState(binding: FragmentRepoDetailsBinding) {
        binding.detailsLoadingIndicator.visibility = View.VISIBLE
        binding.toggleInfoTextVisibility(View.INVISIBLE)
    }

    private fun FragmentRepoDetailsBinding.toggleInfoTextVisibility(visibility: Int) {
        repoName.visibility = visibility
        repoDescription.visibility = visibility
        creationDateLabel.visibility = visibility
        creationDate.visibility = visibility
        updatedDateLabel.visibility = visibility
        updatedDate.visibility = visibility
    }

    companion object {
        fun newInstance(repoOwner: String, repoName: String) = RepoDetailsFragment().apply {
            arguments = Bundle().apply {
                putString("repo_name", repoName)
                putString("repo_owner", repoOwner)
            }
        }
    }
}