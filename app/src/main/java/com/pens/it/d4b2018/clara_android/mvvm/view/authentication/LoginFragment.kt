package com.pens.it.d4b2018.clara_android.mvvm.view.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.pens.it.d4b2018.clara_android.R
import com.pens.it.d4b2018.clara_android.databinding.FragmentLoginBinding
import com.pens.it.d4b2018.clara_android.mvvm.data.models.LoginRequest
import com.pens.it.d4b2018.clara_android.mvvm.data.remote.APIService
import com.pens.it.d4b2018.clara_android.mvvm.data.remote.Resource
import com.pens.it.d4b2018.clara_android.mvvm.data.repositories.AuthRepository
import com.pens.it.d4b2018.clara_android.mvvm.view.base.BaseFragment
import com.pens.it.d4b2018.clara_android.mvvm.view.main.MainActivity
import com.pens.it.d4b2018.clara_android.mvvm.view.utils.enable
import com.pens.it.d4b2018.clara_android.mvvm.view.utils.startNewActivity
import com.pens.it.d4b2018.clara_android.mvvm.view.utils.visible
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<AuthViewModel, FragmentLoginBinding, AuthRepository>() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.progressBar.visible(false)
        binding.signinBtn.enable(false)

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {
            binding.progressBar.visible(false)
            when (it) {
                is Resource.Success -> {
                    viewModel.saveAuthToken(it.value.token)
                    requireActivity().startNewActivity(MainActivity::class.java)

                }
                is Resource.Failure -> {
                    Toast.makeText(requireContext(), "Login Failure", Toast.LENGTH_LONG).show()
                }
            }
        })

        binding.emailInput.addTextChangedListener {
            val email = it.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            binding.signinBtn.enable(email.isNotEmpty() && password.isNotEmpty())
        }

        binding.passwordInput.addTextChangedListener {
            val email = binding.emailInput.text.toString().trim()
            val password = it.toString().trim()
            binding.signinBtn.enable(email.isNotEmpty() && password.isNotEmpty())
        }

        setButtonOnClick()

    }

    override fun getViewModel() = AuthViewModel::class.java

    override fun getFragmentBinding(inflater: LayoutInflater, container: ViewGroup?) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun getFragmentRepository() = AuthRepository(retrofitClient.buildApi(APIService::class.java), userPreferences)

    private fun setButtonOnClick() {
        val thisFragment = this

        binding.signinBtn.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            val request = LoginRequest(email, password)
            // @todo add input validations
            binding.progressBar.visible(true)
            viewModel.login(request)
        }

        binding.buttonSignupPage.setOnClickListener {
            NavHostFragment.findNavController(thisFragment).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

}