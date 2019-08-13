package com.dwisusanto.scanner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.dwisusanto.scanner.model.DataScan
import com.dwisusanto.scannerlite.db.DatabaseHandler
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_add_or_edit.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var dbHandler: DatabaseHandler? = null
    var isEditMode = false
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initDB()
        initOperations(this)

        button = findViewById(R.id.buttonData)
        button.setOnClickListener{
            startActivity(Intent(this, ListActivity::class.java))
        }

        fab.setOnClickListener { view ->
            IntentIntegrator(this).initiateScan()
        }
    }

    private fun initOperations(context: Context) {
        btn_data_save.setOnClickListener({
            var success: Boolean = false
            val datas: DataScan = DataScan()
            datas.name = text_barcode.text.toString()
            if (!datas.name.isNullOrEmpty()){
                success = dbHandler?.addScanner(datas) as Boolean
                if (success){
//                    successMessage(this)
                    context.toast("Succes Add Data")
                }else{
                    context.toast("Failled Add Data")
                }
            }else{
                context.toast("Data Kosong")
            }
//            dbHandler?.addScanner(datas)


        })
    }

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Success Get Data", Toast.LENGTH_LONG).show()

                val tvHello=findViewById(R.id.text_barcode) as TextView;
                tvHello.text=result.contents
            }
        }
    }
}
