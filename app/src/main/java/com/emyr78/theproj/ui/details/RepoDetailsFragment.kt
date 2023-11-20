package com.emyr78.theproj.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.emyr78.theproj.databinding.FragmentRepoDetailsBinding
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
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepoDetailsBinding.inflate(layoutInflater, container, false)
        repoDetailsViewModel.getArgs()
        return binding.root
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