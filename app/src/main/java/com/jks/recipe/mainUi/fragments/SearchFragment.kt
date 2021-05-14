package com.jks.recipe.mainUi.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.jayant.xpost.other.EventObserver
import com.jayant.xpost.other.snackbar
import com.jks.recipe.R
import com.jks.recipe.adapter.CuisinAdapter
import com.jks.recipe.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class SearchFragment : Fragment(R.layout.fragment_search) {

    private lateinit var viewModel:SearchViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(SearchViewModel::class.java)
        subscribeToObserver()

        var job: Job?=null
        et_search.addTextChangedListener {
            job?.cancel()
            job= lifecycleScope.launch {
                delay(1000L)
                it?.let {qm->
                    viewModel.searchCuisin(qm.toString().toLowerCase(Locale.getDefault()))
                }
            }

        }
    }

    private fun subscribeToObserver() {
        viewModel.searchStatus.observe(viewLifecycleOwner,EventObserver(
            onError = {
               snackbar(it)
                searchprogressBar.isVisible=false
            },
            onLoading = {
                searchprogressBar.isVisible=true
            }
        ){
            searchprogressBar.isVisible=false
            val adapters = CuisinAdapter(requireContext(),it)
            rv_search.adapter=adapters
            adapters.notifyDataSetChanged()
        })
    }
}