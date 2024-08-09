package com.example.noteapplication.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noteapplication.adapter.NoteAdapter
import com.example.noteapplication.databinding.FragmentAllNoteBinding
import com.example.noteapplication.helper.DBHelper
import com.example.noteapplication.model.NoteModel

class AllNoteFragment(var priority:Int) : Fragment() {

    private var noteList = mutableListOf<NoteModel>()
    private lateinit var adapter1:NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAllNoteBinding.inflate(layoutInflater)

        adapter1 = NoteAdapter(this@AllNoteFragment,noteList)
        binding.noteRecycleView.adapter = adapter1

        return binding.root
    }

    override fun onResume() {
        val dbHelper = DBHelper(activity)
        if(priority==-1)
        {
            noteList = dbHelper.read()
        }
        else
        {
            noteList = dbHelper.readPriority(priority)
        }
        adapter1.changeData(noteList)
        super.onResume()
    }


}