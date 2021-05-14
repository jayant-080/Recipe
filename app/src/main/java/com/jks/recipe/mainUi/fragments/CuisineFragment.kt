package com.jks.recipe.mainUi.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.jayant.xpost.other.EventObserver
import com.jayant.xpost.other.snackbar
import com.jks.recipe.R
import com.jks.recipe.adapter.CuisinAdapter
import com.jks.recipe.adapter.FavCuisinAdapter
import com.jks.recipe.authUi.AuthActivity
import com.jks.recipe.viewmodel.AddFavCuisinViewModel
import com.jks.recipe.viewmodel.CuisinViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fav_cuisin_layout.*
import kotlinx.android.synthetic.main.fragment_cuisine.*
import kotlinx.android.synthetic.main.fragment_fav_cuisin.*

@AndroidEntryPoint
class CuisineFragment :Fragment(R.layout.fragment_cuisine){

    private lateinit var viewModel:CuisinViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(CuisinViewModel::class.java)
        susbcribeToObserver()
        viewModel.getAllCuisin()

        if(FirebaseAuth.getInstance().currentUser!=null){
            iv_logout.isVisible=true
        }else{
            iv_logout.isVisible=false
        }

        iv_search.setOnClickListener {
            findNavController().navigate(
                CuisineFragmentDirections.actionCuisineFragmentToSearchFragment()
            )
        }

        iv_logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireActivity(),AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }


    private fun susbcribeToObserver() {

        viewModel.getallCuisinStatus.observe(viewLifecycleOwner,EventObserver(
            onError = {
                cuisin_progressBar.isVisible=false
                snackbar(it)
            },
            onLoading = {
                cuisin_progressBar.isVisible=true
            }
        ){
            cuisin_progressBar.isVisible=false
            val adapters= CuisinAdapter(requireContext(),it)
            rv_cuisin.adapter=adapters
            adapters.notifyDataSetChanged()
        })


    }
}