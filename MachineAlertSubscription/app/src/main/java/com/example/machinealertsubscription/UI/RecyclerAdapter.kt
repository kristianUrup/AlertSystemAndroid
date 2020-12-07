package com.example.machinealertsubscription.UI

import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.machinealertsubscription.BE.Alarm
import com.example.machinealertsubscription.BE.Machine
import com.example.machinealertsubscription.R
import kotlinx.android.synthetic.main.list_item.view.*


class RecyclerAdapter<T>(private val listOfItems: List<T>, private val ctx: Context): RecyclerView.Adapter<RecyclerAdapter.RecycleViewHolder>() {

    class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_description: TextView = itemView.txt_description
        val txt_code: TextView = itemView.txt_code
        var bool_isSubscribed: Boolean = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return RecycleViewHolder(itemView)
    }

    override fun getItemCount() = listOfItems.size


    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val currentItem = listOfItems[position]
        if(currentItem is Machine){
            if(currentItem.isSubscribed) {
                holder.txt_description.setTextColor(Color.GREEN)
            }
            holder.txt_description.text = currentItem.machineId
            holder.bool_isSubscribed = currentItem.isSubscribed
            setOnclickListeners(holder, currentItem.machineId, "")
        }
        else if(currentItem is Alarm){
            if(currentItem.isSubscribed) {
                holder.txt_code.setTextColor(Color.GREEN)
                holder.txt_description.setTextColor(Color.GREEN)
            }
            if(currentItem.errorDescription.length > 23) {
                val error : String = currentItem.errorDescription
                val errorRemoved: String = error.removeRange(23,error.length) + ".."
                    holder.txt_description.text = errorRemoved
                }
             else {
                holder.txt_description.text = currentItem.errorDescription
            }
            holder.txt_code.text = currentItem.code
            holder.bool_isSubscribed = currentItem.isSubscribed
            setOnclickListeners(holder, currentItem.id.toString(), currentItem.errorDescription)

        }
        else {
            //Do nothing
        }
    }
    fun setOnclickListeners(holder: RecycleViewHolder, id: String, errorDescription: String){
        val intent = Intent(ctx,ConfirmActivity::class.java)

        if(holder.txt_code.text != "")
        {
            intent.apply {
                putExtra("id", id)
                putExtra("description", errorDescription)
                putExtra("code", holder.txt_code.text)
                putExtra("typeOfAlert", "Alarms")
                putExtra("isSubscribed", holder.bool_isSubscribed)
            }
        }
        else{
            intent.apply {
                putExtra("id", holder.txt_description.text)
                putExtra("description", "")
                putExtra("code", "")
                putExtra("typeOfAlert", "Machines")
                putExtra("isSubscribed", holder.bool_isSubscribed)
            }
        }
        holder.txt_description.setOnClickListener {
            ctx.startActivity(intent)
        }
        holder.txt_code.setOnClickListener {
            ctx.startActivity(intent)
        }
    }
}