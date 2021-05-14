package com.jks.recipe.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.jks.recipe.R
import com.jks.recipe.mainUi.activity.CuisinDetailsActivity
import com.jks.recipe.models.Cuisin
import com.jks.recipe.models.Recipie
import de.hdodenhof.circleimageview.CircleImageView
import javax.inject.Inject

class  FavRecAdapter(val context: Context, val recipeList:List<Recipie>) : RecyclerView.Adapter<FavRecAdapter.FavRecViewHolder>() {
    @Inject
    lateinit var glide : RequestManager

    class FavRecViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val iv_Image : ImageView = itemView.findViewById(R.id.iv_fav_rec_image)
        val tv_title :TextView= itemView.findViewById(R.id.tv_fav_rec_name)
        val tv_instruction :TextView= itemView.findViewById(R.id.tv_fav_rec_instruction)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavRecViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.fav_recipe_layout,parent,false)
        return FavRecViewHolder(view)
     }

    override fun getItemCount(): Int {
       return recipeList.size
    }

    override fun onBindViewHolder(holder: FavRecViewHolder, position: Int) {

        val recipie = recipeList[position]
        holder.apply {
        Glide.with(context).load(recipie.imageUrl).into(iv_Image)
         tv_title.text=recipie.title
            tv_instruction.text=recipie.instruction
        }

        holder.itemView.setOnClickListener {

         val sendToDetails = Intent(context,CuisinDetailsActivity::class.java)
            sendToDetails.putExtra("cuisinname",recipie.category)
            sendToDetails.putExtra("recipe",recipie.title)
            sendToDetails.putExtra("imageurl",recipie.imageUrl)
            sendToDetails.putExtra("time",recipie.timerequire)
            sendToDetails.putExtra("instruction",recipie.instruction)
            sendToDetails.putExtra("uid",recipie.uid)
            sendToDetails.putExtra("recipeUid",recipie.recipeUid)
            sendToDetails.putExtra("notes",recipie.notes)

            context.startActivity(sendToDetails)
        }

    }

}