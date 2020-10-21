package com.example.machinealertsubscription.UI

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.machinealertsubscription.BE.Alert
import com.example.machinealertsubscription.BE.Machine
import com.example.machinealertsubscription.R
import kotlinx.android.synthetic.main.list_item.view.*
import kotlin.reflect.typeOf

class RecyclerAdapter<T>(private val identifier: String, private val listOfItems: List<T>): RecyclerView.Adapter<RecyclerAdapter.RecycleViewHolder>() {

    public class RecycleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txt_id: TextView = itemView.txt_id
        val txt_description: TextView = itemView.txt_description
        val txt_machineId: TextView = itemView.txt_machineId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return RecycleViewHolder(itemView);
    }

    override fun getItemCount() = listOfItems.size


    override fun onBindViewHolder(holder: RecycleViewHolder, position: Int) {
        val currentItem = listOfItems[position]

        if(currentItem is Machine){
            holder.txt_description.setText(currentItem.ID)
        }
        else if(currentItem is Alert){
            holder.txt_id.setText(currentItem.ID)
            holder.txt_description.setText(currentItem.errorDescription)
            holder.txt_machineId.setText(currentItem.machine.ID)
        }
        else {
            //Do nothing
        }

    }
}