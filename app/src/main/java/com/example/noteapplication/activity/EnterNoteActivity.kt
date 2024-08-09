package com.example.noteapplication.activity

import android.os.Bundle
import android.renderscript.RenderScript.Priority
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.noteapplication.R
import com.example.noteapplication.databinding.ActivityEnterNoteBinding
import com.example.noteapplication.helper.DBHelper
import com.example.noteapplication.model.NoteModel
import kotlin.math.log

class EnterNoteActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEnterNoteBinding
    var id:Int=-1
    private var prioritys:Int=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        getData()


        forPriority()
        insertAndUpdate(prioritys)

        binding.backBtn.setOnClickListener {
            finish()
        }
    }

    private fun insertAndUpdate(priority: Int)
    {
        binding.noteDoneBtn.setOnClickListener {

            var title = binding.noteTitleEdt.text.toString()
            var des = binding.noteMsgEdt.text.toString()
            val dbHelper = DBHelper(this)
            val model = NoteModel(id=id,title = title,des = des, priority = priority)

            if(id==-1)
            {
                dbHelper.insert(model)
            }
            else
            {
                model.priority=priority
                //model.id = id
                dbHelper.update(model)
            }
            finish()
        }
    }

    private fun forPriority()
    {
        binding.buttonGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->

            when(checkedId)
            {
                R.id.btn1 -> insertAndUpdate(1)
                R.id.btn2 -> insertAndUpdate(2)
                R.id.btn3 -> insertAndUpdate(3)
                R.id.btn4 -> insertAndUpdate(4)
            }

            Log.e("TAG", "insertAndUpdate ====== : ${prioritys}" )

        }
    }
    private fun getData()
    {
        id = intent.getIntExtra("intentId",-1)
        val title =intent.getStringExtra("intentTitle")
        val des =intent.getStringExtra("intentDes")
        prioritys = intent.getIntExtra("intentPriority",1)
        binding.noteTitleEdt.setText(title)
        binding.noteMsgEdt.setText(des)
        getPriorityData()
    }
    private fun getPriorityData(){
        if (prioritys!=-1){
            binding.buttonGroup.check(when(prioritys){
                1->binding.btn1.id
                2->binding.btn2.id
                3->binding.btn3.id
                4->binding.btn4.id
                else->binding.btn1.id
            })
        }
    }
}