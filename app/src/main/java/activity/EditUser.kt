package com.example.bt1.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bt1.model.AppUser
import com.example.bt1.R
import com.example.bt1.dao.UserDAO

class EditUser : AppCompatActivity() {

    private lateinit var userDAO: UserDAO  // Khởi tạo biến userDAO để tương tác với database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Khởi tạo đối tượng UserDAO để làm việc với cơ sở dữ liệu
        userDAO = UserDAO(this)

        // Nhận dữ liệu User từ Intent
        val user = intent.getParcelableExtra<AppUser>("user")

        // Tìm các EditText và Spinner bằng ID
        val usernameEditText = findViewById<EditText>(R.id.un)
        val passwordEditText = findViewById<EditText>(R.id.Mk)
        val fullnameEditText = findViewById<EditText>(R.id.full)
        val emailEditText = findViewById<EditText>(R.id.mail)
        val telEditText = findViewById<EditText>(R.id.telEditText) // Thêm trường tel
        val dobEditText = findViewById<EditText>(R.id.dobEditText)
        val genderSpinner = findViewById<Spinner>(R.id.genderSpinner)

        // Cài đặt dữ liệu cho Spinner (giới tính)
        val genderOptions = resources.getStringArray(R.array.gender_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        // Hiển thị thông tin User trên các EditText và Spinner
        user?.let {
            usernameEditText.setText(it.userName)
            passwordEditText.setText(it.password)
            fullnameEditText.setText(it.fullName)
            emailEditText.setText(it.email)
            telEditText.setText(it.tel) // Hiển thị số điện thoại
            dobEditText.setText(it.dateOfBirth)

            // Đặt giá trị cho Spinner dựa trên giới tính đã lưu
            val genderIndex = genderOptions.indexOf(it.gender)
            if (genderIndex >= 0) {
                genderSpinner.setSelection(genderIndex)
            }
        }

        // Reset fields
        val resetButton = findViewById<Button>(R.id.resetButton)
        resetButton.setOnClickListener {
            usernameEditText.text.clear()
            passwordEditText.text.clear()
            fullnameEditText.text.clear()
            emailEditText.text.clear()
            telEditText.text.clear() // Xóa trường tel
            dobEditText.text.clear()
            genderSpinner.setSelection(0)

            Toast.makeText(this, "Fields reset!", Toast.LENGTH_SHORT).show()
        }

        // Save updated information
        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            if (user != null) {
                val email = emailEditText.text.toString()

                // Kiểm tra email có đuôi @gmail.com hay không
                if (!email.endsWith("@gmail.com")) {
                    Toast.makeText(this, "Email must end with @gmail.com", Toast.LENGTH_SHORT).show()
                } else {
                    // Tạo đối tượng user đã được cập nhật
                    val updatedUser = AppUser(
                        usernameEditText.text.toString(),
                        passwordEditText.text.toString(),
                        fullnameEditText.text.toString(),
                        emailEditText.text.toString(),
                        telEditText.text.toString(), // Lấy giá trị tel từ EditText
                        user.id,  // Giữ nguyên ID cũ
                        dobEditText.text.toString(),
                        genderSpinner.selectedItem.toString()
                    )

                    // Cập nhật user trong cơ sở dữ liệu
                    val isUpdated = userDAO.update(updatedUser)

                    if (isUpdated) {
                        Toast.makeText(this, "User information updated successfully!", Toast.LENGTH_SHORT).show()
                        finish()  // Quay lại màn hình trước đó
                    } else {
                        Toast.makeText(this, "Failed to update user information", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "User information not found!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
