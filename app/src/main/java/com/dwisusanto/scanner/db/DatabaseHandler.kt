package com.dwisusanto.scannerlite.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.dwisusanto.scanner.model.DataScan

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DatabaseHandler.DB_NAME, null, DatabaseHandler.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $NAME TEXT);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addScanner(dataScan: DataScan): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, dataScan.name)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        Log.v("InsertedId", "$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    fun getScanner(_id: Int): DataScan {
        val data = DataScan()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)

        cursor?.moveToFirst()
        data.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
        data.name = cursor.getString(cursor.getColumnIndex(NAME))
        cursor.close()
        return data
    }

    fun data(): List<DataScan> {
        val scannerList = ArrayList<DataScan>()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val data = DataScan()
                    data.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    data.name = cursor.getString(cursor.getColumnIndex(NAME))
                    scannerList.add(data)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return scannerList
    }

    fun updateScanner(dataScan: DataScan): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, dataScan.name)
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(dataScan.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteScanner(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteAllScanner(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, null, null).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    companion object {
        private val DB_VERSION = 1
        private val DB_NAME = "DataScan"
        private val TABLE_NAME = "DataScan"
        private val ID = "Id"
        private val NAME = "Name"
    }
}