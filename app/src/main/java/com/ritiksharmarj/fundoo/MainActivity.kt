package com.ritiksharmarj.fundoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.ritiksharmarj.fundoo.adapters.QuizAdapter
import com.ritiksharmarj.fundoo.databinding.ActivityMainBinding
import com.ritiksharmarj.fundoo.models.Quiz

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dummyData()
        setUpViews()
    }

    private fun dummyData() {
        quizList.add(Quiz("07-01-2023", "07-01-2023"))
        quizList.add(Quiz("07-01-2023", "07-01-2023"))
        quizList.add(Quiz("07-01-2023", "07-01-2023"))
        quizList.add(Quiz("07-01-2023", "07-01-2023"))
        quizList.add(Quiz("07-01-2023", "07-01-2023"))
        quizList.add(Quiz("07-01-2023", "07-01-2023"))
        quizList.add(Quiz("07-01-2023", "07-01-2023"))
        quizList.add(Quiz("07-01-2023", "07-01-2023"))
        quizList.add(Quiz("07-01-2023", "07-01-2023"))
    }

    private fun setUpViews() {
        setUpDrawerLayout()
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(this, quizList)
        binding.quizRecyclerView.adapter = adapter
        binding.quizRecyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun setUpDrawerLayout() {
        // Handle navigation icon press
        setSupportActionBar(binding.topAppBar)
        toggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.more_options, R.string.close)
        toggle.syncState()

        // Handle navigation menu item press
        binding.drawerNavigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.miProfile -> Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /**
         * Handle toggle selection for navigation menu items
         * Simply - ignore it if there is no nav menu item
         */
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}