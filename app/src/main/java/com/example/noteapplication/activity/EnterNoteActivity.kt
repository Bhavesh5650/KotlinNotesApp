package com.example.noteapplication.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.noteapplication.R
import com.example.noteapplication.databinding.ActivityEnterNoteBinding
import com.example.noteapplication.helper.DBHelper

class EnterNoteActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEnterNoteBinding
    var no:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEnterNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dbHelper = DBHelper(this)

        binding.buttonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->

            when(checkedId)
            {
                R.id.btn1 -> no=1
                R.id.btn2 -> no=2
                R.id.btn3 -> no=3
                R.id.btn4 -> no=4
            }

        }
        binding.noteDoneBtn.setOnClickListener {


            var title = binding.noteTitleEdt.text.toString()
            var des = binding.noteMsgEdt.text.toString()

            dbHelper.insert(title,des,no)

            finish()

        }
        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}