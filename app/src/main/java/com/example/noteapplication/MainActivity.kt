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
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.noteapplication.activity.EnterNoteActivity
import com.example.noteapplication.activity.TrashActivity
import com.example.noteapplication.adapter.NoteAdapter
import com.example.noteapplication.adapter.PriorityAdapter
import com.example.noteapplication.databinding.ActivityMainBinding
import com.example.noteapplication.fragment.AllNoteFragment
import com.example.noteapplication.helper.DBHelper
import com.example.noteapplication.helper.ThemeHelper
import com.example.noteapplication.model.NoteModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import android.view.Window as Window1

class MainActivity : AppCompatActivity() {

    companion object{
        var pageIndex = -1;
    }
    private lateinit var binding: ActivityMainBinding
    private var noteList = mutableListOf<NoteModel>()
    private lateinit var adapter1: NoteAdapter
    var th: ThemeHelper? = null

    //    companion object{
//        var tabPriority =0
//
//    }
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

        val theme = th!!.getTheme(this)
        if(theme)
        {
            binding.setThemeName.text = "Light Mode"
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else
        {
            binding.setThemeName.text = "Dark Mode"
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding.openMenuBtn.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
            binding.drawerLayout.open()
        }

        setPriority()
        //initDrawer()
        initTrashMenuClick()

        binding.themeMenu.setOnClickListener {

            if (theme) {
                th!!.setTheme(this@MainActivity, false)
                binding.setThemeName.text = "Light Mode"
                binding.setThemeIcon.setImageResource(R.drawable.outline_light_mode_24)
//                binding.drawerLayout.closeDrawers()
            } else {
                th!!.setTheme(this@MainActivity, true)
                binding.setThemeName.text = "Dark Mode"
                binding.setThemeIcon.setImageResource(R.drawable.outline_dark_mode_24)
//                binding.drawerLayout.closeDrawers()
            }
            recreate()
        }


        binding.fab.setOnClickListener {
            val intent = Intent(this, EnterNoteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initDrawer() {
        binding.homeMenu.setOnClickListener {
            binding.drawerLayout.closeDrawers()
        }
    }

    private fun setPriority() {

        val priorityAdapter = PriorityAdapter(this)
        binding.viewPager.adapter = priorityAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, index ->

            when (index) {
                0 -> tab.text = "All Notes"
                1 -> tab.text = "Normal"
                2 -> tab.text = "Medium"
                3 -> tab.text = "High"
                4 -> tab.text = "Urgent"
            }
        }.attach()

        binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                pageIndex = when(tab!!.position)
                {
                    0 -> -1
                    1 -> 1
                    2 -> 2
                    3 -> 3
                    4 -> 4
                    else -> -1
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

        binding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                pageIndex = when(position)
                {
                    0 -> -1
                    1 -> 1
                    2 -> 2
                    3 -> 3
                    4 -> 4
                    else -> -1
                }


            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }

    private fun initTrashMenuClick() {
        binding.trashMenu.setOnClickListener {
            val intent = Intent(this, TrashActivity::class.java)
            startActivity(intent)
            binding.drawerLayout.closeDrawers()
        }
    }

}