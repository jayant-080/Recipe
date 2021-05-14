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
import androidx.core.view.isVisible
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

class  CuisinAdapter(val context: Context,val cuisinList:ArrayList<Cuisin>) : RecyclerView.Adapter<CuisinAdapter.CuisinViewHolder>() {
    @Inject
    lateinit var glide: RequestManager

    class CuisinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv_maincuisinImage: CircleImageView = itemView.findViewById(R.id.iv_main_cuisinimage)
        val iv_addToFavCuisin: ImageView = itemView.findViewById(R.id.iv_addfavcui)
        val tv_title: TextView = itemView.findViewById(R.id.tv_main_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuisinViewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.cuisin_item_layout, parent, false)
        return CuisinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cuisinList.size
    }

    override fun onBindViewHolder(holder: CuisinViewHolder, position: Int) {

        val cuisin = cuisinList[position]
        holder.apply {
            Glide.with(context).load(cuisin.imageUrl).into(iv_maincuisinImage)
            tv_title.text = cuisin.name
        }


        holder.iv_maincuisinImage.setOnClickListener {

            val sendToDetails = Intent(context, CuisinDetailsActivity::class.java)
            sendToDetails.putExtra("cuisinname", cuisin.name)
            sendToDetails.putExtra("recipe", cuisin.title)
            sendToDetails.putExtra("imageurl", cuisin.imageUrl)
            sendToDetails.putExtra("time", cuisin.timerequire)
            sendToDetails.putExtra("instruction", cuisin.instruction)
            sendToDetails.putExtra("uid", cuisin.uid)
            sendToDetails.putExtra("recipeUid", cuisin.recipieUid)
            sendToDetails.putExtra("notes", cuisin.notes)
            context.startActivity(sendToDetails)
        }

        holder.iv_addToFavCuisin.setOnClickListener {

            if (FirebaseAuth.getInstance().currentUser != null) {
                CoroutineScope(Dispatchers.IO).launch {

                    val cuisin = Cuisin(
                        uid = cuisin.uid,
                        name = cuisin.name,
                        title = cuisin.title,
                        timerequire = cuisin.timerequire,
                        instruction = cuisin.instruction,
                        date = cuisin.date,
                        imageUrl = cuisin.imageUrl,
                        recipieUid = cuisin.recipieUid,
                        notes = cuisin.notes

                        )
                    FirebaseFirestore.getInstance().collection("favcuisin")
                        .document(FirebaseAuth.getInstance().uid!!).collection("myfavcuisin")
                        .document(UUID.randomUUID().toString()).set(cuisin).await()

                    withContext(Dispatchers.Main){
                        Toast.makeText(context,"added to favourite cuisin",Toast.LENGTH_LONG).show()
                    }
                }

            }else{
                val alertDialog: AlertDialog.Builder = AlertDialog.Builder(context)
                alertDialog.setTitle("You are not logged in")
                alertDialog.setMessage("login to continue")
                alertDialog.setPositiveButton(
                    "login"
                ) { _, _ ->
                    val sendToAuth = Intent(context, AuthActivity::class.java)
                    context.startActivity(sendToAuth)
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
}