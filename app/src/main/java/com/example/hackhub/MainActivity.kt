package com.example.hackhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val SweepIntent=Intent(this,IpSweepper::class.java)
        val stButton : Button = findViewById(R.id.drawerToggle)
        drawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.nav_home -> Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_SHORT).show()
                R.id.nav_File_Bruteforce -> Toast.makeText(applicationContext, "Clicked File Bruteforce", Toast.LENGTH_SHORT).show()
                R.id.nav_IP_Sweeper -> startActivity(SweepIntent)
                R.id.nav_new2 -> Toast.makeText(applicationContext, "Clicked new2", Toast.LENGTH_SHORT).show()
                R.id.nav_new3 -> Toast.makeText(applicationContext, "Clicked new3", Toast.LENGTH_SHORT).show()

            }
            true
        }



        stButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

    }
    private fun replaceFragment(fragment: Fragment,title : String){
        val fragmentManager = supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frameLayout,fragment)
        fragmentTransition.commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
            return super.onOptionsItemSelected(item)
        
    }
}