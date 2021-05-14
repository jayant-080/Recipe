package com.jks.recipe.authUi.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.jayant.xpost.other.EventObserver
import com.jayant.xpost.other.snackbar
import com.jks.recipe.R
import com.jks.recipe.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_register.*


@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var viewModel: AuthViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        register_lottieAnimationView.repeatCount=LottieDrawable.INFINITE

        //viewmodel
        viewModel = ViewModelProvider(requireActivity()).get(AuthViewModel::class.java)
        subscribeToObserver()

        btnregister.setOnClickListener {
            viewModel.registerUser(etregusername.text.toString(),etregemail.text.toString(),etregpassword.text.toString(),System.currentTimeMillis())
        }

        tv_login.setOnClickListener {
            findNavController().navigate(
                RegisterFragmentDirections.actionRegiterFragmentToLoginFragment()
            )
        }
    }

    private fun subscribeToObserver() {

        viewModel.registerStatus.observe(viewLifecycleOwner,EventObserver(
            onError = {
                registerprogress.isVisible=false
                snackbar(it)
            },
            onLoading = {
                registerprogress.isVisible=true
            }
        ){
            registerprogress.isVisible=false
            snackbar("account created successfully")
            findNavController().navigate(
                RegisterFragmentDirections.actionRegiterFragmentToLoginFragment()
            )
        })
    }
}