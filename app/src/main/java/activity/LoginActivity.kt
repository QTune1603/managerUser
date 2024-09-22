package com.example.bt1.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bt1.R
import com.example.bt1.User

class LoginActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Set window insets for edge-to-edge design
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get the user data passed from MainActivity
        val user = intent.getParcelableExtra<User>("user", User::class.java)
        val textView = findViewById<TextView>(R.id.Welcome)

        // Display user ID and dynamic name instead of hardcoded text
        val message = "Welcome, ID: ${user?.name}"
        textView.text = message

        // Add User button
        val btnAdd = findViewById<Button>(R.id.Add)
        btnAdd.setOnClickListener {
            // Navigate to AddUser Activity
            val intent = Intent(this, AddUser::class.java)
            startActivity(intent)
        }

        // Search User button
        val btnSearch = findViewById<Button>(R.id.Search)
        btnSearch.setOnClickListener {
            // Navigate to SearchUser Activity
            val intent = Intent(this, SearchUser::class.java)
            startActivity(intent)
        }
    }
}
