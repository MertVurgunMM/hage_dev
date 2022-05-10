package com.practice.hagekotlin.screen

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.practice.hagekotlin.R
import com.practice.hagekotlin.screen.category.CategoryFragment
import com.practice.hagekotlin.screen.contents.ContentsActivity
import com.practice.hagekotlin.screen.login.LoginActivity
import com.practice.hagekotlin.screen.versionlogs.LogsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<BottomNavigationView>(R.id.bottom_navigation)
            .setOnItemSelectedListener {
                load(Page.find(it.itemId))
                true
            }

        load(Page.CATEGORIES)

        viewModel.greeting { firstName, lastName ->
            Toast.makeText(
                this,
                getString(R.string.welcome, firstName, lastName),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun load(page: Page) {
        when (page) {
            Page.CATEGORIES -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CategoryFragment())
                    .commit()
                title = getString(R.string.category)
            }
            Page.CHANGES -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, LogsFragment())
                    .commit()
                title = getString(R.string.changes)
            }
            Page.LOGOUT -> {
                AlertDialog.Builder(this)
                    .setMessage(getString(R.string.message_logout))
                    .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                        viewModel.logout()
                        dialog.dismiss()

                        Toast.makeText(
                            this,
                            getString(R.string.logout_success),
                            Toast.LENGTH_LONG
                        ).show()

                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                    .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    enum class Page(val menuId: Int) {
        CATEGORIES(R.id.item_categories),
        CHANGES(R.id.item_logs),
        LOGOUT(R.id.item_logout);

        companion object {
            fun find(menuId: Int) = values().first { it.menuId == menuId }
        }
    }
}