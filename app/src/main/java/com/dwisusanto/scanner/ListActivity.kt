package com.dwisusanto.scanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dwisusanto.scanner.model.DataScan
import com.dwisusanto.scannerlite.controller.TaskRecyclerAdapter
import com.dwisusanto.scannerlite.db.DatabaseHandler

class ListActivity : AppCompatActivity() {
    var taskRecyclerAdapter: TaskRecyclerAdapter? = null;
    var recyclerView: RecyclerView? = null
    var dbHandler: DatabaseHandler? = null
    var listTasks: List<DataScan> = ArrayList<DataScan>() as List<DataScan>
    var linearLayoutManager: LinearLayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        initViews()
    }

    fun initDB() {
        dbHandler = DatabaseHandler(this)
        listTasks = (dbHandler as DatabaseHandler).data()
        taskRecyclerAdapter = TaskRecyclerAdapter(datasList = listTasks, context = applicationContext)
        (recyclerView as RecyclerView).adapter = taskRecyclerAdapter
    }

    fun initViews() {

        recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        taskRecyclerAdapter = TaskRecyclerAdapter(datasList = listTasks, context = applicationContext)
        linearLayoutManager = LinearLayoutManager(applicationContext)
        (recyclerView as RecyclerView).layoutManager = linearLayoutManager
    }

    override fun onResume() {
        super.onResume()
        initDB()
    }
}
