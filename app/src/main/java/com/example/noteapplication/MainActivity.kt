package com.example.noteapplication

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.Gravity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.ui.window.Window
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.noteapplication.activity.EnterNoteActivity
import com.example.noteapplication.activity.TrashActivity
import com.example.noteapplication.adapter.NoteAdapter
import com.example.noteapplication.adapter.PriorityAdapter
import com.example.noteapplication.databinding.ActivityMainBinding
import com.example.noteapplication.fragment.AllNoteFragment
import com.example.noteapplication.helper.DBHelper
import com.example.noteapplication.helper.ThemeHelper
import com.example.noteapplication.model.NoteModel
import com.google.android.material.tabs.TabLayoutMediator
import android.view.Window as Window1

@Suppress("UNUSED_EXPRESSION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private val fragmentManager = supportFragmentManager
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var noteList = mutableListOf<NoteModel>()
    private lateinit var adapter1:NoteAdapter
    var th :ThemeHelper?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        th = ThemeHelper()

        binding.openMenuBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        setPriority()
        initDrawer()
        initTrashMenuClick()

        binding.fab.setOnClickListener {
            val intent = Intent(this,EnterNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initDrawer()
    {
        binding.homeMenu.setOnClickListener{
            binding.drawerLayout.closeDrawers()
        }
    }

    private fun setPriority()
    {
        //initTheme()

        val priorityAdapter = PriorityAdapter(this)
        binding.viewPager.adapter = priorityAdapter

        TabLayoutMediator(binding.tabLayout,binding.viewPager){ tab,index ->

            when(index)
            {
                0 -> tab.text = "All Notes"
                1 -> tab.text = "Normal"
                2 -> tab.text = "Medium"
                3 -> tab.text = "High"
                4 -> tab.text = "Urgent"
            }
        }.attach()
    }

    private fun initTrashMenuClick()
    {
        binding.trashMenu.setOnClickListener {
            val intent = Intent(this,TrashActivity::class.java)
            startActivity(intent)
            binding.drawerLayout.closeDrawers()
        }
    }

//    private fun initTheme()
//    {
//        val theme = th!!.getTheme(this)
//
//        if(theme)
//        {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        }
//        else
//        {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
//
//        binding.themeMenu.setOnClickListener {
//
//            if(theme)
//            {
//                th!!.setTheme(this@MainActivity,false)
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//
//                binding.setThemeName.text = "Light Mode"
//                binding.setThemeIcon.setImageResource(R.drawable.outline_light_mode_24)
//            }
//            else
//            {
//                th!!.setTheme(this@MainActivity,true)
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            }
//            binding.drawerLayout.closeDrawers()
//        }
//
//    }
}