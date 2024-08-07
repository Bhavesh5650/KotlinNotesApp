package com.example.noteapplication.adapter

import android.annotation.SuppressLint
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.noteapplication.R
import com.example.noteapplication.databinding.NoteShowLayoutBinding
import com.example.noteapplication.model.NoteModel

class NoteAdapter(private var noteList: MutableList<NoteModel>) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

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

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.setTitle.text = noteList[position].title
        holder.binding.setDes.text = noteList[position].des
        if(noteList[position].priority==1)
        {
            holder.binding.setBackground.setCardBackgroundColor(R.color.normal_gray)
        }
        else if(noteList[position].priority==2)
        {
            holder.binding.setBackground.setBackgroundColor(R.color.medium_amber)
        }
        else if(noteList[position].priority==3)
        {
            holder.binding.setBackground.setBackgroundColor(R.color.high_red)
        }
        else if(noteList[position].priority==4)
        {
            holder.binding.setBackground.setBackgroundColor(R.color.urgent_orange)
        }
        else
        {
            holder.binding.setBackground.setBackgroundColor(R.color.normal_gray)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun changeData(list:MutableList<NoteModel>){
        noteList = list
        notifyDataSetChanged()
    }
}