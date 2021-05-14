package com.jks.recipe.mainUi.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import com.jayant.xpost.other.EventObserver
import com.jayant.xpost.other.snackbar
import com.jks.recipe.R
import com.jks.recipe.authUi.AuthActivity
import com.jks.recipe.viewmodel.AddRecipeViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_addrecipie.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddRecipiFragment:Fragment(R.layout.fragment_addrecipie) {
    @Inject
    lateinit var glide : RequestManager

    private lateinit var mylauncher: ActivityResultLauncher<String>
    private  lateinit var viewModel:AddRecipeViewModel
    var isadd=false

    private val cropImageContract = object : ActivityResultContract<String, Uri?>(){
        override fun createIntent(context: Context, input: String?): Intent {
            return CropImage.activity()
                .setAspectRatio(1,1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(requireContext())
        }

        override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
            return  CropImage.getActivityResult(intent).uri
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mylauncher= registerForActivityResult(cropImageContract){
            viewModel.setImage(it!!)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel=ViewModelProvider(requireActivity()).get(AddRecipeViewModel::class.java)
        subscribeToObserver()

        iv_addRecipiImage.setOnClickListener {
            mylauncher.launch("image/*")
        }

        bttn_upload_recipie.setOnClickListener {
            if(FirebaseAuth.getInstance().currentUser!=null) {

                if(imageuri==null){
                    snackbar("choose image")
                    return@setOnClickListener
                }else if(et_rec_title.text.toString().isEmpty() || et_rec_category.text.toString().isEmpty() || et_rec_instruction.text.toString().isEmpty() || et_rec_time.text.toString().isEmpty() || et_rec_notes.text.toString().isEmpty()){
                    snackbar("all field are required")
                }else{
                    isadd=true
                    imageuri?.let {uri->
                        if(imageuri!=null){
                            viewModel.setImage(uri)
                            viewModel.addRecipe(
                                imageuri!!,
                                et_rec_title.text.toString(),
                                et_rec_category.text.toString().toLowerCase(Locale.getDefault())
                                ,
                                et_rec_instruction.text.toString(),
                                System.currentTimeMillis(),
                                et_rec_time.text.toString(),
                                et_rec_notes.text.toString()

                            )
                        }else{
                            isadd=false
                            snackbar("choose image")
                        }

                    }

                }



            }else{
                isadd=false

                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
                alertDialog.setTitle("You are not logged in")
                alertDialog.setMessage("login to continue")
                alertDialog.setPositiveButton(
                    "login"
                ) { _, _ ->
                    val sendToAuth = Intent(requireActivity(),AuthActivity::class.java)
                    startActivity(sendToAuth)
                    requireActivity().finish()
                }
                alertDialog.setNegativeButton(
                    "Cancel"
                ) { _, _ ->
                }
                val alert: AlertDialog = alertDialog.create()
                alert.setCanceledOnTouchOutside(false)
                alert.show()

            }
        }
    }
    private  var imageuri: Uri?= null

    private fun subscribeToObserver() {

        viewModel.imageStatus.observe(viewLifecycleOwner){
                imageuri=it
                glide.load(imageuri).into(iv_addRecipiImage)

        }
        viewModel.addrecStatus.observe(viewLifecycleOwner,EventObserver(
            onError = {
                addrecprogress.isVisible=false
                Toast.makeText(requireActivity(),it,Toast.LENGTH_LONG).show()
            },
            onLoading = {
                addrecprogress.isVisible=true
            }
        ){

            if(isadd) {
                addrecprogress.isVisible = false
                snackbar("recipe added")
                et_rec_title.setText("")
                et_rec_category.setText("")
                et_rec_instruction.setText("")
                et_rec_time.setText("")
                et_rec_notes.setText("")
                iv_addRecipiImage.setImageResource(R.drawable.ic_baseline_insert_photo_24)
            }
        })

    }




}