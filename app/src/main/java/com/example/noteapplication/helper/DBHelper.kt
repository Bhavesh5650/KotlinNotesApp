package com.example.noteapplication.helper

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.FileObserver.CREATE
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import com.example.noteapplication.model.NoteModel

@Suppress("NAME_SHADOWING")
class DBHelper(context: Context?) : SQLiteOpenHelper(context, "MyDataBase.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE Note (id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT ,des TEXT,priority INTEGER)"
        db!!.execSQL(query)

        val trashQuery = "CREATE TABLE Trash (id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT ,des TEXT,priority INTEGER)"
        db.execSQL(trashQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun insert(model: NoteModel)
    {
        Log.e("TAG", "insert: = ==  ${model.priority}", )
        val db = writableDatabase
        val query1 = "INSERT INTO Note(title,des,priority) VALUES ('${model.title}','${model.des}','${model.priority}')"
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

    @SuppressLint("Range")
    fun readPriority(priority: Int):MutableList<NoteModel>
    {
        val noteList = mutableListOf<NoteModel>()
        val db = readableDatabase
        val query2 = "SELECT * FROM Note WHERE priority='$priority'"
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

    fun delete(id:Int)
    {
        val db = writableDatabase
        val query4 = "DELETE FROM Note WHERE id='$id'"
        db.execSQL(query4)
    }

    fun trashInsert(model: NoteModel)
    {
        val db = writableDatabase
        val tQuery1 = "INSERT INTO Trash(title,des,priority) VALUES ('${model.title}','${model.des}','${model.priority}')"
        db.execSQL(tQuery1)
    }

    @SuppressLint("Range")
    fun trashRead() : MutableList<NoteModel>
    {
        val noteList = mutableListOf<NoteModel>()
        val db = readableDatabase
        val tQuery2 = "SELECT * FROM Trash"
        val cursor =db.rawQuery(tQuery2,null)
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

    fun trashUpdate(model: NoteModel)
    {
        val db = writableDatabase
        val tQuery3 = "UPDATE Trash SET title='${model.title}',des='${model.des}',priority='${model.priority}' WHERE id ='${model.id}'"
        db.execSQL(tQuery3)
    }

    fun trashDelete(id:Int)
    {
        val db = writableDatabase
        val tQuery4 = "DELETE FROM Trash WHERE id='$id'"
        db.execSQL(tQuery4)
    }

}