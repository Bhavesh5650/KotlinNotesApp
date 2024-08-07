package com.example.noteapplication.helper

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.FileObserver.CREATE
import android.provider.ContactsContract.CommonDataKinds.Note
import com.example.noteapplication.model.NoteModel

class DBHelper(context: Context?) : SQLiteOpenHelper(context, "MyDataBase.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE Note (id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT ,des TEXT,priority INTEGER)"
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun insert(title:String,des:String,priority:Int)
    {
        val db = writableDatabase
        val query1 = "INSERT INTO Note(title,des,priority) VALUES ('$title','$des','$priority')"
        db.execSQL(query1)
    }

    @SuppressLint("Range")
    fun read():MutableList<NoteModel>
    {
        val noteList = mutableListOf<NoteModel>()
        val db = readableDatabase
        val query2 = "SELECT * FROM Note"
        val cursor =db.rawQuery(query2,null)
        if(cursor.moveToFirst())
        {
            do{

                val id=cursor.getInt(cursor.getColumnIndex("id"))
                val title = cursor.getString(cursor.getColumnIndex("title"))
                val des = cursor.getString(cursor.getColumnIndex("des"))
                val priority = cursor.getInt(cursor.getColumnIndex("priority"))
                val model = NoteModel(id,title,des,priority)
                noteList.add(model)
            }while(cursor.moveToNext())
        }
        return noteList
    }

    fun update(model: NoteModel)
    {
        val db = writableDatabase
        val query3 = "UPDATE Note SET title='${model.title}',des='${model.des}',priority='${model.priority}' WHERE id ='${model.id}'"
        db.execSQL(query3)
    }
}