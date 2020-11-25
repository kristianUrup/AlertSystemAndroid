package com.example.machinealertsubscription.UI

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.machinealertsubscription.BE.Alarm
import com.example.machinealertsubscription.BE.Machine
import com.example.machinealertsubscription.R
import kotlinx.android.synthetic.main.list_item.view.*
import org.w3c.dom.Text

class RecyclerAdapter<T>(private val listOfItems: List<T>, private val ctx: Context): RecyclerView.Adapter<RecyclerAdapter.RecycleViewHolder>() {

    class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_id: TextView = itemView.txt_id
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
                holder.itemView.setBackgroundColor(Color.GREEN)

            }
            holder.txt_description.text = currentItem.machineId
            holder.bool_isSubscribed = currentItem.isSubscribed
            setOnclickListeners(holder)
        }
        else if(currentItem is Alarm){
            if(currentItem.isSubscribed) {
                holder.itemView.setBackgroundColor(Color.GREEN)
            }
            holder.txt_id.text = currentItem.id.toString()
            holder.txt_description.text = currentItem.errorDescription
            holder.txt_code.text = currentItem.code
            holder.bool_isSubscribed = currentItem.isSubscribed
            setOnclickListeners(holder)
        }
        else {
            //Do nothing
        }

    }

    fun setOnclickListeners(holder: RecycleViewHolder){
        val intent = Intent(ctx,ConfirmActivity::class.java)
        if(holder.txt_id.text != "" && holder.txt_code.text != "")
        {
            intent.apply {
                putExtra("id", holder.txt_id.text)
                putExtra("description", holder.txt_description.text)
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



        holder.txt_id.setOnClickListener {
            ctx.startActivity(intent)
        }
        holder.txt_description.setOnClickListener {
            ctx.startActivity(intent)
        }
        holder.txt_code.setOnClickListener {
            ctx.startActivity(intent)
        }
    }


}