package com.dwisusanto.scannerlite.controller

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dwisusanto.scanner.AddOrEditActivity
import com.dwisusanto.scanner.R
import com.dwisusanto.scanner.model.DataScan
import java.util.*

class TaskRecyclerAdapter(datasList: List<DataScan>, internal var context: Context) : RecyclerView.Adapter<TaskRecyclerAdapter.TaskViewHolder>() {

    internal var datasList: List<DataScan> = ArrayList()
    init {
        this.datasList = datasList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_datas, parent, false)
        return TaskViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val datas = datasList[position]
        holder.name.text = datas.name
        if (datas.name !=null)
            holder.list_item.background = ContextCompat.getDrawable(context, R.color.colorSuccess)
        else
            holder.list_item.background = ContextCompat.getDrawable(context, R.color.colorUnSuccess)

//        holder.itemView.setOnClickListener {
//            val i = Intent(context, AddOrEditActivity::class.java)
//            i.putExtra("Mode", "E")
//            i.putExtra("Id", datas.id)
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            context.startActivity(i)
//        }
    }

    override fun getItemCount(): Int {
        return datasList.size
    }

    inner class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.tvName) as TextView
        var list_item: LinearLayout = view.findViewById(R.id.list_item) as LinearLayout
    }

}