package com.example.russianpostcatalogue.domain.db

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteDatabase
import android.util.Log


class DataBaseHelper(context: Context): SQLiteOpenHelper(context, "myDB", null, 1) {


    private val LOG_TAG = "DataBaseHelper"

    private lateinit var db: SQLiteDatabase

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(LOG_TAG, "--- onCreate database ---")
        // создаем таблицу с полями
        this.db = db
        db.execSQL(
            "create table homes ("
                    + "name text,"
                    + "code text" + ");"
        )
        db.execSQL(
            "create table streets ("
                    + "name text,"
                    + "code text" + ");"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun deleteTable(tableName: String) {
        db.delete(tableName, null, null)

    }
}