package com.example.noteapplication.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.icu.util.LocaleData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.noteapplication.R
import com.example.noteapplication.activity.EnterNoteActivity
import com.example.noteapplication.databinding.DeleteDialogBinding
import com.example.noteapplication.databinding.NoteShowLayoutBinding
import com.example.noteapplication.fragment.AllNoteFragment
import com.example.noteapplication.helper.DBHelper
import com.example.noteapplication.model.NoteModel
import java.text.SimpleDateFormat

class NoteAdapter(var context: AllNoteFragment, private var noteList: MutableList<NoteModel>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {


    class NoteViewHolder(itemView: View) : ViewHolder(itemView)
    {
        var binding = NoteShowLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_show_layout,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    @SuppressLint("SetTextI18n", "ResourceAsColor", "SimpleDateFormat", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.setTitle.text = noteList[position].title
        holder.binding.setDes.text = noteList[position].des

        var calendar = Calendar.getInstance()
        var simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        holder.binding.setDate.text = simpleDateFormat.format(calendar.time).toString()

        if(noteList[position].priority==1)
        {
            holder.binding.setBackground.setCardBackgroundColor(holder.itemView.context.getColor(R.color.normal_gray))
        }
        else if(noteList[position].priority==2)
        {
            holder.binding.setBackground.setCardBackgroundColor(holder.itemView.context.getColor(R.color.medium_amber))
        }
        else if(noteList[position].priority==3)
        {
            holder.binding.setBackground.setCardBackgroundColor(holder.itemView.context.getColor(R.color.high_red))
        }
        else if(noteList[position].priority==4)
        {
            holder.binding.setBackground.setCardBackgroundColor(holder.itemView.context.getColor(R.color.urgent_orange))
        }
        else
        {
            holder.binding.setBackground.setCardBackgroundColor(holder.itemView.context.getColor(R.color.normal_gray))
        }
        holder.binding.setBackground.setOnClickListener {
            val intent = Intent(holder.itemView.context,EnterNoteActivity::class.java)
            intent.putExtra("intentTitle",noteList[position].title)
            intent.putExtra("intentDes",noteList[position].des)
            intent.putExtra("intentPriority",noteList[position].priority)
            intent.putExtra("intentId",noteList[position].id)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.setBackground.setOnLongClickListener {

            val dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.delete_dialog)
            dialog.setCanceledOnTouchOutside(false)
            val cancelBtn:TextView = dialog.findViewById(R.id.cancelBtn)
            val deleteBtn:TextView = dialog.findViewById(R.id.deleteBtn)
            cancelBtn.setOnClickListener{
                dialog.dismiss()
            }
            dialog.show()
            deleteBtn.setOnClickListener {
                val dbHelper = DBHelper(holder.itemView.context)
                dbHelper.trashInsert(noteList[position])
                dbHelper.delete(noteList[position].id)
                noteList.removeAt(position)
                notifyDataSetChanged()
                dialog.dismiss()
            }
            true
        }

    }


    @SuppressLint("NotifyDataSetChanged")
    fun changeData(list:MutableList<NoteModel>){
        noteList = list
        notifyDataSetChanged()
    }
}

