package com.dwisusanto.scanner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.dwisusanto.scanner.model.DataScan
import com.dwisusanto.scannerlite.db.DatabaseHandler
import com.google.zxing.integration.android.IntentIntegrator

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var dbHandler: DatabaseHandler? = null
    private lateinit var buttonListData: Button
    private lateinit var buttonAbout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initDB()
        initOperations(this)

        buttonListData = findViewById(R.id.buttonData)
        buttonListData.setOnClickListener{
            startActivity(Intent(this, ListActivity::class.java))
        }

        buttonAbout = findViewById(R.id.buttonAbout)
        buttonAbout.setOnClickListener{
            startActivity(Intent(this, AboutActivity::class.java))
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
                    context.toast("Succes Add Data")
                    text_barcode.setText("")
                }else{
                    context.toast("Failled Add Data")
                    text_barcode.setText("")
                }
            }else{
                context.toast("Data Kosong")
            }

        })
    }

    fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    private fun initDB() {
        dbHandler = DatabaseHandler(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Success Get Data", Toast.LENGTH_LONG).show()

                val textBarcode=findViewById(R.id.text_barcode) as TextView;
                textBarcode.text=result.contents
            }
        }
    }
}
