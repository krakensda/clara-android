package com.pens.it.d4b2018.clara_android.mvvm.view.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pens.it.d4b2018.clara_android.databinding.FragmentProfileBinding
import com.pens.it.d4b2018.clara_android.mvvm.data.models.UserResponse
import com.pens.it.d4b2018.clara_android.mvvm.data.remote.APIService
import com.pens.it.d4b2018.clara_android.mvvm.data.remote.Resource
import com.pens.it.d4b2018.clara_android.mvvm.data.repositories.UserRepository
import com.pens.it.d4b2018.clara_android.mvvm.view.base.BaseFragment
import com.pens.it.d4b2018.clara_android.mvvm.view.utils.visible
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainFragment: BaseFragment<MainViewModel, FragmentProfileBinding, UserRepository>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressBar.visible(false)

        viewModel.getUser()

        viewModel.user.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visible(false)
                    Log.e("clara", it.value.toString())
                    updateUI(it.value)
                }

                is Resource.Loading -> {
                    binding.progressBar.visible(true)
                }
            }
        })

        binding.profileLogoutButton.setOnClickListener {
            logout()
        }
    }

    private fun updateUI(userResponse: UserResponse) {
        val user = userResponse.userrr
        with(binding) {
            profileUserNameTextview.text = user.fullName
            profileUserClassTextview.text = user.grade
            profileUserNrpTextview.text = user.nrp
        }
    }

    override fun getViewModel() = MainViewModel::class.java

    override fun getFragmentBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
    ) = FragmentProfileBinding.inflate(inflater, container, false)

    override fun getFragmentRepository(): UserRepository {
        val token = runBlocking { userPreferences.authToken.first() }
        val api = retrofitClient.buildApi(APIService::class.java, token)
        return UserRepository(api)
    }

}