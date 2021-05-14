package com.jks.recipe.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
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

class  FavCuisinAdapter(val context: Context, val cuisinList:ArrayList<Cuisin>) : RecyclerView.Adapter<FavCuisinAdapter.FavCuiViewHolder>() {
    @Inject
    lateinit var glide : RequestManager

    class FavCuiViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val iv_Image : CircleImageView= itemView.findViewById(R.id.iv_fav_cui_image)
        val tv_title :TextView= itemView.findViewById(R.id.tv_fav_cui_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavCuiViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.fav_cuisin_layout,parent,false)
        return FavCuiViewHolder(view)
     }

    override fun getItemCount(): Int {
       return cuisinList.size
    }

    override fun onBindViewHolder(holder: FavCuiViewHolder, position: Int) {

        val cuisin = cuisinList[position]
        holder.apply {
        Glide.with(context).load(cuisin.imageUrl).into(iv_Image)
            tv_title.text=cuisin.name

        }

        holder.itemView.setOnClickListener {

         val sendToDetails = Intent(context,CuisinDetailsActivity::class.java)
            sendToDetails.putExtra("cuisinname",cuisin.name)
            sendToDetails.putExtra("recipe",cuisin.title)
            sendToDetails.putExtra("imageurl",cuisin.imageUrl)
            sendToDetails.putExtra("time",cuisin.timerequire)
            sendToDetails.putExtra("instruction",cuisin.instruction)
            sendToDetails.putExtra("uid",cuisin.uid)
            sendToDetails.putExtra("recipeUid",cuisin.recipieUid.toString())
            sendToDetails.putExtra("notes",cuisin.notes)


            context.startActivity(sendToDetails)
        }

    }

}