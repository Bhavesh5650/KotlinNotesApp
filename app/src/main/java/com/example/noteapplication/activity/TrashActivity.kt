package com.example.noteapplication.activity

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.noteapplication.R
import com.example.noteapplication.adapter.TrashAdapter
import com.example.noteapplication.databinding.ActivityMainBinding
import com.example.noteapplication.databinding.ActivityTrashBinding
import com.example.noteapplication.helper.DBHelper
import com.example.noteapplication.model.NoteModel

class TrashActivity : AppCompatActivity() {

    private lateinit var binding:ActivityTrashBinding
    var list = mutableListOf<NoteModel>()
    lateinit var trashAdapter: TrashAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.trashBackBtn.setOnClickListener {
            finish()
        }

        trashAdapter = TrashAdapter(list)
        binding.trashRecyclerView.adapter = trashAdapter
    }

    override fun onResume() {
        val dbHelper = DBHelper(this)
        list = dbHelper.trashRead()
        trashAdapter.restoreDataChange(list)
        super.onResume()
    }
}