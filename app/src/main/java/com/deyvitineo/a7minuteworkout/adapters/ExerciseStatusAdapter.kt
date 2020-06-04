package com.deyvitineo.a7minuteworkout.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.deyvitineo.a7minuteworkout.ExerciseModel
import com.deyvitineo.a7minuteworkout.R
import kotlinx.android.synthetic.main.item_exercise_status.view.*

class ExerciseStatusAdapter(val items: ArrayList<ExerciseModel>, val context: Context) :
    RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_exercise_status, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.mItem.text = item.id.toString()
        if(item.isSelected){
            holder.mItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_thin)
            holder.mItem.setTextColor(Color.BLACK)
        }

        if(item.isCompleted){
            holder.mItem.background = ContextCompat.getDrawable(context, R.drawable.item_circular_color_accent_background)
            holder.mItem.setTextColor(Color.WHITE)
        }

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mItem = itemView.tv_item
    }

}