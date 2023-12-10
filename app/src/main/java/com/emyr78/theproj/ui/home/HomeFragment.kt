package com.emyr78.theproj.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.emyr78.theproj.constants.observe
import com.emyr78.theproj.databinding.FragmentHomeBinding
import com.emyr78.theproj.ui.home.adapters.HomeRepoAdapter
import com.emyr78.theproj.ui.home.state.HomeScreenState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeScreenViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        init()
        initObservers()
        return binding.root
    }

    private fun initObservers() {
        observe(viewModel.viewState, ::observeStates)
    }

    private fun init() {
        binding.rvRepoList.apply {
            adapter = HomeRepoAdapter(viewModel::onRepoSelected)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    private fun observeStates(state: HomeScreenState) {
        when(state){
            is HomeScreenState.HomeScreenStateLoading -> handleLoadingState(binding)
            is HomeScreenState.HomeScreenStateLoaded -> handleLoadedState(state,binding)
            is HomeScreenState.HomeScreenStateError -> handleErrorState(state,binding)
        }
    }

    private fun handleErrorState(state: HomeScreenState.HomeScreenStateError, binding: FragmentHomeBinding) {
        binding.rvRepoList.visibility = View.GONE
        binding.loadingIndicator.visibility = View.GONE

        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = state.message
    }

    private fun handleLoadedState(state: HomeScreenState.HomeScreenStateLoaded, binding: FragmentHomeBinding) {
        binding.tvError.visibility = View.GONE
        binding.loadingIndicator.visibility = View.GONE

        binding.rvRepoList.visibility = View.VISIBLE
        (binding.rvRepoList.adapter as HomeRepoAdapter).setRepoData(state.repos)
    }

    private fun handleLoadingState(binding: FragmentHomeBinding) {
        binding.tvError.visibility = View.GONE
        binding.rvRepoList.visibility = View.GONE
        binding.loadingIndicator.visibility = View.VISIBLE
    }
}