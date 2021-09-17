package com.example.foodsaver


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.activity_main.*

class Homepage : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_homepage)

            val firstFragment=FirstFragment()
//            val secondFragment=SecondFragment()
            val thirdFragment=ThirdFragment()

            setCurrentFragment(firstFragment)


            bottomNavigationView.setOnNavigationItemSelectedListener {
                when(it.itemId){
                    R.id.home -> setCurrentFragment(firstFragment)
//                    R.id.history->setCurrentFragment(secondFragment)
                    R.id.settings -> setCurrentFragment(thirdFragment)

                }
                true
            }

        }

        private fun setCurrentFragment(fragment: Fragment)=
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.flFragment, fragment)
                    commit()
                }

        fun gotoMainActivity(v: View?) {
            val preferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear();
            editor.apply();
            finish();
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        fun gotoEditProfileActivity(v: View?) {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
    fun gotoItemActivity(v: View?) {
        val intent = Intent(this, ItemActivity::class.java)
        startActivity(intent)
    }

}