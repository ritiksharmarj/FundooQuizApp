package com.ritiksharmarj.fundoo

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ritiksharmarj.fundoo.adapters.QuizAdapter
import com.ritiksharmarj.fundoo.authentication.LoginActivity
import com.ritiksharmarj.fundoo.databinding.ActivityMainBinding
import com.ritiksharmarj.fundoo.models.Quiz
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    private lateinit var firestore: FirebaseFirestore
    private var checkedItem = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViews()
    }

    private fun setUpViews() {
        setUpFireStore()
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()
    }

    private fun setUpDatePicker() {
        binding.btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()

            // To show the picker to the user
            datePicker.show(supportFragmentManager, "DatePicker")

            // If user clicks "Ok" button
            datePicker.addOnPositiveButtonClickListener {
                // Format date as Quiz Title
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val date = dateFormatter.format(Date(it))

                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            // If user clicks "Cancel" button
            datePicker.addOnNegativeButtonClickListener {
                // Cancel clicked
            }
            // If user press the back button
            datePicker.addOnCancelListener {
                // Date Picker Cancelled
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpFireStore() {
        // Initialize Cloud Firestore
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quizzes")

        // Get real time updates
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null) {
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            /**
             * clear - Delete existing data to update with new one
             * addAll - Add all the data to quiz list
             */
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(this, quizList)
        binding.quizRecyclerView.adapter = adapter
        binding.quizRecyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun setUpDrawerLayout() {
        // Handle navigation drawer icon press
        setSupportActionBar(binding.topAppBar)
        toggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.more_options, R.string.close)
        toggle.syncState()

        // Handle navigation drawer menu item press
        binding.drawerNavigationView.setNavigationItemSelectedListener { it ->
            when (it.itemId) {
                R.id.miProfile -> {
                    Intent(this, ProfileActivity::class.java).also {
                        startActivity(it)
                    }
                }
                R.id.miChooseTheme -> setUpTheme()
                R.id.miLogOut -> setUpLogOut()
            }

            true
        }
    }

    private fun setUpLogOut() {
        FirebaseAuth.getInstance().signOut()
        Intent(this, LoginActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun setUpTheme() {
        val themeOptions = arrayOf("Light", "Dark", "System default")

        MaterialAlertDialogBuilder(this)
            .setTitle("Choose theme")
            .setSingleChoiceItems(themeOptions, checkedItem) { _, index ->
                checkedItem = index
            }
            .setPositiveButton("OK") { _, _ ->
                when (themeOptions[checkedItem]) {
                    "Light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    "Dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    "System default" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
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