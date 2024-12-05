package com.example.noteapplication.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.noteapplication.fragment.AllNoteFragment

class PriorityAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when(position)
        {
            0->AllNoteFragment()
            1->AllNoteFragment()
            2->AllNoteFragment()
            3->AllNoteFragment()
            4->AllNoteFragment()
            else->AllNoteFragment()
        }
    }
}