package com.dwisusanto.scanner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.dwisusanto.scanner.model.DataScan
import com.dwisusanto.scannerlite.db.DatabaseHandler
import kotlinx.android.synthetic.main.activity_add_or_edit.*

class AddOrEditActivity : AppCompatActivity() {

    var dbHandler: DatabaseHandler? = null
    var isEditMode = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_or_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initDB()
        initOperations()
    }

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
        btn_delete.visibility = View.INVISIBLE
        if (intent != null && intent.getStringExtra("Mode") == "E") {
            isEditMode = true
            val tasks: DataScan = dbHandler!!.getScanner(intent.getIntExtra("Id",0))
            input_name.setText(tasks.name)
            btn_delete.visibility = View.VISIBLE
        }
    }

    private fun initOperations() {
        btn_save.setOnClickListener({
            var success: Boolean = false
            if (!isEditMode) {
                val datas: DataScan = DataScan()
                datas.name = input_name.text.toString()
                success = dbHandler?.addScanner(datas) as Boolean
            } else {
                val datas: DataScan = DataScan()
                datas.id = intent.getIntExtra("Id", 0)
                datas.name = input_name.text.toString()
                success = dbHandler?.updateScanner(datas) as Boolean
            }

            if (success)
                finish()
        })

        btn_delete.setOnClickListener({
            val dialog = AlertDialog.Builder(this).setTitle("Info").setMessage("Click 'YES' Delete the Task.")
                .setPositiveButton("YES", { dialog, i ->
                    val success = dbHandler?.deleteScanner(intent.getIntExtra("Id", 0)) as Boolean
                    if (success)
                        finish()
                    dialog.dismiss()
                })
                .setNegativeButton("NO", { dialog, i ->
                    dialog.dismiss()
                })
            dialog.show()
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
