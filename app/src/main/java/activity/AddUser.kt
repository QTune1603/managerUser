package com.example.bt1.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.bt1.model.AppUser
import com.example.bt1.R
import com.example.bt1.dao.UserDAO
import java.util.*

class AddUser : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val userNameEditText = findViewById<EditText>(R.id.un)
        val passwordEditText = findViewById<EditText>(R.id.Mk)
        val fullNameEditText = findViewById<EditText>(R.id.full)
        val emailEditText = findViewById<EditText>(R.id.mail)
        val telEditText = findViewById<EditText>(R.id.telEditText)
        val dobEditText = findViewById<EditText>(R.id.dobEditText)
        val genderSpinner = findViewById<Spinner>(R.id.genderSpinner)
        val addButton = findViewById<Button>(R.id.addButton)

        // Cài đặt dữ liệu cho Spinner (danh sách giới tính)
        val genderOptions = arrayOf("", "Male", "Female", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genderOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        // Thiết lập DatePickerDialog cho EditText ngày sinh
        dobEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val dob = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                dobEditText.setText(dob)
            }, year, month, day)

            datePickerDialog.show()
        }

        addButton.setOnClickListener {
            val userName = userNameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val fullName = fullNameEditText.text.toString()
            val email = emailEditText.text.toString()
            val tel = telEditText.text.toString()
            val dob = dobEditText.text.toString()
            val selectedGender = genderSpinner.selectedItem.toString()

            // Kiểm tra xem có trường nào bị bỏ trống hay không
            if (listOf(userName, password, fullName, email, tel, dob, selectedGender).any { it.isEmpty() }) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userDAO = UserDAO(this)

            // Tạo đối tượng AppUser và thêm vào database
            val user = AppUser(userName, password, fullName, email, tel, 0, dob, selectedGender)

            // Thêm user vào cơ sở dữ liệu và lấy ID mới sinh ra
            val newId = userDAO.add(user)

            // Định dạng ID thành chuỗi có độ dài 5 ký tự
            val formattedId = String.format("%05d", newId)
            Toast.makeText(this, "User added successfully with ID: $formattedId", Toast.LENGTH_SHORT).show()

            // Xóa sạch các trường dữ liệu
            userNameEditText.text.clear()
            passwordEditText.text.clear()
            fullNameEditText.text.clear()
            emailEditText.text.clear()
            dobEditText.text.clear()
            telEditText.text.clear()
            genderSpinner.setSelection(0) // Reset spinner
        }
    }
}
