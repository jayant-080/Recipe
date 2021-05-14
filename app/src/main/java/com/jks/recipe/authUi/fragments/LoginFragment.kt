package com.jks.recipe.authUi.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.jayant.xpost.other.EventObserver
import com.jayant.xpost.other.snackbar
import com.jks.recipe.mainUi.MainActivity
import com.jks.recipe.R
import com.jks.recipe.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var viewModel: AuthViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         login_lottieAnimationView.repeatCount=LottieDrawable.INFINITE

        //viewmodel
        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        subscribeToObserver()

        btnlogin.setOnClickListener {
            viewModel.loginUser(etloginemail.text.toString(),etloginpassword.text.toString())
        }

        tv_createnewaccount.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegiterFragment()
            )
        }

        tv_skiplogin.setOnClickListener {
            val sendToMain = Intent(requireActivity(),MainActivity::class.java)
            startActivity(sendToMain)
            requireActivity().finish()
        }
    }

    private fun subscribeToObserver() {
        viewModel.loginStatus.observe(viewLifecycleOwner,EventObserver(
            onError = {
                loginprogress.isVisible=false
                snackbar(it)
            },
            onLoading = {
                loginprogress.isVisible=true
            }
        ){
            loginprogress.isVisible=false
            val sendToMain = Intent(requireActivity(),
                MainActivity::class.java)
            startActivity(sendToMain)
            requireActivity().finish()
        })
    }
}