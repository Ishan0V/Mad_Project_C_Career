package com.ivpy.career.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ivpy.career.R
import com.ivpy.career.model.Mentor
import com.ivpy.career.utils.GlideLoader

open class ItemAdapter(val context: Context,var list:ArrayList<Mentor>)
    :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.mentor_item_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model =list[position]

        if (holder is MyViewHolder){
            GlideLoader(context).loadProductPicture(model.image,holder.itemView.findViewById(R.id.iv_mentor_image_display))
            holder.itemView.findViewById<TextView>(R.id.tv_mentor_name).text= model.name
            holder.itemView.findViewById<TextView>(R.id.tv_mentor_exp).text= "${model.experience} years of exp."
            holder.itemView.findViewById<TextView>(R.id.tv_mentor_institute).text= model.institute
            holder.itemView.findViewById<TextView>(R.id.tv_mentor_degree).text= model.degree

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(view: View) :RecyclerView.ViewHolder(view)
}