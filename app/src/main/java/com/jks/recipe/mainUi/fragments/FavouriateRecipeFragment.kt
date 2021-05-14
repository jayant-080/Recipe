package com.jks.recipe.mainUi.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.jayant.xpost.other.EventObserver
import com.jayant.xpost.other.snackbar
import com.jks.recipe.R
import com.jks.recipe.adapter.FavRecAdapter
import com.jks.recipe.authUi.AuthActivity
import com.jks.recipe.viewmodel.AddFavRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_fav_recipe.*

@AndroidEntryPoint
class FavouriateRecipeFragment:Fragment(R.layout.fragment_fav_recipe) {

    private lateinit var viewModel : AddFavRecipeViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(AddFavRecipeViewModel::class.java)
        subscribeToObserber()

        if(FirebaseAuth.getInstance().currentUser!=null){
            viewModel.getAllFavRecipe()
        }else{
            val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
            alertDialog.setTitle("You are not logged in")
            alertDialog.setMessage("login to continue")
            alertDialog.setPositiveButton(
                "login"
            ) { _, _ ->
                val sendToAuth = Intent(requireActivity(), AuthActivity::class.java)
                startActivity(sendToAuth)
                requireActivity().finish()
            }
            alertDialog.setNegativeButton(
                "Cancel"
            ) { _, _ ->findNavController().navigate(
                FavouriateRecipeFragmentDirections.actionFavouriateRecipeFragmentToCuisineFragment()
            )
            }
            val alert: AlertDialog = alertDialog.create()
            alert.setCanceledOnTouchOutside(false)
            alert.show()
        }

    }

    private fun subscribeToObserber() {
       viewModel.getrecFav.observe(viewLifecycleOwner,EventObserver(
           onError = {
               snackbar(it)
               fav_recipe_progressBar.isVisible=false
           },
           onLoading = {
               fav_recipe_progressBar.isVisible=true
           }
       ){
           fav_recipe_progressBar.isVisible=false
           val adapters = FavRecAdapter(requireContext(),it.toList())
           rv_fav_recipe.adapter=adapters
           adapters.notifyDataSetChanged()
       })
    }
}