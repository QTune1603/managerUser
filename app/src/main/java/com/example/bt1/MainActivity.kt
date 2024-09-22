package com.example.bt1

import android.widget.EditText
import android.widget.Toast
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.os.Parcelable
import com.example.bt1.activity.LoginActivity
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val name: String, val password: String) : Parcelable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Set window insets for edge-to-edge design
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val inputField = findViewById<EditText>(R.id.Name)
        val log = findViewById<Button>(R.id.timKiem)
        val inputPass = findViewById<EditText>(R.id.Pass)

        log.setOnClickListener {
            val enteredName = inputField.text.toString()
            val password = inputPass.text.toString()

            if (enteredName.isEmpty() || password.isEmpty()) {
                Toast.makeText(
                    this@MainActivity,
                    "ID OR PASSWORD IS NOT EMPTY!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (enteredName != "B21DCDT239" || password != "123") {
                Toast.makeText(
                    this@MainActivity,
                    "WRONG ID OR PASSWORD!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Tạo đối tượng User
                val user = User(enteredName, password)
                // Truyền đối tượng User qua Intent
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("user", user)
                startActivity(intent)
            }
        }
    }
}
