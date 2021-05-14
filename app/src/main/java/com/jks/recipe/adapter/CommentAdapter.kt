package com.jks.recipe.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jayant.xpost.other.safeCall
import com.jks.recipe.R
import com.jks.recipe.authUi.AuthActivity
import com.jks.recipe.mainUi.activity.CuisinDetailsActivity
import com.jks.recipe.models.Cuisin
import com.jks.recipe.models.comment
import com.jks.recipe.others.Resource
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class  CommentAdapter(val context: Context, val commentList:ArrayList<comment>) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    @Inject
    lateinit var glide: RequestManager

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val tv_name: TextView = itemView.findViewById(R.id.tv_cmmt_name)
        val tv_mssg: TextView = itemView.findViewById(R.id.tv_cmmt_mssg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):CommentViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.comment_item_layout, parent, false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        val comment = commentList[position]
        holder.apply {
            tv_name.text = comment.name
            tv_mssg.text=comment.message
        }


    }
}