package com.example.testovoe16

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.testovoe16.databinding.ActivityMenuBinding
import com.google.android.material.tabs.TabLayout

class MenuActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var binding: ActivityMenuBinding
    private var favoriteFragment: Favorite? = null
    private var homeFragment: Home? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tabLayout = findViewById(R.id.tabLayout)

        val tab1: TabLayout.Tab = tabLayout.newTab()
        tab1.text = "Main"
        tab1.setIcon(R.drawable.main)
        tabLayout.addTab(tab1)

        val tab2: TabLayout.Tab = tabLayout.newTab()
        tab2.text = "Favorites"
        tab2.setIcon(R.drawable.star)
        tabLayout.addTab(tab2)

        val tab3: TabLayout.Tab = tabLayout.newTab()
        tab3.text = "Notification"
        tab3.setIcon(R.drawable.bell)
        tabLayout.addTab(tab3)

        val tab4: TabLayout.Tab = tabLayout.newTab()
        tab4.text = "News"
        tab4.setIcon(R.drawable.new_tab)
        tabLayout.addTab(tab4)

        val tab5: TabLayout.Tab = tabLayout.newTab()
        tab5.text = "Portfolio"
        tab5.setIcon(R.drawable.bag)
        tabLayout.addTab(tab5)

        val tab6: TabLayout.Tab = tabLayout.newTab()
        tab6.text = "ToolFusion"
        tab6.setIcon(R.drawable.key)
        tabLayout.addTab(tab6)

        val tab7: TabLayout.Tab = tabLayout.newTab()
        tab7.text = "Settings"
        tab7.setIcon(R.drawable.nastroi)
        tabLayout.addTab(tab7)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val position = tab.position
                val fragmentTransaction = supportFragmentManager.beginTransaction()

                when (position) {
                    1 -> {
                        val favoriteFragment = Favorite()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, favoriteFragment)
                            .commit()
                    }
                    0 -> {
                        val homeFragment = Home()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, homeFragment)
                            .commit()
                    }
                }

                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        replaceFragment(Home())
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.frame_layout, Home(), "HomeFragment")
        fragmentTransaction.commit()
    }
}