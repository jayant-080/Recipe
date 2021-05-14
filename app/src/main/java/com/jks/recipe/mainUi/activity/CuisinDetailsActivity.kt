package com.jks.recipe.mainUi.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jayant.xpost.other.EventObserver
import com.jks.recipe.R
import com.jks.recipe.adapter.CommentAdapter
import com.jks.recipe.authUi.AuthActivity
import com.jks.recipe.models.User
import com.jks.recipe.viewmodel.AddFavRecipeViewModel
import com.jks.recipe.viewmodel.CommentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_cuisin_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class CuisinDetailsActivity : AppCompatActivity() {

    private lateinit var viewModel:AddFavRecipeViewModel
    private lateinit var viewModelComment:CommentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuisin_details)
        viewModelComment=ViewModelProvider(this).get(CommentViewModel::class.java)

        val cuisinName= intent.getStringExtra("cuisinname").toString()
        val recipe= intent.getStringExtra("recipe").toString()
        val image= intent.getStringExtra("imageurl").toString()
        val instruction= intent.getStringExtra("instruction").toString()
        val time= intent.getStringExtra("time").toString()
        val uid= intent.getStringExtra("uid").toString()
        val recipeuid= intent.getStringExtra("recipeUid").toString()
        val notes= intent.getStringExtra("notes").toString()

        subscribeToComment(recipeuid)

        Glide.with(this).load(image).into(iv_detailImage)
        tv_cuisinname.text=cuisinName
        tv_detail_dummy.text="Recipe : "+recipe
        tv_details_instructions.text=instruction
        tv_detail_time.text="Time requires : "+time
        tv_details_notes.text=notes

        viewModelComment.getAllComments(recipeuid)


        if(FirebaseAuth.getInstance().currentUser!=null){
            tv_detail_note.isVisible=true
           tv_details_notes.isVisible=true
        }else{
            tv_detail_note.isVisible=false
            tv_details_notes.isVisible=false
        }

        btn_cmmnt_send.setOnClickListener {

            if(FirebaseAuth.getInstance().currentUser!=null){

                CoroutineScope(Dispatchers.IO).launch {
                    val user = FirebaseFirestore.getInstance()
                        .collection("users").document(FirebaseAuth.getInstance().uid!!).get().await().toObject(User::class.java)!!
                   val name=user.userName.toString()
                    viewModelComment.addComment(recipeuid,name,et_comments.text.toString())
                }




            }else{

                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
                alertDialog.setTitle("You are not logged in")
                alertDialog.setMessage("login to continue")
                alertDialog.setPositiveButton(
                    "login"
                ) { _, _ ->
                    val sendToAuth = Intent(this, AuthActivity::class.java)
                    startActivity(sendToAuth)
                    finish()
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



        iv_addRecFav.setOnClickListener {

            if(FirebaseAuth.getInstance().currentUser!=null){
                viewModel=ViewModelProvider(this).get(AddFavRecipeViewModel::class.java)
                subscribeToObserver()
                viewModel.addRecToFav(recipe,cuisinName,instruction,image,System.currentTimeMillis(),time,uid,recipeuid,notes)

            }else{
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(this)
                alertDialog.setTitle("You are not logged in")
                alertDialog.setMessage("login to continue")
                alertDialog.setPositiveButton(
                    "login"
                ) { _, _ ->
                    val sendToAuth = Intent(this, AuthActivity::class.java)
                    startActivity(sendToAuth)
                    finish()
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



    private fun subscribeToComment(recipeUid:String) {
        viewModelComment.addCommentStatus.observe(this,EventObserver(
            onError = {
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            },
            onLoading = {

            }
        ){
            Toast.makeText(this,"comment added",Toast.LENGTH_LONG).show()
            viewModelComment.getAllComments(recipeUid)
        })

        viewModelComment.getCommentStatus.observe(this,EventObserver(
            onError = {
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
                comment_progressBar.isVisible=false
            },
            onLoading = {
                comment_progressBar.isVisible=true
            }
        ){
            comment_progressBar.isVisible=false
            val adapters = CommentAdapter(this,it)
            rv_comments.adapter=adapters
            adapters.notifyDataSetChanged()

        })
    }

    private fun subscribeToObserver() {

        viewModel.addrectoFav.observe(this,EventObserver(
            onError = {
               Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            },
            onLoading = {

            }
        ){
            Toast.makeText(this,"added to favourite",Toast.LENGTH_LONG).show()
        })
    }


}