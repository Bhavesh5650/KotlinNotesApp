package com.example.noteapplication.adapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.icu.util.Calendar
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.noteapplication.R
import com.example.noteapplication.databinding.NoteShowLayoutBinding
import com.example.noteapplication.databinding.TrashDialogBinding
import com.example.noteapplication.helper.DBHelper
import com.example.noteapplication.model.NoteModel
import java.text.SimpleDateFormat

class TrashAdapter(var list:MutableList<NoteModel>) : RecyclerView.Adapter<TrashAdapter.TrashViewHolder>() {

    class TrashViewHolder(itemView: View) : ViewHolder(itemView)
    {
        val binding = NoteShowLayoutBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrashViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_show_layout,parent,false)
        return TrashViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: TrashViewHolder, position: Int) {
        holder.binding.setTitle.text = list[position].title
        holder.binding.setDes.text = list[position].des

        var calendar = Calendar.getInstance()
        var simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
        holder.binding.setDate.text = simpleDateFormat.format(calendar.time).toString()

        if(list[position].priority==1)
        {
            holder.binding.setBackground.setCardBackgroundColor(holder.itemView.context.getColor(R.color.normal_gray))
        }
        else if(list[position].priority==2)
        {
            holder.binding.setBackground.setCardBackgroundColor(holder.itemView.context.getColor(R.color.medium_amber))
        }
        else if(list[position].priority==3)
        {
            holder.binding.setBackground.setCardBackgroundColor(holder.itemView.context.getColor(R.color.high_red))
        }
        else if(list[position].priority==4)
        {
            holder.binding.setBackground.setCardBackgroundColor(holder.itemView.context.getColor(R.color.urgent_orange))
        }
        else
        {
            holder.binding.setBackground.setCardBackgroundColor(holder.itemView.context.getColor(R.color.normal_gray))
        }

        holder.binding.setBackground.setOnLongClickListener {

            val dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.trash_dialog)
            dialog.setCanceledOnTouchOutside(false)

            val cancelBtn = dialog.findViewById<ImageView>(R.id.cancelBtn)
            val restoreBtn = dialog.findViewById<TextView>(R.id.restoreBtn)
            val deleteBtn = dialog.findViewById<TextView>(R.id.deleteBtn)

            cancelBtn.setOnClickListener {
                dialog.dismiss()
            }
            deleteBtn.setOnClickListener{
                val dbHelper = DBHelper(holder.itemView.context)
                dbHelper.trashDelete(list[position].id)
                list.removeAt(position)
                notifyDataSetChanged()
                dialog.dismiss()
            }
            restoreBtn.setOnClickListener{
                val dbHelper = DBHelper(holder.itemView.context)
                dbHelper.insert(list[position])
                dbHelper.trashDelete(list[position].id)
                list.removeAt(position)
                notifyDataSetChanged()
                dialog.dismiss()
            }
            dialog.show()
            true
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun restoreDataChange(l1:MutableList<NoteModel>)
    {
        list = l1
        notifyDataSetChanged()
    }

}